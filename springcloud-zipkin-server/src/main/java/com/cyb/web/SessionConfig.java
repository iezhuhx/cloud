package com.cyb.web;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年10月15日
 */
/*@EnableRedisHttpSession(maxInactiveIntervalInSeconds= 1800)
public class SessionConfig {
	Log log = LogFactory.getLog(SessionConfig.class);
	// 冒号后的值为没有配置文件时，制动装载的默认值
	@Value("${redis.hostname:localhost}")
	String HostName;
	@Value("${redis.port:6379}")
	int Port;

	@Bean public JedisConnectionFactory connectionFactory() { 
		JedisConnectionFactory connection = new JedisConnectionFactory(); 
		connection.setPort(6011);
		connection.setHostName("192.168.108.119"); 
		return connection; 
		}

}*/
