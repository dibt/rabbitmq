package com.povosdi.rabbitmq.consumer;

import static com.povosdi.rabbitmq.configuration.TopicMqConfig.TOPIC_QUEUE_COMMON;
import static com.povosdi.rabbitmq.configuration.TopicMqConfig.TOPIC_QUEUE_ERROR;
import static com.povosdi.rabbitmq.configuration.TopicMqConfig.TOPIC_QUEUE_RIGHT;
import static com.povosdi.rabbitmq.configuration.TopicMqConfig.TOPIC_ROUTING_KEY_COMMON;
import static com.povosdi.rabbitmq.configuration.TopicMqConfig.TOPIC_ROUTING_KEY_ERROR;
import static com.povosdi.rabbitmq.configuration.TopicMqConfig.TOPIC_ROUTING_KEY_RIGHT;
import static org.springframework.amqp.core.ExchangeTypes.TOPIC;

import com.povosdi.rabbitmq.exception.BusinessException;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
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

   
    @RabbitListener(queues = TOPIC_QUEUE_RIGHT)
    public void receiveTopicRightMessage(Message message){
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("模拟接收 {} 类型,路由键为:{}的消息:{}", TOPIC,TOPIC_ROUTING_KEY_RIGHT,msg);
    }
    @RabbitListener(queues = TOPIC_QUEUE_COMMON)
    public void receiveTopicCommonMessage(Message message){
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("模拟接收 {} 类型,路由键为:{}的消息:{}", TOPIC,TOPIC_ROUTING_KEY_COMMON,msg);
    }

    @RabbitListener(queues = TOPIC_QUEUE_ERROR)
    public void receiveTopicErrorMessageError(Message message,Channel channel) throws IOException {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        log.info("模拟接收 {} 类型,路由键为:{}的消息:{}", TOPIC,TOPIC_ROUTING_KEY_ERROR,msg);
      
        channel.basicReject(deliveryTag,false);
        throw new BusinessException("模拟消费消息失败");
    }
    
}
