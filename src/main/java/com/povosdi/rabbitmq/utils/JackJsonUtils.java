package com.povosdi.rabbitmq.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javax.annotation.Resource;

/**
 * @author： povosdi
 * @date: 2020/8/4 上午11:17
 * @email：dibenteng@hzsuidifu.com
 */
public class JackJsonUtils {
    @Resource
    private static ObjectMapper objectMapper;
    
    public static String toJsonString(Object object){
        if (objectMapper == null) {
            objectMapper = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }
    public static <T> T fromJson(String content, Class<T> valueType) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        }
        try {
            return objectMapper.readValue(content, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
