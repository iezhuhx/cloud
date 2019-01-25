package com.cyb.app.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;

import com.cyb.app.bms.BMSJob;
import com.cyb.app.csdn.CsdnJob;
import com.cyb.app.holiday.HolidayJob;
import com.cyb.app.stock.StockJob;
import com.cyb.utils.random.RandomUtils;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2019年1月16日
 */

public class GlobalTaskWindow {
	Log log = LogFactory.getLog(GlobalTaskWindow.class);
	static String job_name = "动态任务调度";
	static String cron = "*/10 * * * * ?";
	static List<Task> tasks = new ArrayList<>();
	
	static {
		tasks = new ArrayList<>();
		tasks.add(new Task(BMSJob.class,false,cron));
		tasks.add(new Task(CsdnJob.class,false,cron));
		tasks.add(new Task(StockJob.class,true,cron));
		tasks.add(new Task(HolidayJob.class,false,cron));
	}
	public static void main(String[] args) throws SchedulerException {
	    for(Task task:tasks){
	    	if(task.enable){
	    		QuartzManager
	    		.addJob(job_name+RandomUtils.getPassWord(8), 
	    				task.getTarget(),
	    				cron);
	    	}
	    }
	}
}


