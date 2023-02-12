package fun.lance.order.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import fun.lance.api.user.api.MemberFeignClient;
import fun.lance.common.cache.utils.BusinessSnGenerator;
import fun.lance.common.common.enums.BusinessType;
import fun.lance.order.common.OrderConst;
import fun.lance.order.domain.dto.OrderItemDTO;
import fun.lance.order.domain.vo.OrderConfirmVO;
import fun.lance.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final Executor threadPoolTaskExecutor;
    private final MemberFeignClient memberFeignClient;
    private final BusinessSnGenerator businessSnGenerator;
    private final RedisTemplate<Object, Object> redisTemplate;

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
            String cacheKey = OrderConst.ORDER_RESUBMIT_LOCK_PREFIX + orderToken;
            redisTemplate.opsForValue().set(cacheKey, orderToken);
        }, threadPoolTaskExecutor);

        CompletableFuture.allOf(orderItemFuture, addressFuture, tokenFuture).join();
        log.info("订单确认: {}", orderConfirmVO);
        return orderConfirmVO;
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
