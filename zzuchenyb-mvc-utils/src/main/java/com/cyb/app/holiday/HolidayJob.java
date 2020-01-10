package com.cyb.app.holiday;
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
public class HolidayJob implements Job{
	Log log = LogFactory.getLog(HolidayJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			TimeContext.recordTimeStart();
			GoseekHolidayUtils.initHoliday();//抓取当年的节假日
			TimeContext.calExecuteTime("任务执行结束！~~~~~~~~~~~~~~~~~~~");
		}catch(Exception e){
			System.out.println("错误被忽略！");
		}
	}
}
