package com.cyb.app.stock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cyb.app.holiday.HolidayH2DbUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月29日
 */
public class StockJob implements Job{
	Log log = LogFactory.getLog(StockJob.class);
	@Override
	public synchronized void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			if(HolidayH2DbUtils.isTradeDay()){
				JobDataMap data = context.getJobDetail().getJobDataMap();
				QutoesWindow.execTask(data);
			}
		}catch(Exception e){
			System.out.println("错误被忽略！");
		}
	}
}
