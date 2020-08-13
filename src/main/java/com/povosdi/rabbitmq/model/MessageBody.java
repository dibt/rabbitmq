package com.povosdi.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author： povosdi
 * @date: 2020/8/7 下午2:00
 * @email：dibenteng@hzsuidifu.com
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class MessageBody {
    
    private Integer code;
    
    private String msg;
    
}
