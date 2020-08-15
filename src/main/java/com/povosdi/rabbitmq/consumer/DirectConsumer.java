package com.povosdi.rabbitmq.consumer;

import static com.povosdi.rabbitmq.configuration.DirectMqConfig.DIRECT_QUEUE;

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
public class DirectConsumer {
    
    @RabbitListener(queues = DIRECT_QUEUE)
    public void receiverDirectMessage(Message message){
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("模拟接收{}类型的消息:{}", DIRECT_QUEUE,msg);
    }
    
}
