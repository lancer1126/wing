package fun.lance.user.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

import static fun.lance.common.mq.group.UserGroup.*;

@Configuration
public class UserRabbitConfig {

    @Bean
    public Exchange userExchange() {
        return new DirectExchange(USER_EXCHANGE, true, false);
    }

    @Bean
    public Queue userQueue() {
        HashMap<String, Object> argMap = new HashMap<>(3);
        argMap.put("x-dead-letter-exchange", USER_DLX_EXCHANGE);
        argMap.put("x-dead-letter-routing-key", USER_DLX_ROUTING_KEY);
        argMap.put("x-message-ttl", 6000);
        return new Queue(USER_QUEUE, true, false, false, argMap);
    }

    @Bean
    public Binding userQueueBinding() {
        return BindingBuilder.bind(userQueue()).to(userExchange()).with(USER_ROUTING_KEY).noargs();
    }

    @Bean
    public Exchange userDlxExchange() {
        return new DirectExchange(USER_DLX_EXCHANGE, true, false);
    }

    @Bean
    public Queue userDlxQueue() {
        return new Queue(USER_DLX_QUEUE, true);
    }

    @Bean
    public Binding userDlxQueueBinding() {
        return BindingBuilder.bind(userDlxQueue()).to(userDlxExchange()).with(USER_DLX_ROUTING_KEY).noargs();
    }
}
