package com.povosdi.rabbitmq.producter;

import static com.povosdi.rabbitmq.configuration.FanoutMqConfig.FANOUT_EXCHANGE;
import static org.springframework.amqp.core.ExchangeTypes.FANOUT;

import com.povosdi.rabbitmq.utils.JackJsonUtils;
import com.povosdi.rabbitmq.utils.UUIDUtils;
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
public class FanoutProducer implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback{
    @Resource
    private RabbitTemplate rabbitTemplate;
    
    @PostConstruct
    private void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }
    
    
    public void sendFanoutMessage(Object message){
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUIDUtils.getUuid());
        log.info("模拟发送{}类型的消息:{},correlationData-id:{}", FANOUT,JackJsonUtils.toJsonString(message),correlationData.getId());
        rabbitTemplate.convertAndSend(FANOUT_EXCHANGE,"", message,correlationData);
    }
    
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack){
            if(Objects.isNull(correlationData.getReturnedMessage())){
                log.info("{}类型消息唯一标识id:{} 成功发送到队列",FANOUT,correlationData.getId());
                log.info("模拟{}类型消息成功发送到队列的操作",FANOUT);
                return;
            }
            log.error("{}类型消息唯一标识id:{} 成功发送到交换机:{}，但是没有找到对应队列",FANOUT,correlationData.getId(),
                correlationData.getReturnedMessage().getMessageProperties().getReceivedExchange());
            log.error("模拟{}类型消息成功发送到交换机但是没有发送到队列的操作,但是 ack 为true",FANOUT);
            return;
        }
        log.error("{}类型消息唯一标识id:{},失败原因,cause:{}",FANOUT,correlationData.getId(),cause);
        log.error("{}类型模拟消息没有发送到交换机的操作",FANOUT);
    }
    
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("消息主体 message:{}", JackJsonUtils.toJsonString(message));
        log.error("消息响应码 replyCode:{}", replyCode);
        log.error("响应描述 replyText:{}", replyText);
        log.error("消息使用的交换器 exchange:{}", exchange);
        log.error("消息使用的路由键 routingKey:{}", routingKey);
    }
    
    
  
}
