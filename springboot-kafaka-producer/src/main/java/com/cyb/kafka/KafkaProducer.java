package com.cyb.kafka;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * 生产者
 * 使用@EnableScheduling注解开启定时任务
 */
@Component
@EnableScheduling
public class KafkaProducer {
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 定时任务
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void send(){
        String message = UUID.randomUUID().toString();
        System.err.println("发送消息！"+message);
        ListenableFuture<?> future = kafkaTemplate.send("test","msg", message);
        future.addCallback(
        		o -> System.out.println("send-消息发送成功：" + message), 
        		throwable -> System.out.println("消息发送失败：" + message));
    }
}
