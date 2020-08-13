package com.povosdi.rabbitmq.utils;

import java.util.UUID;

/**
 * @author： povosdi
 * @date: 2020/8/7 下午2:09
 * @email：dibenteng@hzsuidifu.com
 */
public class UUIDUtils {
    
    public static String getUuid(){
        return UUID.randomUUID().toString();
    }
    
}
