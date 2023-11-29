package fun.lance.order.listener.mq;

import com.rabbitmq.client.Channel;
import fun.lance.common.mq.group.UserGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class UserListener {

    @RabbitListener(queues = UserGroup.USER_ORDER_QUEUE)
    public void receiveMember(String memberInfo, Message message, Channel channel) {
        log.info("order模块接收到 {}", memberInfo);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
