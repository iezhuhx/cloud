package com.cyb.activemq;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 生产者
 * 使用@EnableScheduling注解开启定时任务
 */
@Component
@EnableScheduling
public class ActivemqProducer {
	
	  //注入jsmtemplate
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;


    /**
     * 定时任务
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void send(){
        String message = UUID.randomUUID().toString();
        System.err.println("发送消息！"+message);
        Map<String,String> map = new HashMap<String,String>();
        map.put("mobile", "13888888888");
        map.put("id", message);
        jmsMessagingTemplate.convertAndSend("my_map", map);
        System.out.println("map发送成功");
    }
}
