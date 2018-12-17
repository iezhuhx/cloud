package com.kiiik.web.example.task1;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年12月13日
 */
public class Job1 extends QuartzJobBean {

	private static int i = 0;

	// 调度工厂实例化后，经过timeout时间开始执行调度
	public void setTimeout(int timeout) {
		
	}

	/**
	 * 要调度的具体任务
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		System.out.println("继承QuartzJobBean的方式-调度" + ++i + "进行中...");
	}
}