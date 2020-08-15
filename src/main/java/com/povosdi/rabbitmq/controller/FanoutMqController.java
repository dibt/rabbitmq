package com.povosdi.rabbitmq.controller;


import static org.springframework.amqp.core.MessageProperties.CONTENT_TYPE_JSON;

import com.povosdi.rabbitmq.model.MessageBody;
import com.povosdi.rabbitmq.producter.FanoutProducer;
import com.povosdi.rabbitmq.utils.DateUtils;
import com.povosdi.rabbitmq.utils.JackJsonUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： povosdi
 * @date: 2020/8/3 下午10:03
 * @email：dibenteng@hzsuidifu.com
 */
@RestController
public class FanoutMqController {
    @Resource
    private FanoutProducer fanoutProducer;
    /**
     *  http://127.0.0.1:8023/fanout
     */
    @GetMapping("fanout")
    public String fanoutTest(HttpServletRequest request,HttpServletResponse response){
        MessageBody messageBody = new MessageBody(1000,"fanout类型messageBody,当前时间："+ DateUtils.currentDateToyMdHmS());
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(CONTENT_TYPE_JSON);
        messageProperties.setPublishSequenceNumber(1L);
        messageProperties.setHeader("exchangeType","fanout");
        messageProperties.setContentEncoding("UTF-8");
        Message message = new Message(JackJsonUtils.toJsonString(messageBody).getBytes(),messageProperties);
        
        fanoutProducer.sendFanoutMessage(message);
        return request.getRequestURL().toString()+" success";
    }





}
