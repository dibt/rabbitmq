package com.povosdi.rabbitmq.producter;

import static com.povosdi.rabbitmq.configuration.FanoutMqConfig.FANOUT_EXCHANGE;
import static org.springframework.amqp.core.ExchangeTypes.FANOUT;

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

    }
    
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {

    }
    
    
  
}
