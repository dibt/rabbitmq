package com.povosdi.rabbitmq.controller;


import static org.springframework.amqp.core.MessageProperties.CONTENT_TYPE_JSON;

import com.povosdi.rabbitmq.model.MessageBody;
import com.povosdi.rabbitmq.producter.DirectProducer;
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
public class DirectMqController {
    @Resource
    private DirectProducer directProducer;
    
    @GetMapping("direct")
    public String directTest(HttpServletRequest request,HttpServletResponse response){
        //{"headers":{"exchangeType":"direct"},"timestamp":null,"messageId":null,"userId":null,"appId":null,"clusterId":null,"type":null,"correlationId":null,"replyTo":null,"contentType":"application/json","contentEncoding":"UTF-8","contentLength":0,"deliveryMode":"PERSISTENT","expiration":null,"priority":0,"redelivered":null,"receivedExchange":null,"receivedRoutingKey":null,"receivedUserId":null,"deliveryTag":0,"messageCount":null,"consumerTag":null,"consumerQueue":null,"receivedDelay":null,"receivedDeliveryMode":null,"finalRetryForMessageWithNoId":false,"publishSequenceNumber":1,"lastInBatch":false,"inferredArgumentType":null,"targetMethod":null,"targetBean":null,"delay":null,"replyToAddress":null,"xdeathHeader":null}
        MessageBody messageBody = new MessageBody(1000,"direct类型messageBody,当前时间："+ DateUtils.currentDateToyMdHmS());
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(CONTENT_TYPE_JSON);
        messageProperties.setPublishSequenceNumber(1L);
        messageProperties.setHeader("exchangeType","direct");
        messageProperties.setContentEncoding("UTF-8");
        Message message = new Message(JackJsonUtils.toJsonString(messageBody).getBytes(),messageProperties);
        directProducer.sendDirectMessage(message);
        return request.getRequestURL().toString()+" success";
    }
    /**
     * 先从总体的情况分析，推送消息存在四种情况：
     *
     * 消息推送到server，但是在server里找不到交换机-触发的是 ConfirmCallback
     * 消息推送到server，找到交换机了，但是没找到队列 -触发的是 ConfirmCallback和 RetrunCallback两个回调函数 注意ConfirmCallback的ack 为 true
     * 消息推送到sever，交换机和队列都没找到-触发的是 ConfirmCallback
     * 消息推送成功-触发的是 ConfirmCallback
     */
    @GetMapping("direct/no-queue-error")
    public String directNoQueueErrorTest(HttpServletRequest request,HttpServletResponse response) {
        MessageBody messageBody = new MessageBody(1000,"direct类型messageBody,当前时间："+ DateUtils.currentDateToyMdHmS());
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(CONTENT_TYPE_JSON);
        messageProperties.setPublishSequenceNumber(1L);
        messageProperties.setHeader("exchangeType","direct");
        messageProperties.setContentEncoding("UTF-8");
        Message message = new Message(JackJsonUtils.toJsonString(messageBody).getBytes(),messageProperties);
        directProducer.sendNoQueueErrorDirectMessage(message);
        return request.getRequestURL().toString()+" success";
    }
   
    
}
