package com.povosdi.rabbitmq.producter;

import java.util.Objects;
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
public class DlqProducer implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback{
    @Resource
    private RabbitTemplate rabbitTemplate;
    
    @PostConstruct
    private void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }
    
//    public void sendDirectMessage(Object message){
//        CorrelationData correlationData = new CorrelationData();
//        correlationData.setId(UUID.randomUUID().toString());
//        log.info("模拟发送{}类型的消息:{}", FanoutMqConfig.DIRECT,JackJsonUtils.toJsonString(message));
//        rabbitTemplate.convertAndSend(FanoutMqConfig.DIRECT_EXCHANGE, FanoutMqConfig.DIRECT, message,correlationData);
//    }
//
//    public void sendTopicOneMessage(Object message){
//        CorrelationData correlationData = new CorrelationData();
//        correlationData.setId(UUID.randomUUID().toString());
//        log.info("模拟发送{}类型的消息:{}", FanoutMqConfig.TOPIC_QUEUE_ONE,JackJsonUtils.toJsonString(message));
//        rabbitTemplate.convertAndSend(FanoutMqConfig.TOPIC_EXCHANGE , FanoutMqConfig.TOPIC_ROUTING_KEY_ONE, message,correlationData);
//    }
//
//    public void sendTopicTwoMessage(Object message){
//        CorrelationData correlationData = new CorrelationData();
//        correlationData.setId(UUID.randomUUID().toString());
//        log.info("模拟发送{}类型的消息:{}", FanoutMqConfig.TOPIC_QUEUE_TWO,JackJsonUtils.toJsonString(message));
//        rabbitTemplate.convertAndSend(FanoutMqConfig.TOPIC_EXCHANGE , FanoutMqConfig.TOPIC_ROUTING_KEY_TWO, message,correlationData);
//    }
//    public void sendTopicErrorMessage(Object message){
//        CorrelationData correlationData = new CorrelationData();
//        correlationData.setId(UUID.randomUUID().toString());
//        log.info("模拟发送{}类型的消息:{}", FanoutMqConfig.TOPIC_QUEUE_ERROR,JackJsonUtils.toJsonString(message));
//        rabbitTemplate.convertAndSend(FanoutMqConfig.TOPIC_EXCHANGE , FanoutMqConfig.TOPIC_ROUTING_KEY_ERROR, message,correlationData);
//    }
//
//
//    public void sendFanoutMessage(Object message){
//        CorrelationData correlationData = new CorrelationData();
//        correlationData.setId(UUID.randomUUID().toString());
//        log.info("模拟发送{}类型的消息:{}", FanoutMqConfig.FANOUT,JackJsonUtils.toJsonString(message));
//        rabbitTemplate.convertAndSend(FanoutMqConfig.FANOUT_EXCHANGE,"", message,correlationData);
//    }
//
//    public void sendNoExchangeErrorDirectMessage(Object message){
//        CorrelationData correlationData = new CorrelationData();
//        correlationData.setId(UUID.randomUUID().toString());
//        log.info("模拟发送{}类型的消息:{}", FanoutMqConfig.DIRECT,JackJsonUtils.toJsonString(message));
//        rabbitTemplate.convertAndSend(FanoutMqConfig.DIRECT, FanoutMqConfig.DIRECT, message,correlationData);
//    }
//    public void sendNoQueueErrorDirectMessage(Object message) {
//        CorrelationData correlationData = new CorrelationData();
//        correlationData.setId(UUID.randomUUID().toString());
//        log.info("模拟发送{}类型的消息:{}", FanoutMqConfig.DIRECT,JackJsonUtils.toJsonString(message));
//        rabbitTemplate.convertAndSend(FanoutMqConfig.DIRECT_EXCHANGE_NO_QUEUE, FanoutMqConfig.DIRECT, message,correlationData);
//    }
    
    
    
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack){
            if(Objects.isNull(correlationData.getReturnedMessage())){
                log.info("消息唯一标识id:{}成功发送到队列",correlationData.getId());
                log.info("模拟消息成功发送到队列的操作");
                return;
            }
            log.error("消息唯一标识id:{}成功发送到交换机:{}，但是没有找到对应队列",correlationData.getId(),correlationData
                .getReturnedMessage().getMessageProperties().getReceivedExchange());
            log.error("模拟消息成功发送到交换机但是没有发送到队列的操作");
            return;
        }
        log.error("消息唯一标识id:{},失败原因,cause:{}",correlationData.getId(),cause);
        log.error("模拟消息没有发送到交换机的操作");
    }
    
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息发送失败id:{}", message.getMessageProperties().getHeaders().get("spring_returned_message_correlation"));
        log.info("消息主体 message:{}", message);
        log.info("消息主体 replyCode:{}", replyCode);
        log.info("描述:{}" + replyText);
        log.info("消息使用的交换器 exchange:{}", exchange);
        log.info("消息使用的路由键 routing:{}", routingKey);
    }
}
