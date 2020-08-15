package com.povosdi.rabbitmq.configuration;

import static org.springframework.amqp.core.ExchangeTypes.DIRECT;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author： povosdi
 * @date: 2020/8/6 下午9:37
 * @email：dibenteng@hzsuidifu.com
 */
@Configuration
public class DirectMqConfig {
    public final static String DIRECT_QUEUE = "direct_queue";
    public final static String DIRECT_EXCHANGE = "direct_exchange";
    public final static String DIRECT_EXCHANGE_NO_QUEUE = "direct_exchange_no_queue";
    public final static String DIRECT_NO_EXCHANGE = "direct_no_exchange";

    
    /**
     * direct模式队列
     * durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
     * exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
     * autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
     */
    @Bean
    public Queue directQueue() {
        return QueueBuilder.durable(DIRECT_QUEUE).build();
    }
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(DIRECT_EXCHANGE);
    }
    @Bean
    public Binding directExchangeBinding(){
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(DIRECT);
    }
    @Bean
    public DirectExchange directExchangeNoQueue(){
        return new DirectExchange(DIRECT_EXCHANGE_NO_QUEUE);
    }
    
    
}
