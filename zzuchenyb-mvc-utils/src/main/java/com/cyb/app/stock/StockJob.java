package com.cyb.app.stock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cyb.app.holiday.util.HolidayH2DbUtils;
import com.cyb.utils.date.DateRangeUtil;
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
				if(DateRangeUtil.isInTime("08:30-11:30")||
						DateRangeUtil.isInTime("13:00-15:20")	){
					JobDataMap data = context.getJobDetail().getJobDataMap();
					QutoesWindow.execTask(data);
				}else{
					System.out.println("不在指定的交易区间内！");
				}
			}else{
				System.out.println("今天非交易日！");
			}
		}catch(Exception e){
			System.out.println("错误被忽略！");
		}
	}
}
