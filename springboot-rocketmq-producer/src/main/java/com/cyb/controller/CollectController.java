package com.cyb.controller;


import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.rocketmq.RocketMQProvider;


@RestController
@RequestMapping("mq")
@EnableScheduling
public class CollectController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    //注入jsmtemplate
    @Autowired
	RocketMQProvider rocketMQProvider;

    @RequestMapping("/start")
	public String testMq() {
		rocketMQProvider.defaultMQProducer();
		return "success";
	}
    
    @RequestMapping("/push")
    public String pushMsg(String msg) {
        return rocketMQProvider.send("TopicTest", "push", msg);
    }
    
    @Scheduled(cron = "0/1 * * * * ?")
	 public void send(){
		 String message = UUID.randomUUID().toString();
		 rocketMQProvider.send("TopicTest", "push", message);
	 }

}
