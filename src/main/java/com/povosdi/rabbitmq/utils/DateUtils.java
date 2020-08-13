package com.povosdi.rabbitmq.utils;

import java.util.Date;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author： povosdi
 * @date: 2020/8/11 下午12:40
 * @email：dibenteng@hzsuidifu.com
 */
public class DateUtils {
    public static final String DEFAULT = "yyyy-MM-dd HH:mm:SS";
    public static final String yyyyMMddHHmmSS = "yyyy-MM-dd HH:mm:SS";
    
    public static String currentDateToyMdHmS(){
        return DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:SS");
    }
    
}
