package com.cyb.app.bms;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cyb.utils.context.TimeContext;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月29日
 */
public class BMSJob implements Job{
	Log log = LogFactory.getLog(BMSJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			//context.getJobDetail().getJobDataMap().get("param1");
			TimeContext.recordTimeStart();
			BmsDataCheckWorker.execTask();
			TimeContext.calExecuteTime("任务执行结束！~~~~~~~~~~~~~~~~~~~");
		}catch(Exception e){
			System.out.println("错误被忽略！");
		}
	}
}
