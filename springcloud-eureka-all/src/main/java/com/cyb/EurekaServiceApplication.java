package com.cyb;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
//https://gitee.com/didispace/SpringCloud-Learning
//http://edu.51cto.com/center/course/lesson/index?id=202533
@EnableEurekaServer        
@SpringBootApplication     
public class EurekaServiceApplication{
	 @ResponseBody
	    @RequestMapping(value="/")
	    String location(){
	        return "123";
	    }
	 //根据不通的profiles读取启动文件配置
	@SuppressWarnings("resource")
	public static void myStart(String[] args){
		Scanner scan = new Scanner(System.in);
		String profiles = scan.nextLine();
		new SpringApplicationBuilder(EurekaServiceApplication.class)
		.profiles(profiles).run(args);
	} 
    public static void main(String[] args) {
        SpringApplication
        .run(EurekaServiceApplication.class, args);
        
        
    }
    /*@SuppressWarnings("unchecked")
	@Bean
    public EmbeddedServletContainerFactory servletContainer(){
    	TomcatEmbeddedServletContainerFactory factory= new TomcatEmbeddedServletContainerFactory();
    	factory.setPort(8888);
    	factory.setSessionTimeout(10,TimeUnit.MINUTES);
    	factory.setErrorPages((Set<ErrorPage>) new ErrorPage(HttpStatus.NOT_FOUND,"/404.html"));
    	return factory;
    }*/
	/*public void customize(ConfigurableEmbeddedServletContainer c) {
		c.setPort(8888);
		
	}*/
}