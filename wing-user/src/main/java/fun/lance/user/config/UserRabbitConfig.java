package fun.lance.user.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static fun.lance.user.common.constants.MessageConst.*;

@Configuration
public class UserRabbitConfig {

    @Bean
    public Exchange userExchange() {
        return new DirectExchange(USER_EXCHANGE, true, false);
    }

    @Bean
    public Queue userQueue() {
        return new Queue(USER_QUEUE, true);
    }

    @Bean
    public Binding userQueueBinding() {
        return BindingBuilder.bind(userQueue()).to(userExchange()).with(USER_ROUTING_KEY).noargs();
    }
}
