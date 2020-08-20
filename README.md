# DLQ Dead Letter Queue
 new SimpleRabbitListenerContainerFactory()
        ConditionalRejectingErrorHandler
        
RabbitListenerErrorHandler 针对不同的监听器针对 Error 制定不同的 Handler
        
ErrorHandler 全局异常处理接口
handler 后台使用 FatalExceptionStrategy 检查异常等级是否为 fatal，如果为 fatal，失败消息将被 reject  
默认情况下，下列为 fatal 异常：
  - org.springframework.messaging.converter.MessageConversionException
  - org.springframework.amqp.support.converter.MessageConversionException
  - MethodArgumentTypeMismatchException  
  - MethodArgumentNotValidException
  - NoSuchMethodException
  - ClassCastException
  