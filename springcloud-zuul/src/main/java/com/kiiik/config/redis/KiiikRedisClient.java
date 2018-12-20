package com.kiiik.config.redis;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年11月22日
 */
@Component
public class KiiikRedisClient {
	Log log = LogFactory.getLog(KiiikRedisClient.class);

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	public void batchDelByKey(String key) {
		Set<String> data = redisTemplate.keys(key+"*");
		for(String k:data){
			redisTemplate.delete(k);
		}
	}
}
