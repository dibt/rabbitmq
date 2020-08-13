package com.povosdi.rabbitmq.consumer;

import com.povosdi.rabbitmq.configuration.TopicMqConfig;
import com.povosdi.rabbitmq.exception.BusinessException;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author： povosdi
 * @date: 2020/8/3 下午8:43
 * @email：dibenteng@hzsuidifu.com
 */
@Slf4j
@Component
public class TopicConsumer {
    @Resource
    private RabbitTemplate rabbitTemplate;
   
    @RabbitListener(queues = TopicMqConfig.TOPIC_QUEUE_ONE)
    public void receiverTopicOneMessage(Message message){
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("模拟接收{}类型的消息:{}", TopicMqConfig.TOPIC_ROUTING_KEY_ONE,msg);
    }
    @RabbitListener(queues = TopicMqConfig.TOPIC_QUEUE_TWO)
    public void receiverTopicTwoMessage(Message message){
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("模拟接收{}类型的消息:{}", TopicMqConfig.TOPIC_ROUTING_KEY_TWO,msg);
    }

    @RabbitListener(queues = TopicMqConfig.TOPIC_QUEUE_ERROR)
    public void receiverTopicErrorMessageError(Message message,Channel channel) throws IOException {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        log.info("模拟接收{}类型的消息:{}", TopicMqConfig.TOPIC_ROUTING_KEY_ERROR,msg);
        channel.basicReject(deliveryTag,false);
        throw new BusinessException("模拟消费消息失败");
    }
//    @RabbitListener(queues = RabbitMqConfig.DEAD_LETTER_QUEUE)
//    public void receiverDeadLetterQueueMessage(Message message){
//        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
//        log.info("模拟接收{}类型的消息:{}", RabbitMqConfig.DEAD_LETTER_QUEUE_ROUTING_KEY,msg);
//    }
    
}
