package com.povosdi.rabbitmq.utils;

/**
 * @author： povosdi
 * @date: 2020/8/15 下午4:18
 * @email：dibenteng@hzsuidifu.com
 */
public class ThreadUtils {
    public static String getClassAndMethod(){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        return stackTraceElement.getClassName()+"-"+stackTraceElement.getMethodName();
    }
    
}
