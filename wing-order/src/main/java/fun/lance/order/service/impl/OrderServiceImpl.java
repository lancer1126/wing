package fun.lance.order.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.lance.api.user.api.MemberFeignClient;
import fun.lance.common.cache.utils.BusinessSnGenerator;
import fun.lance.common.common.enums.BusinessType;
import fun.lance.common.exception.WingException;
import fun.lance.common.utils.MessageUtil;
import fun.lance.common.mq.group.OrderGroup;
import fun.lance.order.common.enums.OrderStatusEnum;
import fun.lance.order.conveter.OrderConverter;
import fun.lance.order.conveter.OrderItemConverter;
import fun.lance.order.domain.dto.OrderItemDTO;
import fun.lance.order.domain.dto.OrderSubmitDTO;
import fun.lance.order.domain.entity.Order;
import fun.lance.order.domain.entity.OrderItem;
import fun.lance.order.domain.vo.OrderConfirmVO;
import fun.lance.order.domain.vo.OrderSubmitResultVO;
import fun.lance.order.mapper.OrderMapper;
import fun.lance.order.service.OrderItemService;
import fun.lance.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private final Executor threadPoolTaskExecutor;
    private final MemberFeignClient memberFeignClient;
    private final BusinessSnGenerator businessSnGenerator;
    private final RedisTemplate<Object, Object> redisTemplate;
    private final OrderConverter orderConverter;
    private final OrderItemConverter orderItemConverter;
    private final OrderItemService orderItemService;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public OrderConfirmVO confirmOrder(Long skuId) {
        OrderConfirmVO orderConfirmVO = new OrderConfirmVO();
        Long memberId = Convert.toLong(111L);

        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        // 因为OrderConfirmVO中的三个值并无关联，所以使用三个线程分别获取
        CompletableFuture<Void> orderItemFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(attributes);
            // 异步获取商品明细信息
            orderConfirmVO.setOrderItems(getOrderItems(skuId, memberId));
        }, threadPoolTaskExecutor);

        CompletableFuture<Void> addressFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(attributes);
            try {
                // 异步获取用户地址列表
                orderConfirmVO.setMemberAddresses(memberFeignClient.listMemberAddresses(memberId).getData());
            } catch (Exception e) {
                log.error("获取用户地址列表错误, memberId: " + memberId);
                orderConfirmVO.setMemberAddresses(new ArrayList<>());
            }
        }, threadPoolTaskExecutor);

        CompletableFuture<Void> tokenFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(attributes);
            // 确认页面生成唯一token,订单提交根据此token判断是否重复提交
            String orderToken = businessSnGenerator.generateSerialNo(BusinessType.ORDER);
            orderConfirmVO.setOrderToken(orderToken);
            String cacheKey = OrderGroup.ORDER_RESUBMIT_LOCK_PREFIX + orderToken;
            redisTemplate.opsForValue().set(cacheKey, orderToken);
        }, threadPoolTaskExecutor);

        CompletableFuture.allOf(orderItemFuture, addressFuture, tokenFuture).join();
        log.info("订单确认: {}", orderConfirmVO);
        return orderConfirmVO;
    }

    @Override
    @Transactional
    public OrderSubmitResultVO submitOrder(OrderSubmitDTO orderSubmitDTO) {
        log.info("订单提交：" + JSON.toJSONString(orderSubmitDTO));

        List<OrderItemDTO> orderItems = orderSubmitDTO.getOrderItems();
        if (CollUtil.isEmpty(orderItems)) {
            throw new WingException("订单数量为空");
        }
        // 订单重复提交检测
        String orderToken = orderSubmitDTO.getOrderToken();
        Long lockResult = redisTemplate.execute(new DefaultRedisScript<>(
                "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end", Long.class
                ),
                CollUtil.newArrayList(OrderGroup.ORDER_RESUBMIT_LOCK_PREFIX + orderToken), orderToken);
        lockResult = Optional.ofNullable(lockResult).orElse(0L);
        if (lockResult.equals(1L)) {
            throw new WingException(MessageUtil.get("order.submit.repeat"));
        }

        // todo 订单验价

        // todo 锁定订单商品的库存

        // 保存订单
        Order orderEntity = orderConverter.submitDTO2Entity(orderSubmitDTO);
        orderEntity.setStatus(OrderStatusEnum.UNPAID.getValue());
        orderEntity.setMemberId(111L);  // 伪数据
        boolean saveResult = save(orderEntity);

        Long orderId = orderEntity.getId();
        if (saveResult) {
            List<OrderItem> itemEntities = orderItemConverter.dto2Entity(orderId, orderItems);
            saveResult = orderItemService.saveBatch(itemEntities);
            if (saveResult) {
                // 设置订单超时未支付则关闭订单
                rabbitTemplate.convertAndSend(
                        OrderGroup.MQ.ORDER_EXCHANGE,
                        OrderGroup.MQ.ORDER_CLOSE_DELAY_ROUTING_KEY,
                        orderToken
                );
            }
        }
        if (!saveResult) {
            throw new WingException("订单提交失败");
        }
        return new OrderSubmitResultVO(orderId, orderEntity.getOrderSn());
    }

    @Override
    public void closeOrder(String orderSn) {
        Order order = getOne(new LambdaQueryWrapper<Order>()
                .eq(Order::getOrderSn, orderSn)
                .select(Order::getId, Order::getStatus));
        if (orderSn == null) {
            throw new WingException("订单不存在");
        }

        if (OrderStatusEnum.UNPAID.getValue().equals(order.getStatus())) {
            order.setStatus(OrderStatusEnum.CANCELED.getValue());
            updateById(order);
            // todo 关单成功释放锁定的商品库存
        }
    }

    /**
     * 获取订单内的商品明细信息
     */
    private List<OrderItemDTO> getOrderItems(Long skuId, Long memberId) {
        // 伪数据
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setSkuId(skuId);
        orderItemDTO.setCount(111);
        orderItemDTO.setPrice(123L);
        orderItemDTO.setSkuName("大力丸");
        orderItemDTO.setSkuSn("singleSN" + memberId);
        return CollUtil.newArrayList(orderItemDTO);
    }
}
