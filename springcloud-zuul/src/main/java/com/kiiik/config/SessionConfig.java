package com.kiiik.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年10月15日
 */
@Configurable
public class SessionConfig {
	
	@Value("${server.session.timeout}")
    private int sessionTimeout;
 
    @Primary
    @Bean
    public RedisOperationsSessionRepository sessionRepository(
        @Qualifier("sessionRedisTemplate") RedisOperations<Object, Object> sessionRedisTemplate,
        ApplicationEventPublisher applicationEventPublisher) {
        RedisOperationsSessionRepository sessionRepository = new RedisOperationsSessionRepository(sessionRedisTemplate);
        sessionRepository.setApplicationEventPublisher(applicationEventPublisher);
        sessionRepository.setDefaultMaxInactiveInterval(sessionTimeout);
        return sessionRepository;
    }
    
	/*@Bean public JedisConnectionFactory connectionFactory() { 
		 RedisStandaloneConfiguration redisStandaloneConfiguration =
	                new RedisStandaloneConfiguration();
	        redisStandaloneConfiguration.setHostName("localhost");
	        redisStandaloneConfiguration.setDatabase(0);
	        redisStandaloneConfiguration.setPassword();
	        redisStandaloneConfiguration.setPort(6380);
		return new JedisConnectionFactory(redisStandaloneConfiguration); 
	}*/

}
