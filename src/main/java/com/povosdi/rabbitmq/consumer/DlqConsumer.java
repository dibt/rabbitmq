package com.povosdi.rabbitmq.consumer;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author： povosdi
 * @date: 2020/8/3 下午8:43
 * @email：dibenteng@hzsuidifu.com
 */
@Slf4j
@Component
public class DlqConsumer {
    @Resource
    private RabbitTemplate rabbitTemplate;
   
//    @RabbitListener(queues = RabbitMqConfig.DEAD_LETTER_QUEUE)
//    public void receiverDeadLetterQueueMessage(Message message){
//        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
//        log.info("模拟接收{}类型的消息:{}", RabbitMqConfig.DEAD_LETTER_QUEUE_ROUTING_KEY,msg);
//    }
    
}
