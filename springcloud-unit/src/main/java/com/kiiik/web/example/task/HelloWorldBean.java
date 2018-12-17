package com.kiiik.web.example.task;
import org.quartz.Job;  
import org.quartz.JobExecutionContext;  
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;  
 //如何使用容器里的service

import com.kiiik.web.example.service.TestService;
@Component
public class HelloWorldBean implements Job{  
  
	@Autowired
	TestService service;
	
    @Override  
    public void execute(JobExecutionContext context)  
            throws JobExecutionException {  
        System.err.println(service+"Hello World....");  
    }  
  
}  