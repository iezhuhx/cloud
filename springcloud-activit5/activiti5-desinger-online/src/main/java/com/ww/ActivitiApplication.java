package com.ww;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//访问方式：http://localhost/model-list.html  http://localhost/index.html
@SpringBootApplication
@ComponentScan({"com.ww","org.activiti"})

public class ActivitiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivitiApplication.class, args);
	}
}
