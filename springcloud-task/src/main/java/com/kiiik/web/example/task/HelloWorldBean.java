package com.kiiik.web.example.task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;  
import org.quartz.JobExecutionContext;  
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;  
 //如何使用容器里的service

import com.kiiik.web.example.service.TestService;
@Component
public class HelloWorldBean implements Job{  
    Log log = LogFactory.getLog(getClass());
    
	@Autowired
	TestService service;//无法注入service
	
    @Override  
    public void execute(JobExecutionContext context)  
            throws JobExecutionException {  
        log.info(service+"  Hello World....");  
    }  
  
}  