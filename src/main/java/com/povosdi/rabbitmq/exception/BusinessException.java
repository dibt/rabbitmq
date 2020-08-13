package com.povosdi.rabbitmq.exception;

import org.springframework.amqp.AmqpException;

/**
 * @author： povosdi
 * @date: 2020/8/3 下午8:44
 * @email：dibenteng@hzsuidifu.com
 */
public class BusinessException extends AmqpException {
    
    
    public BusinessException(String message) {
        super(message);
    }
}
