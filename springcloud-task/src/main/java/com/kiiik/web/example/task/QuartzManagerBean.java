package com.kiiik.web.example.task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Component;

import static org.quartz.JobBuilder.newJob;  
import static org.quartz.TriggerBuilder.newTrigger;  
import static org.quartz.CronScheduleBuilder.*;
/**
 *作者 : iechenyb<br>
 *类描述:根据corn执行任务 
 * 延时执行<br>
 *创建时间: 2018年12月10日
 */
@Component
public class QuartzManagerBean {
	    Log log = LogFactory.getLog(QuartzManagerBean.class);
	  
	    /** 
	     * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名 
	     *  
	     * @param sched 
	     *            调度器 
	     *  
	     * @param jobName 
	     *            任务名 
	     * @param cls 
	     *            任务 
	     * @param time 
	     *            时间设置，参考quartz说明文档 
	     *  
	     * @Title: QuartzManager.java 
	     */  
	    public  void addJob(Scheduler sched, String jobName,  
	            @SuppressWarnings("rawtypes") Class cls, String time
	            ,String job_group_name,String trigger_group_name) {  
	        try {  
	            JobKey jobKey = new JobKey(jobName, job_group_name);// 任务名，任务组，任务执行类  
	            @SuppressWarnings("unchecked") 
	            JobDetail jobDetail = newJob(cls).withIdentity(jobKey).build();  
	            TriggerKey triggerKey = new TriggerKey(jobName, trigger_group_name);// 触发器  
	            Trigger trigger = newTrigger().withIdentity(triggerKey)  
	                    .withSchedule(cronSchedule(time)).build();// 触发器时间设定  
	            sched.scheduleJob(jobDetail, trigger);  
	            if (!sched.isShutdown()) {  
	                sched.start();// 启动  
	            }  
	        } catch (Exception e) {  
	            throw new RuntimeException(e);  
	        }  
	    }  
	  
	    /** 
	     * @Description: 添加一个定时任务 
	     *  
	     * @param sched 
	     *            调度器 
	     *  
	     * @param jobName 
	     *            任务名 
	     * @param jobGroupName 
	     *            任务组名 
	     * @param triggerName 
	     *            触发器名 
	     * @param triggerGroupName 
	     *            触发器组名 
	     * @param jobClass 
	     *            任务 
	     * @param time 
	     *            时间设置，参考quartz说明文档 
	     *  
	     * @Title: QuartzManager.java 
	     */  
	    public  void addJob(Scheduler sched, String jobName,  
	            String jobGroupName, String triggerName, String triggerGroupName,  
	            @SuppressWarnings("rawtypes") Class jobClass, String time) {  
	        try {  
	            JobKey jobKey = new JobKey(jobName, jobGroupName);  
	            @SuppressWarnings("unchecked")  
	            JobDetail jobDetail = newJob(jobClass).withIdentity(jobKey).build();  
	            // 触发器  
	            TriggerKey triggerKey = new TriggerKey(triggerName,  
	                    triggerGroupName);  
	            Trigger trigger = newTrigger().withIdentity(triggerKey)  
	                    .withSchedule(cronSchedule(time)).build();  
	            sched.scheduleJob(jobDetail, trigger);  
	        } catch (Exception e) {  
	            throw new RuntimeException(e);  
	        }  
	    }  
	  
