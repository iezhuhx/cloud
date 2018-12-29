package com.cyb;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableEurekaClient
@EnableZipkinServer //zipkin服务器 默认使用http进行通信
//@EnableZipkinStreamServer //采用stream方式启动zipkin server
public class ZipkinServerApplication {
	EnableZipkinServer x;
    public static void main(String[] args) {
        SpringApplication.run(ZipkinServerApplication.class, args);
    }
    @Bean
    public MultipartConfigElement multipartConfigElement(){
    	MultipartConfigFactory factory = new MultipartConfigFactory();
    	factory.setMaxFileSize(100*1024L*1024L);
    	return factory.createMultipartConfig();
    }
}
