package com.cyb.controller;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/activemq")
public class CollectController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    //注入jsmtemplate
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @RequestMapping("/sendMsg")
    public void sendMsg(String msg) {
        jmsMessagingTemplate.convertAndSend("my_msg", msg);
        System.out.println("msg发送成功");
    }

    @RequestMapping("/sendMap")
    public void sendMap() {
        Map<String,String> map = new HashMap<String,String>();
        map.put("mobile", "13888888888");
        map.put("content", "王总喜提兰博基尼");
        jmsMessagingTemplate.convertAndSend("my_map", map);
        System.out.println("map发送成功");
    }

}
