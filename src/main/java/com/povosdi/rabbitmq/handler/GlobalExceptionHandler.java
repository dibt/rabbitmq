package com.povosdi.rabbitmq.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.util.ErrorHandler;

/**
 * @author： povosdi
 * @date: 2020/8/19 下午1:59
 * @email：dibenteng@hzsuidifu.com
 */
@Slf4j
public class GlobalExceptionHandler implements ErrorHandler {
    private final FatalExceptionStrategy exceptionStrategy;
    
    public GlobalExceptionHandler(FatalExceptionStrategy exceptionStrategy) {
        this.exceptionStrategy = exceptionStrategy;
    }
    
    
    /**
     * 全局异常处理
     * 异常不是 GlobalException 直接丢弃掉
     * 否则路由到死信队列
     * @param throwable
     */
    @Override
    public void handleError(Throwable throwable) {
    
        log.error("这里模拟全局异常处理");
        if(exceptionStrategy.isFatal(throwable)){
            log.error("非 GlobalException 异常将会被拦截做异常等级检查，自定义异常等级检查就将会过滤掉非 GlobalException 的所有异常的消息",throwable.getCause());
            throw new AmqpRejectAndDontRequeueException("非 GlobalException 异常信息直接 reject");
        }
        
        
        
    }
    
}
