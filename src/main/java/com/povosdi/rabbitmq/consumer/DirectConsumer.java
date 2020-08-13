package com.povosdi.rabbitmq.consumer;

import com.povosdi.rabbitmq.configuration.DirectMqConfig;
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
public class DirectConsumer {
    @Resource
    private RabbitTemplate rabbitTemplate;
    
    @RabbitListener(queues = DirectMqConfig.DIRECT_QUEUE)
    public void receiverDirectMessage(Message message){
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("模拟接收{}类型的消息:{}", DirectMqConfig.DIRECT_QUEUE,msg);
    }
    
}
