//package com.povosdi.rabbitmq.controller;
//
//
//import com.povosdi.rabbitmq.producter.DlqProducer;
//import java.util.Date;
//import javax.annotation.Resource;
//import org.apache.commons.lang3.time.DateFormatUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author： povosdi
// * @date: 2020/8/3 下午10:03
// * @email：dibenteng@hzsuidifu.com
// */
//@RestController
//public class DlqMqController {
//    @Resource
//    private DlqProducer producer;
//
//    @GetMapping("direct")
//    public String directTest(){
//        String message = "direct message";
//        producer.sendDirectMessage(message+" "+DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:SS"));
//        return "success";
//    }
//    @GetMapping("topic/one")
//    public String topicOneTest(){
//        String message = "topic message";
//        producer.sendTopicOneMessage(message+" "+DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:SS"));
//        return "success";
//    }
//    @GetMapping("topic/two")
//    public String topicTwoTest(){
//        String message = "topic message";
//        producer.sendTopicTwoMessage(message+" "+DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:SS"));
//        return "success";
//    }
//    @GetMapping("fanout")
//    public String fanoutTest(){
//        String message = "fanout message";
//        producer.sendFanoutMessage(message+" "+DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:SS"));
//        return "success";
//    }
//    @GetMapping("direct/no-exchange-error")
//    public String directNoExchangeErrorTest(){
//
//        String message = "direct message";
//        producer.sendNoExchangeErrorDirectMessage(message+" "+DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:SS"));
//        return "success";
//    }
//
//    /**
//     * 先从总体的情况分析，推送消息存在四种情况：
//     *
//     * 消息推送到server，但是在server里找不到交换机-触发的是 ConfirmCallback
//     * 消息推送到server，找到交换机了，但是没找到队列 -触发的是 ConfirmCallback和 RetrunCallback两个回调函数 注意ConfirmCallback的ack 为 true
//     * 消息推送到sever，交换机和队列啥都没找到-触发的是 ConfirmCallback
//     * 消息推送成功-触发的是 ConfirmCallback
//     */
//    @GetMapping("direct/no-queue-error")
//    public String directNoQueueErrorTest() throws InterruptedException {
//        String message = "direct message";
//
//        producer.sendNoQueueErrorDirectMessage(message+" "+DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:SS"));
//        return "success";
//    }
//
//
//
//    @GetMapping("topic/receive-error")
//    public String fanoutReceiveErrorTest(){
//        String message = "topic message";
//        producer.sendTopicErrorMessage(message+" "+DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:SS"));
//        return "success";
//    }
//
//
//
//
//
//}
