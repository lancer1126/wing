package fun.lance.order.listener;

import com.rabbitmq.client.Channel;
import fun.lance.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static fun.lance.order.common.constant.OrderConst.MQ.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCloseListener {

    private final OrderService orderService;

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = ORDER_CLOSE_DELAY_QUEUE,
                            arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = ORDER_DLX_EXCHANGE),
                                    @Argument(name = "x-dead-letter-routing-key", value = ORDER_ClOSE_ROUTING_KEY),
                                    @Argument(name = "x-message-ttl", value = "50000", type = "java.lang.Long")
                            }),
                    exchange = @Exchange(value = ORDER_EXCHANGE),
                    key = {ORDER_CLOSE_DELAY_ROUTING_KEY}
            )
    }, ackMode = "MANUAL")
    public void handleOrderCloseDelay(String orderSn, Message message, Channel channel) {
        log.info("订单 {} 延迟队列，10s内未支付将路由到关单队列", orderSn);
        message.getMessageProperties().getDeliveryTag();
    }

    @RabbitListener(queues = ORDER_ClOSE_QUEUE)
    public void handleOrderClose(String orderSn, Message message, Channel channel) throws IOException {
        // 消息序号
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        log.info("订单 【{}】 超时未支付，系统自动关闭订单", orderSn);
        try {
            orderService.closeOrder(orderSn);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicReject(deliveryTag, false);
        }
    }
}
