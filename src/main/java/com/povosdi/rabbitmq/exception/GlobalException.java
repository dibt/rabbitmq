package com.povosdi.rabbitmq.exception;

import org.springframework.amqp.AmqpException;

/**
 * 全局异常信息
 * @author： povosdi
 * @date: 2020/8/18 下午7:34
 * @email：dibenteng@hzsuidifu.com
 */
public class GlobalException extends AmqpException {
    
    
    public GlobalException(String message) {
        super(message);
    }
    
}
