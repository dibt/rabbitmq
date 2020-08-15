package com.povosdi.rabbitmq.producter;

import static com.povosdi.rabbitmq.configuration.TopicMqConfig.TOPIC_EXCHANGE;
import static com.povosdi.rabbitmq.configuration.TopicMqConfig.TOPIC_ROUTING_KEY_COMMON;
import static com.povosdi.rabbitmq.configuration.TopicMqConfig.TOPIC_ROUTING_KEY_ERROR;
import static com.povosdi.rabbitmq.configuration.TopicMqConfig.TOPIC_ROUTING_KEY_RIGHT;
import static org.springframework.amqp.core.ExchangeTypes.TOPIC;

import com.povosdi.rabbitmq.utils.JackJsonUtils;
import com.povosdi.rabbitmq.utils.UUIDUtils;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author： povosdi
 * @date: 2020/8/3 下午8:37
 * @email：dibenteng@hzsuidifu.com
 */
@Slf4j
@Component
public class TopicProducer implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback{
    @Resource
    private RabbitTemplate rabbitTemplate;
    
    @PostConstruct
    private void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }
    
    
    public void sendTopicRightMessage(Object message){
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUIDUtils.getUuid());
        log.info("模拟发送{}类型的消息:{},correlationData-id:{}", TOPIC,JackJsonUtils.toJsonString(message),correlationData.getId());
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE,TOPIC_ROUTING_KEY_RIGHT, message,correlationData);
    }
    
    public void sendTopicCommonMessage(Object message){
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUIDUtils.getUuid());
        log.info("模拟发送{}类型的消息:{},correlationData-id:{}", TOPIC,JackJsonUtils.toJsonString(message),correlationData.getId());
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE,TOPIC_ROUTING_KEY_COMMON, message,correlationData);
    }
    public void sendTopicErrorMessage(Object message){
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUIDUtils.getUuid());
        log.info("模拟发送{}类型的消息:{},correlationData-id:{}", TOPIC,JackJsonUtils.toJsonString(message),correlationData.getId());
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE,TOPIC_ROUTING_KEY_ERROR, message,correlationData);
    }
    

   
    
    
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

    }
    
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {

    }
}
