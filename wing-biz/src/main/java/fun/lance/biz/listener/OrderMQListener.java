package fun.lance.biz.listener;

import fun.lance.common.mq.group.OrderGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMQListener {

    @RabbitListener(queues = OrderGroup.MQ.ORDER_DLX_QUEUE)
    public void handlerOrder(String orderStr) {
        log.info("收到过期的订单：{}", orderStr);
    }
}
