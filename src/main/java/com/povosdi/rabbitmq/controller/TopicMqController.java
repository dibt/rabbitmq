package com.povosdi.rabbitmq.controller;


import static org.springframework.amqp.core.MessageProperties.CONTENT_TYPE_JSON;

import com.povosdi.rabbitmq.model.MessageBody;
import com.povosdi.rabbitmq.producter.TopicProducer;
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
public class TopicMqController {
    @Resource
    private TopicProducer topicProducer;
    
    /**
     * http://127.0.0.1:8023/topic/right
     */
    @GetMapping("topic/right")
    public String topicRightTest(HttpServletRequest request,HttpServletResponse response){
        topicProducer.sendTopicRightMessage(getTopicMessage());
        return request.getRequestURL().toString()+" success";
    }
    
    
    /**
     * http://127.0.0.1:8023/topic/common
     */
    @GetMapping("topic/common")
    public String topicCommonTest(HttpServletRequest request,HttpServletResponse response) {
        topicProducer.sendTopicCommonMessage(getTopicMessage());
        return request.getRequestURL().toString() + " success";
    }
    
    /**
     * http://127.0.0.1:8023/topic/error
     */
    @GetMapping("topic/error")
    public String topicErrorTest(HttpServletRequest request,HttpServletResponse response){
        topicProducer.sendTopicErrorMessage(getTopicMessage());
        return request.getRequestURL().toString() + " success";
    }
    
    private Message getTopicMessage() {
        MessageBody messageBody = new MessageBody(1000,"topic类型messageBody,当前时间："+ DateUtils.currentDateToyMdHmS());
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(CONTENT_TYPE_JSON);
        messageProperties.setPublishSequenceNumber(1L);
        messageProperties.setHeader("exchangeType","topic");
        messageProperties.setContentEncoding("UTF-8");
        return new Message(JackJsonUtils.toJsonString(messageBody).getBytes(),messageProperties);
    }





}
