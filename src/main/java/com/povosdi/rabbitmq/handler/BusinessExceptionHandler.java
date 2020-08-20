package com.povosdi.rabbitmq.handler;

import static com.povosdi.rabbitmq.configuration.DlqConfiguration.DEAD_LETTER_EXCHANGE;
import static com.povosdi.rabbitmq.configuration.DlqConfiguration.DEAD_LETTER_QUEUE_ROUTING_KEY_RETRY;

import com.povosdi.rabbitmq.exception.BusinessException;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

/**
 * @author： povosdi
 * @date: 2020/8/17 下午7:59
 * @email：dibenteng@hzsuidifu.com
 */
@Slf4j
@Component
public class BusinessExceptionHandler implements RabbitListenerErrorHandler {
    
    @Resource
    private RabbitTemplate rabbitTemplate;
    
    @Override
    public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> message,
        ListenerExecutionFailedException exception){
        log.error("这里模拟针对不同的监听器做不同的异常处理");
        if(!(exception.getCause() instanceof BusinessException)){
            log.error("只支持 BusinessException 类型的异常重新入队列,该消息:{}消费异常，转交给全局异常处理",amqpMessage.toString());
            throw exception;
        }
        rabbitTemplate.convertAndSend(DEAD_LETTER_EXCHANGE,DEAD_LETTER_QUEUE_ROUTING_KEY_RETRY, amqpMessage);
        return null;
    }
}
