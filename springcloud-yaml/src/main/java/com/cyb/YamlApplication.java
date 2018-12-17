package com.cyb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.cyb.property.YamlEntity;

@SpringBootApplication
public class YamlApplication  {
	@Autowired
	YamlEntity yml;
	
	public static void main(String[] args) {
		SpringApplication.
		run(YamlApplication.class, args);
	}
   
	/**
	 * ${from} 不存在时 直接将其作为值
	 * 设置@Value注解取值不到忽略（不报错）
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日
	 *@return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
	    PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
	    c.setIgnoreUnresolvablePlaceholders(true);
	    return c;
	}
}