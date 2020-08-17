package com.povosdi.rabbitmq.handler;

import com.povosdi.rabbitmq.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

/**
 * @author： povosdi
 * @date: 2020/8/17 下午7:59
 * @email：dibenteng@hzsuidifu.com
 */
@Component
@Slf4j
public class BusinessExceptionHandler implements RabbitListenerErrorHandler {
    
    @Override
    public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> message,
        ListenerExecutionFailedException exception){
        if(!(exception.getCause() instanceof BusinessException)){
            log.error("只支持 BusinessException 类型的异常重新入队列,该消息:{}消费异常，直接丢弃",amqpMessage.toString());
            return null;
        }
        return amqpMessage;
    }
}
