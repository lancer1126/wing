package fun.lance.common.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitConfig {

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connFactory) {
        SimpleRabbitListenerContainerFactory simpleFactory = new SimpleRabbitListenerContainerFactory();
        simpleFactory.setConnectionFactory(connFactory);
        simpleFactory.setMessageConverter(new Jackson2JsonMessageConverter());
        return simpleFactory;
    }
}
