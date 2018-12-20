package com.kiiik.web.example.task;
import org.quartz.Job;  
import org.quartz.JobExecutionContext;  
import org.quartz.JobExecutionException;  
 //如何使用容器里的service 
public class HelloWorld implements Job{  
  
    @Override  
    public void execute(JobExecutionContext context)  
            throws JobExecutionException {  
        System.err.println("Hello World....");  
    }  
  
}  