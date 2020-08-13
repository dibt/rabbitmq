package com.povosdi.rabbitmq.configuration;

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
 * @date: 2020/8/3 下午8:10
 * @email：dibenteng@hzsuidifu.com
 */
@Configuration
public class DlqConfiguration {
    
    public final static String DEAD_LETTER_QUEUE = "dead_letter_queue";
    public final static String DEAD_LETTER_EXCHANGE = "dead_letter_exchange";
    public final static String DEAD_LETTER_QUEUE_ROUTING_KEY = "dead_letter";
    

    @Bean
    public Queue deadLetterQueue(){
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }
    @Bean
    public TopicExchange deadLetterExchange(){
        return ExchangeBuilder.topicExchange(DEAD_LETTER_EXCHANGE).build();
    }
    
    @Bean
    public Binding deadLetterExchangeBinging() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DEAD_LETTER_QUEUE_ROUTING_KEY);
    }
}
