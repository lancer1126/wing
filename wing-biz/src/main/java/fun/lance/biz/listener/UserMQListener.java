package fun.lance.biz.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import fun.lance.common.exception.WingException;
import fun.lance.common.mq.group.UserGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserMQListener {

    @RabbitListener(queues = UserGroup.USER_QUEUE)
    public void receiveMember(String userInfo, Message message, Channel channel) throws IOException {
        // 消息序号
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        boolean err = false;
        try {
            log.info("收到消息: {}, {}",deliveryTag, userInfo);
            JSONObject userJson = JSON.parseObject(userInfo);
            if (userJson.getInteger("gender") == 2) {
                throw new WingException(412, "error");
            }
        } catch (Exception e) {
            err = true;
            log.error("", e);
            throw e;
        } finally {
            if (!err) {
                channel.basicAck(deliveryTag, true);
            } else {
                channel.basicNack(deliveryTag, false, false);
            }
        }
    }

}
