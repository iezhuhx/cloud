package com.kiiik.web.example.task1;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import com.kiiik.web.example.service.TestService;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年12月12日
 */
@Service
public class SftpJob extends QuartzJobBean {
   Log log = LogFactory.getLog(getClass());

	@Autowired
	TestService service;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// 传入的参数
		JobDataMap params = context.getJobDetail().getJobDataMap();
		System.err.println(params.get("task")+",service=" + service);
	}
}
