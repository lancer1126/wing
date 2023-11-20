package fun.lance.order.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static fun.lance.common.mq.group.OrderGroup.MQ.*;

@Slf4j
@Configuration
public class OrderCloseRabbitConfig {

    /**
     * 订单交换机
     */
    @Bean
    public Exchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE, true, false);
    }

    /**
     * 延迟队列
     */
    @Bean
    public Queue orderDelayQueue() {
        // 延时队列的消息过期了，会自动触发消息的转发，根据routingKey发送到指定的exchange中，exchange路由到死信队列
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", ORDER_DLX_EXCHANGE);
        args.put("x-dead-letter-routing-key", ORDER_DLX_ROUTING_KEY); // 死信路由Key
        args.put("x-message-ttl",6000L);
        return new Queue(ORDER_DELAY_QUEUE, true, false, false, args);
    }

    /**
     * 延迟队列绑定交换机
     */
    @Bean
    public Binding orderDelayQueueBinding() {
        return BindingBuilder.bind(orderDelayQueue()).to(orderExchange()).with(ORDER_DELAY_ROUTING_KEY).noargs();
    }

    /**
     * 死信交换机
     */
    @Bean
    public Exchange orderDlxExchange() {
        return new DirectExchange(ORDER_DLX_EXCHANGE, true, false);
    }

    /**
     * 死信队列
     */
    @Bean
    public Queue orderDlxQueue() {
        return new Queue(ORDER_DLX_QUEUE, true, false, false);
    }

    /**
     * 关单队列绑定死信交换机
     */
    @Bean
    public Binding orderDlxQueueBinding() {
        return BindingBuilder.bind(orderDlxQueue()).to(orderDlxExchange()).with(ORDER_DLX_ROUTING_KEY).noargs();
    }
}
