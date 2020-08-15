package com.povosdi.rabbitmq.consumer;

import static com.povosdi.rabbitmq.configuration.FanoutMqConfig.FANOUT_QUEUE_ONE;
import static com.povosdi.rabbitmq.configuration.FanoutMqConfig.FANOUT_QUEUE_THREE;
import static com.povosdi.rabbitmq.configuration.FanoutMqConfig.FANOUT_QUEUE_TWO;
import static org.springframework.amqp.core.ExchangeTypes.FANOUT;

import com.povosdi.rabbitmq.exception.BusinessException;
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
public class FanoutConsumer {

   
    @RabbitListener(queues = FANOUT_QUEUE_ONE)
    public void receiverFanoutOneMessage(Message message){
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("模拟接收 {} 类型 {} 队列的消息:{}", FANOUT,FANOUT_QUEUE_ONE,msg);
    }
    @RabbitListener(queues = FANOUT_QUEUE_TWO)
    public void receiverFanoutTwoMessage(Message message){
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("模拟接收 {} 类型 {} 队列的消息:{}", FANOUT,FANOUT_QUEUE_TWO,msg);
    }
    @RabbitListener(queues = FANOUT_QUEUE_THREE)
    public void receiverDirectMessageError(Message message){
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("模拟接收 {} 类型 {} 队列的消息:{}", FANOUT,FANOUT_QUEUE_THREE,msg);
        throw new BusinessException("异常");
    }
    
}
