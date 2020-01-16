package com.kiiik.web.example.task1.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import com.kiiik.web.example.service.TestService;
import com.kiiik.web.example.task1.ann.KiiikTask;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年12月12日
 */
@Service
@KiiikTask(cron="*/10 * * * * ?",startJobOnStartUp=true)
public class SpringBeanJob extends QuartzJobBean {
   Log log = LogFactory.getLog(getClass());

	@Autowired
	TestService service;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		//通过this.getClass();获取注解信息。
		// 传入的参数
		JobDataMap params = context.getJobDetail().getJobDataMap();
		log.info(params.get("task")+",service=" + service);
		//读取是否允许并发的标记，然后进行判断，如果不允许并发，则需要需要判断是否有正在执行的任务。
	}
}
