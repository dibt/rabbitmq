package com.povosdi.rabbitmq.exception;

/**
 * 业务异常信息
 * @author： povosdi
 * @date: 2020/8/3 下午8:44
 * @email：dibenteng@hzsuidifu.com
 */
public class BusinessException extends GlobalException {
    
    
    public BusinessException(String message) {
        super(message);
    }
}
