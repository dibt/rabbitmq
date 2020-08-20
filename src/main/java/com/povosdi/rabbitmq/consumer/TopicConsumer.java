package com.povosdi.rabbitmq.consumer;

import static com.povosdi.rabbitmq.configuration.TopicMqConfig.TOPIC_QUEUE_COMMON;
import static com.povosdi.rabbitmq.configuration.TopicMqConfig.TOPIC_QUEUE_ERROR;
import static com.povosdi.rabbitmq.configuration.TopicMqConfig.TOPIC_QUEUE_RIGHT;
import static com.povosdi.rabbitmq.configuration.TopicMqConfig.TOPIC_ROUTING_KEY_COMMON;
import static com.povosdi.rabbitmq.configuration.TopicMqConfig.TOPIC_ROUTING_KEY_ERROR;
import static com.povosdi.rabbitmq.configuration.TopicMqConfig.TOPIC_ROUTING_KEY_RIGHT;
import static org.springframework.amqp.core.ExchangeTypes.TOPIC;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author： povosdi
 * @date: 2020/8/3 下午8:43
 * @email：dibenteng@hzsuidifu.com
 */
@Slf4j
@Component
public class TopicConsumer {
    
    /**
     * autoAck，default-requeue-rejected=true
     * BusinessException 会先交由 RabbitListenerErrorHandler-handleError 处理
     * 然后交由全局异常处理器 ErrorHandler-handleError 处理，后台会进行异常等级判断
     */
    
    //throw new RuntimeException("模拟消费消息失败-RuntimeException");
    
    
    @RabbitListener(queues = TOPIC_QUEUE_RIGHT,containerFactory="simpleRabbitListenerContainerFactory",errorHandler = "businessExceptionHandler")
    public void receiveTopicRightMessage(Message message){
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("模拟接收 {} 类型,路由键为:{}的消息:{}", TOPIC,TOPIC_ROUTING_KEY_RIGHT,msg);
        // BusinessException 路由到死信队列
//         throw new BusinessException("模拟消费消息失败-BusinessException");
       
        //throw new AmqpRejectAndDontRequeueException("模拟消费消息失败-AmqpRejectAndDontRequeueException");
        //throw new ClassCastException("模拟消费消息失败-ClassCastException");
        throw new RuntimeException("模拟消费消息失败-RuntimeException");
        // GlobalException 一直重试
//        throw new GlobalException("模拟消费消息失败-GlobalException");
    }


    @RabbitListener(queues = TOPIC_QUEUE_COMMON)
    public void receiveTopicCommonMessage(Message message){
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("模拟接收 {} 类型,路由键为:{}的消息:{}", TOPIC,TOPIC_ROUTING_KEY_COMMON,msg);
    }
    
    
    /**
     * 绑定死信队列，autoAck，default-requeue-rejected=true
     * 只要抛 AmqpRejectAndDontRequeueException 就可以次路由到死信队列
     *
     */
    @RabbitListener(queues = TOPIC_QUEUE_ERROR)
    public void receiveTopicErrorMessageError(Message message) {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);

        log.info("模拟接收 {} 类型,路由键为:{}的消息:{}", TOPIC,TOPIC_ROUTING_KEY_ERROR,msg);
        throw new AmqpRejectAndDontRequeueException("模拟消费消息失败-AmqpRejectAndDontRequeueException");
       
    }
    
}
