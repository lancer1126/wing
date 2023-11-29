package fun.lance.common.mq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
        simpleFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        simpleFactory.setMessageConverter(new Jackson2JsonMessageConverter());
        return simpleFactory;
    }

    @Bean
    public RabbitTemplate customRabbitTemplate(ConnectionFactory connFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        // 消息成功到达exchange中回调
        rabbitTemplate.setConfirmCallback((correlationData, b, s) -> log.info("----- Confirm回调： {}, {}, {} -----", correlationData, b, s));
        // 消息从exchange到queue投递失败，进行回调
        rabbitTemplate.setReturnsCallback((returnedMessage) -> {
            log.info("----------------------Returns回调----------------------------");
            log.info("ReturnCallback: " + "消息体： " + returnedMessage.getMessage());
            log.info("ReturnCallback: " + "回应码： " + returnedMessage.getReplyText());
            log.info("ReturnCallback: " + "回应信： " + returnedMessage.getReplyText());
            log.info("ReturnCallback: " + "交换机： " + returnedMessage.getExchange());
            log.info("ReturnCallback: " + "路由键： " + returnedMessage.getRoutingKey());
            log.info("-------------------------------------------------------------");
        });
        return rabbitTemplate;
    }
}
