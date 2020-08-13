package com.povosdi.rabbitmq.configuration;

import static com.povosdi.rabbitmq.configuration.DlqConfiguration.DEAD_LETTER_EXCHANGE;
import static com.povosdi.rabbitmq.configuration.DlqConfiguration.DEAD_LETTER_QUEUE_ROUTING_KEY;

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
    public final static String TOPIC_QUEUE_ONE = "topic_queue_one";
    public final static String TOPIC_QUEUE_TWO = "topic_queue_two";
    public final static String TOPIC_QUEUE_ERROR = "topic_queue_error";
    public final static String TOPIC_EXCHANGE = "topic_exchange";
    public final static String TOPIC_ROUTING_KEY_ONE = "common.key";
    public final static String TOPIC_ROUTING_KEY_TWO = "common.#";
    public final static String TOPIC_ROUTING_KEY_ERROR = "common.error";
    
    /**
     * topic 订阅者模式队列
     */
    @Bean
    public Queue topicQueueOne() {
        return QueueBuilder.durable(TOPIC_QUEUE_ONE).build();
    }
    @Bean
    public Queue topicQueueTwo() {
        return QueueBuilder.durable(TOPIC_QUEUE_TWO).build();
    }
    @Bean
    public Queue topicQueueError() {
        Map<String,Object> map = new HashMap<>(2);
        map.put("x-dead-letter-exchange",DEAD_LETTER_EXCHANGE);
        map.put("x-dead-letter-routing-key",DEAD_LETTER_QUEUE_ROUTING_KEY);
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
        return BindingBuilder.bind(topicQueueOne()).to(topicExchange()).with(TOPIC_ROUTING_KEY_ONE);
    }
    
    @Bean
    public Binding topicExchangeBingingTwo() {
        return BindingBuilder.bind(topicQueueTwo()).to(topicExchange()).with(TOPIC_ROUTING_KEY_TWO);
    }
    @Bean
    public Binding topicExchangeBingingError() {
        return BindingBuilder.bind(topicQueueError()).to(topicExchange()).with(TOPIC_ROUTING_KEY_ERROR);
    }
}
