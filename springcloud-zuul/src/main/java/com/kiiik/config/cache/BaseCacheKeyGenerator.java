package com.kiiik.config.cache;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.kiiik.pub.contant.KiiikContants;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年11月20日
 */
@Component("cacheKeyGenerator")
// 开启缓存
@EnableCaching
public class BaseCacheKeyGenerator implements KeyGenerator {
	Log log = LogFactory.getLog(BaseCacheKeyGenerator.class);

	// private Duration timeToLive = Duration.ZERO;
	@Override
	public Object generate(Object target, Method method, Object... params) {
		try {
			return new BaseCacheKey(target, method, params).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return KiiikContants.ERROR_KEY_DEFALUT;
		}
	}

	/*
	 * 默认的处理方式
	 * 
	 * @Bean public CacheManager cacheManager(RedisTemplate<?,?> redisTemplate)
	 * {
	 * 
	 * redisTemplate.setKeySerializer(keySerializer());
	 * redisTemplate.setValueSerializer(valueSerializer());
	 * redisTemplate.setHashKeySerializer(keySerializer());
	 * redisTemplate.setHashValueSerializer(valueSerializer());
	 * 
	 * RedisCacheManager redisCacheManager= new
	 * RedisCacheManager(redisTemplate);
	 * redisCacheManager.setDefaultExpiration(3600); Map<String,Long>
	 * expiresMap=new HashMap<>(); expiresMap.put("Product",5L);
	 * redisCacheManager.setExpires(expiresMap); return redisCacheManager; }
	 */
	// 定制的处理方式
	@Bean
	public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
		redisTemplate.setKeySerializer(stringKeySerializer());
		redisTemplate.setValueSerializer(valueSerializer());
		redisTemplate.setHashKeySerializer(stringKeySerializer());
		redisTemplate.setHashValueSerializer(valueSerializer());

		CustomizeRedisCacheManager redisCacheManager = new CustomizeRedisCacheManager(redisTemplate);
		redisCacheManager.setDefaultExpiration(3600);
		Map<String, Long> expiresMap = new HashMap<>();
		expiresMap.put("Product", 5L);
		redisCacheManager.setExpires(expiresMap);
		return redisCacheManager;
	}

	@Bean(name = "redisTemplate")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		redisTemplate.setKeySerializer(stringKeySerializer());
		redisTemplate.setHashKeySerializer(stringKeySerializer());
		redisTemplate.setValueSerializer(valueSerializer());
		redisTemplate.setHashValueSerializer(valueSerializer());

		log.debug("自定义RedisTemplate加载完成");
		return redisTemplate;
	}

	
	public JdkSerializationRedisSerializer jdkKeySerializer() {
		return new JdkSerializationRedisSerializer();
	}
	
	public StringRedisSerializer stringKeySerializer() {
		return new StringRedisSerializer();
	}

	private RedisSerializer<Object> valueSerializer() {
		return new GenericJackson2JsonRedisSerializer();
	}
}
