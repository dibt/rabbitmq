package com.povosdi.rabbitmq.handler;

import com.povosdi.rabbitmq.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;

/**
 * @author： povosdi
 * @date: 2020/8/17 下午9:16
 * @email：dibenteng@hzsuidifu.com
 */
@Slf4j
public class GlobalFatalExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {
    
    @Override
    public boolean isFatal(Throwable t) {
        log.info("这里自定义检查异常等级是否为 fatal");
        if(t.getCause() instanceof GlobalException){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
        //return super.isFatal(t);
    }
}
