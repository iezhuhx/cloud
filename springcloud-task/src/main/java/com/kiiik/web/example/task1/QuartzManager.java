package com.kiiik.web.example.task1;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月12日
 */
//@Service
public class QuartzManager {
    @Autowired
    private SchedulerFactory scheduler;

    public QuartzManager(){}

    /**
     * 添加一个定时任务
     *
     * @param jobName           任务名
     * @param jobGroupName      任务组名
     * @param triggerName       触发器名
     * @param triggerGroupName  触发器组名
     * @param jobClass          任务
     * @param cron              时间设置，参考quartz说明文档
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addJob(String jobName, String jobGroupName,
    		String triggerName, String triggerGroupName,
    		Class jobClass, String cron, 
    		Map<String, Object> params) {
        try {
            // 任务名，任务组，任务执行类
            JobDetail job = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            // 任务参数
            job.getJobDataMap().putAll(params);

            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();

            // 调度容器设置JobDetail和Trigger
            scheduler.getScheduler().scheduleJob(job, trigger);
            //scheduler.startDelayed(3);延时启动
            // 启动
            if (!scheduler.getScheduler().isShutdown()) {
                scheduler.getScheduler().start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改一个任务的触发时间
     *
     * @param triggerName       触发器名
     * @param triggerGroupName  触发器组名
     * @param cron              时间设置，参考quartz说明文档
     */
    public  void modifyJobTime(String jobName,String job_group_name,String trigger_name,String trigger_group_name
            , String cron, Map<String, Object> params) {  
        try {  
            TriggerKey triggerKey = new TriggerKey(jobName, trigger_group_name);  
            CronTrigger trigger = (CronTrigger)  scheduler.getScheduler().getTrigger(triggerKey);  
            if (trigger == null) {  
                return;  
            }  
            String oldTime = trigger.getCronExpression();  
            if (!oldTime.equalsIgnoreCase(cron)) {  
                JobKey jobKey = new JobKey(jobName, job_group_name);  
                JobDetail jobDetail =  scheduler.getScheduler().getJobDetail(jobKey);  
                Class<?> targetJobClass = jobDetail.getJobClass();  
                removeJob(jobName,job_group_name,trigger_name,trigger_group_name);  
                System.out.println("修改任务："+jobName);  
                addJob(jobName,job_group_name,trigger_name,
                		trigger_group_name,targetJobClass,
                		cron,params);  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    } 

    /**
     * 移除一个任务
     *
     * @param jobName           任务名
     * @param jobGroupName      任务组名
     * @param triggerName       触发器名
     * @param triggerGroupName  触发器组名
     */
    public void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            // 停止触发器
            scheduler.getScheduler().pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.getScheduler().unscheduleJob(triggerKey);
            // 删除任务
            scheduler.getScheduler().deleteJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取任务是否存在
     *
     * STATE_BLOCKED 4 阻塞
     * STATE_COMPLETE 2 完成
     * STATE_ERROR 3 错误
     * STATE_NONE -1 不存在
     * STATE_NORMAL 0 正常
     * STATE_PAUSED 1 暂停
     * @throws SchedulerException 
     *
     */
    public  Boolean exists(String job_name, String JOB_GROUP_NAME) throws SchedulerException {
        /*try {
            return scheduler.getScheduler().getTriggerState(TriggerKey.triggerKey(triggerName, triggerGroupName)) == Trigger.TriggerState.NONE;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
    	
    	JobKey jobKey = new JobKey(job_name, JOB_GROUP_NAME);// 任务名，任务组，任务执行类  
		if(scheduler.getScheduler().checkExists(jobKey)){
			return true;
		}
		return false;
    }
    
    public  void startJobs() {  
        try {  
        	if (!scheduler.getScheduler().isStarted()) {  
        	scheduler.getScheduler().start();  
        	}
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
    public  void shutdownJobs() {  
        try {  
            if (!scheduler.getScheduler().isShutdown()) {  
            	scheduler.getScheduler().shutdown();  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    } 
    public TriggerState getState(JobKey jobKey){
		TriggerKey triggerKey = TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup());
		try {
			return scheduler.getScheduler().getTriggerState(triggerKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return null;
		}		
	}
    public List<ScheduleJobInfor> getAllJob() throws SchedulerException {  
        Scheduler scheduler = this.scheduler.getScheduler();  
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();  
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);  
        List<ScheduleJobInfor> jobList = new ArrayList<ScheduleJobInfor>();  
        for (JobKey jobKey : jobKeys) {  
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);  
            for (Trigger trigger : triggers) {  
                ScheduleJobInfor job = new ScheduleJobInfor(); 
                job.setJobName(jobKey.getName());  
                job.setJobGroup(jobKey.getGroup());  
                job.setDescription("触发器:" + trigger.getKey());  
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());  
                job.setJobStatus(triggerState.name());  
                if (trigger instanceof CronTrigger) {  
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();  
                    job.setCronExpression(cronExpression);  
                }  
                jobList.add(job);  
            }  
        }  
        return jobList;  
    }

}
