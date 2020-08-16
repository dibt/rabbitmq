package com.povosdi.rabbitmq.configuration;

import static com.povosdi.rabbitmq.configuration.DlqConfiguration.DEAD_LETTER_EXCHANGE;
import static com.povosdi.rabbitmq.configuration.DlqConfiguration.DEAD_LETTER_QUEUE_ROUTING_KEY_RETRY;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author： povosdi
 * @date: 2020/8/6 下午9:39
 * @email：dibenteng@hzsuidifu.com
 */
@Configuration
public class TopicMqConfig {
    public final static String TOPIC_QUEUE_RIGHT = "topic_queue_right";
    public final static String TOPIC_QUEUE_COMMON = "topic_queue_common";
    public final static String TOPIC_QUEUE_ERROR = "topic_queue_error";

    
    public final static String TOPIC_EXCHANGE = "topic_exchange";
    
    public final static String TOPIC_ROUTING_KEY_RIGHT = "common.right";
    public final static String TOPIC_ROUTING_KEY_COMMON = "common.#";
    public final static String TOPIC_ROUTING_KEY_ERROR = "common.error";
    
    /**
     * topic 订阅者模式队列
     */
    @Bean
    public Queue topicQueueRight() {
        return QueueBuilder.durable(TOPIC_QUEUE_RIGHT).build();
    }
    @Bean
    public Queue topicQueueCommon() {
        return QueueBuilder.durable(TOPIC_QUEUE_COMMON).build();
    }
    @Bean
    public Queue topicQueueError() {
        // 可以通过绑定不通类型的交换机来处理不同的逻辑，本示例通过定义一个topic类型的死信交换机进行重试操作，通过在消息的header里定义最大重试次数
        Map<String,Object> map = new HashMap<>(2);
        map.put("x-dead-letter-exchange",DEAD_LETTER_EXCHANGE);
        map.put("x-dead-letter-routing-key",DEAD_LETTER_QUEUE_ROUTING_KEY_RETRY);
        return QueueBuilder.durable(TOPIC_QUEUE_ERROR).withArguments(map).build();
    }
  
    
    /**
     * topic 交换器
     */
    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE).build();
    }
    
    /**
     * 订阅者模式绑定
     */
    @Bean
    public Binding topExchangeBingingOne() {
        return BindingBuilder.bind(topicQueueRight()).to(topicExchange()).with(TOPIC_ROUTING_KEY_RIGHT);
    }
    
    @Bean
    public Binding topicExchangeBingingTwo() {
        return BindingBuilder.bind(topicQueueCommon()).to(topicExchange()).with(TOPIC_ROUTING_KEY_COMMON);
    }
    @Bean
    public Binding topicExchangeBingingError() {
        return BindingBuilder.bind(topicQueueError()).to(topicExchange()).with(TOPIC_ROUTING_KEY_ERROR);
    }

}
