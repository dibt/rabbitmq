package com.povosdi.rabbitmq.consumer;

import static com.povosdi.rabbitmq.configuration.DlqConfiguration.DEAD_LETTER_QUEUE_RETRY;
import static com.povosdi.rabbitmq.configuration.DlqConfiguration.DEAD_LETTER_QUEUE_ROUTING_KEY_RETRY;
import static org.springframework.amqp.core.ExchangeTypes.TOPIC;

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
public class DlqConsumer {

   
    @RabbitListener(queues = DEAD_LETTER_QUEUE_RETRY)
    public void receiverDeadLetterQueueMessage(Message message){
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("模拟接收 {} 类型,路由键为:{}的消息:{}", TOPIC,DEAD_LETTER_QUEUE_ROUTING_KEY_RETRY,msg);
        log.info("模拟消息消费失败进入到死信队列重试操作");

    }
    
}
