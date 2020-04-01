package com.cyb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableDiscoveryClient
@RestController
@EnableZuulProxy
@EnableEurekaClient
public class ZuulApplication {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Value("${from}")
	private String profile;

	@GetMapping("/profile")
	public String hello() {
		return this.profile;
	}

	// 项目启动时，读取配置文件参数做初始化使用
	@Autowired
	void setEnvironment(Environment env) {
		System.err.println("param from env: " + env.getProperty("from"));
	}

	/**
	 * ${from} 不存在时 直接将其作为值 设置@Value注解取值不到忽略（不报错） 作者 : iechenyb<br>
	 * 方法描述: 说点啥<br>
	 * 创建时间: 2017年7月15日
	 * 
	 * @return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
		c.setIgnoreUnresolvablePlaceholders(true);
		return c;
	}

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}

}