	    /** 
	     * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名) 
	     *  
	     * @param sched 
	     *            调度器 
	     * @param jobName 
	     * @param time 
	     *  
	     * @Title: QuartzManager.java 
	     */  
	    @SuppressWarnings("rawtypes")  
	    public  void modifyJobTime(Scheduler sched, String jobName,  
	            String time,String job_group_name,String trigger_group_name) {  
	        try {  
	            TriggerKey triggerKey = new TriggerKey(jobName, trigger_group_name);  
	            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);  
	            if (trigger == null) {  
	                return;  
	            }  
	            String oldTime = trigger.getCronExpression();  
	            if (!oldTime.equalsIgnoreCase(time)) {  
	                JobKey jobKey = new JobKey(jobName, job_group_name);  
	                JobDetail jobDetail = sched.getJobDetail(jobKey);  
	                Class objJobClass = jobDetail.getJobClass();  
	                removeJob(sched, jobName,job_group_name,trigger_group_name);  
	                System.out.println("修改任务："+jobName);  
	                addJob(sched, jobName, objJobClass, time,job_group_name,trigger_group_name);  
	            }  
	        } catch (Exception e) {  
	            throw new RuntimeException(e);  
	        }  
	    }  
	  
	    /** 
	     * @Description: 修改一个任务的触发时间 
	     *  
	     * @param sched 
	     *            调度器 * 
	     * @param sched 
	     *            调度器 
	     * @param triggerName 
	     * @param triggerGroupName 
	     * @param time 
	     *  
	     * @Title: QuartzManager.java 
	     */  
	    public  void modifyJobTime(Scheduler sched, String triggerName,  
	            String triggerGroupName, String time) {  
	        try {  
	            TriggerKey triggerKey = new TriggerKey(triggerName,  
	                    triggerGroupName);  
	            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);  
	            if (trigger == null) {  
	                return;  
	            }  
	            String oldTime = trigger.getCronExpression();  
	            if (!oldTime.equalsIgnoreCase(time)) {  
	                // 修改时间  
	                trigger.getTriggerBuilder().withSchedule(cronSchedule(time));  
	                // 重启触发器  
	                sched.resumeTrigger(triggerKey);  
	            }  
	        } catch (Exception e) {  
	            throw new RuntimeException(e);  
	        }  
	    }  
	  
	    /** 
	     * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名) 
	     *  
	     * @param sched 
	     *            调度器 
	     * @param jobName 
	     *  
	     * @Title: QuartzManager.java 
	     */  
	    public  void removeJob(Scheduler sched, String jobName
	    		,String job_group_name,String trigger_group_name) {  
	        try {  
	            TriggerKey triggerKey = new TriggerKey(jobName, trigger_group_name);  
	            sched.pauseTrigger(triggerKey);// 停止触发器  
	            sched.unscheduleJob(triggerKey);// 移除触发器  
	            JobKey jobKey = new JobKey(jobName, job_group_name);  
	            sched.deleteJob(jobKey);// 删除任务  
	        } catch (Exception e) {  
	            throw new RuntimeException(e);  
	        }  
	    }  
	  
	    /** 
	     * @Description: 移除一个任务 
	     *  
	     * @param sched 
	     *            调度器 
	     * @param jobName 
	     * @param jobGroupName 
	     * @param triggerName 
	     * @param triggerGroupName 
	     *  Trigger trigger=TriggerBuilder.newTrigger().withIdentity("lztJob", "zflJob") 
	     *  .withSchedule(CronScheduleBuilder.cronSchedule("* 7 10 * * ?")) 
	     *  .startNow().build();
	     * @Title: QuartzManager.java 
	     */  
	    public  void removeJob(Scheduler sched, String jobName,  
	            String jobGroupName, String triggerName, String triggerGroupName) {  
	        try {  
	            TriggerKey triggerKey = new TriggerKey(triggerName,  
	                    triggerGroupName);  
	            sched.pauseTrigger(triggerKey);// 停止触发器  
	            sched.unscheduleJob(triggerKey);// 移除触发器  
	            JobKey jobKey = new JobKey(jobName, jobGroupName);  
	            sched.deleteJob(jobKey);// 删除任务  
	        } catch (Exception e) {  
	            throw new RuntimeException(e);  
	        }  
	    }  
	  
	    /** 
	     * @Description:启动所有定时任务 
	     *  
	     * @param sched 
	     *            调度器 
	     *  
	     * @Title: QuartzManager.java 
	     */  
	    public  void startJobs(Scheduler sched) {  
	        try {  
	            sched.start();  
	        } catch (Exception e) {  
	            throw new RuntimeException(e);  
	        }  
	    }  
	  
	    /** 
	     * @Description:关闭所有定时任务 
	     *  
	     * @param sched 
	     *            调度器 
	     *  
	     */  
	    public  void shutdownJobs(Scheduler sched) {  
	        try {  
	            if (!sched.isShutdown()) {  
	                sched.shutdown();  
	            }  
	        } catch (Exception e) {  
	            throw new RuntimeException(e);  
	        }  
	    } 
}
