package com.swagger.offline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;

/**
 * @Title: 启动类
 * @ClassName: App.java
 * @Description:
 *
 * @Copyright 2016-2018  - Powered By 研发中心
 * @author: iechenyb
 * @date: 2018-03-08 10:22
 * @version V1.0
 */
@SpringBootApplication
public class App implements EmbeddedServletContainerCustomizer{
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(App.class); 
		} 
	
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		 //指定项目名称
        container.setContextPath("/demo");
        //指定端口地址
        container.setPort(8090);
	}

}
