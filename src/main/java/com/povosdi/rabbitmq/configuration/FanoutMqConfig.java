package com.povosdi.rabbitmq.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author： povosdi
 * @date: 2020/8/3 下午9:15
 * @email：dibenteng@hzsuidifu.com
 */
@Configuration
public class FanoutMqConfig {

    public final static String FANOUT_QUEUE_ONE = "fanout_queue_one";
    public final static String FANOUT_QUEUE_TWO = "fanout_queue_two";
    public final static String FANOUT_QUEUE_THREE = "fanout_queue_three";
    
    public final static String FANOUT_EXCHANGE = "fanout_exchange";

    /**
     * fanout 广播者模式队列
     */
    @Bean
    public Queue fanoutQueueOne() {
        return QueueBuilder.durable(FANOUT_QUEUE_ONE).build();
    }
    @Bean
    public Queue fanoutQueueTwo() {
        return QueueBuilder.durable(FANOUT_QUEUE_TWO).build();
    }
    @Bean
    public Queue fanoutQueueThree() {
        return QueueBuilder.durable(FANOUT_QUEUE_THREE).build();
    }
    /**
     *  fanout 交换器
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }
    
    /**
     * 广播模式绑定
     */
    @Bean
    public Binding fanoutExchangeBingingOne() {
        return BindingBuilder.bind(fanoutQueueOne()).to(fanoutExchange());
    }
    @Bean
    public Binding fanoutExchangeBingingTwo() {
        return BindingBuilder.bind(fanoutQueueTwo()).to(fanoutExchange());
    }
    @Bean
    public Binding fanoutExchangeBingingThree() {
        return BindingBuilder.bind(fanoutQueueThree()).to(fanoutExchange());
    }

}
