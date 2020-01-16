package com.kiiik.web.example.task1;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger.TriggerState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * https://blog.csdn.net/xiaobuding007/article/details/80455187 创建时间:
 * 2018年12月12日
 */
@Service
public class SpringBeanTaskUtil {
	// 任务名前缀
	public static final String job_prefix = "job_";
	// 任务组前缀
	public static final String job_group_prefix = "job_group_";
	// 触发器前缀
	public static final String trigger_prefix = "trigger_";
	// 触发组前缀
	public static final String trigger_group_prefix = "trigger_group_";

	/*
	 * 不可以注入service
	 * @Autowired
	private QuartzManager quartzManager;*/
    //可以注入service
	@Autowired
	private QuartzManager2 quartzManager;

	/**
	 * 添加定时任务
	 */
	public void addJob(TaskInfor task,Map<String, Object> params) {
		if(exist(task)) return;
		quartzManager.addJob(task.getName(),task.getJob_group(), 
				task.getJob_trigger(), task.getJob_trigger_group(),
				task.getTarget(), task.getCron(), params);
	}

	public boolean exist(TaskInfor task) {
		try {
			return quartzManager.exists(task.getName()
					,task.getJob_group());
		} catch (SchedulerException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 修改定时任务
	 */
	public void modifyJob(TaskInfor task,Map<String,Object> params) {
		removeJob(task);
		addJob(task, params);
	}

	/**
	 * 移除定时任务
	 */
	public void removeJob(TaskInfor task) {
		String id = task.getId();
		quartzManager.removeJob(job_prefix + id, job_group_prefix + id, trigger_prefix + id, trigger_group_prefix + id);
	}
	
	public void shutdown(){
		quartzManager.shutdownJobs();
	}
	
	public void startAll(){
		quartzManager.startJobs();
	}
	
	
	public String getState(TaskInfor task){
		JobKey jobKey = new JobKey(task.getName(), 
				task.getJob_group());// 任务名，任务组，任务执行类  
		TriggerState a =  quartzManager.getState(jobKey);
		return a.name();
	}
	public  static String getTriggerStatesCN(String key) {
	    Map<String, String> map = new LinkedHashMap<String, String>();
	    map.put("BLOCKED", "阻塞");
	    map.put("COMPLETE", "完成");
	    map.put("ERROR", "出错");
	    map.put("NONE", "不存在");
	    map.put("NORMAL", "正常");
	    map.put("PAUSED", "暂停");

	    map.put("4", "阻塞");
	    map.put("2", "完成");
	    map.put("3", "出错");
	    map.put("-1", "不存在");
	    map.put("0", "正常");
	    map.put("1", "暂停");
	    /*  **STATE_BLOCKED 4 阻塞
			STATE_COMPLETE 2 完成
			STATE_ERROR 3 错误
			STATE_NONE -1 不存在
			STATE_NORMAL 0 正常
			STATE_PAUSED 1 暂停***/
	    return map.get(key);
	}
	public List<ScheduleJobInfor> getAllJob() throws SchedulerException {
		return quartzManager.getAllJob();
	}
}