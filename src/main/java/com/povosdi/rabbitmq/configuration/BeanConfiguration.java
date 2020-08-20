package com.povosdi.rabbitmq.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.povosdi.rabbitmq.handler.GlobalExceptionHandler;
import com.povosdi.rabbitmq.handler.GlobalFatalExceptionStrategy;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.ErrorHandler;

/**
 * @author： povosdi
 * @date: 2020/8/4 上午11:16
 * @email：dibenteng@hzsuidifu.com
 */
@Configuration
public class BeanConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMandatory(true);
        template.setMessageConverter(new SerializerMessageConverter());
        return template;
    }
    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory
        connectionFactory,SimpleRabbitListenerContainerFactoryConfigurer simpleRabbitListenerContainerFactoryConfigurer){
       
        SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory = new
            SimpleRabbitListenerContainerFactory();
    
        /* 消息序列化类型 */
        rabbitListenerContainerFactory.setMessageConverter( new Jackson2JsonMessageConverter() );
        
        simpleRabbitListenerContainerFactoryConfigurer.configure(rabbitListenerContainerFactory,connectionFactory);
    
        rabbitListenerContainerFactory.setErrorHandler(globalExceptionHandler());
        
        return rabbitListenerContainerFactory;
    }
    @Bean
    public ErrorHandler globalExceptionHandler(){
        return new GlobalExceptionHandler(globalFatalExceptionStrategy());
    }
    @Bean
    public FatalExceptionStrategy globalFatalExceptionStrategy(){
        return new GlobalFatalExceptionStrategy();
    }
}
