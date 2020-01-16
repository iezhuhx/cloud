package com.kiiik.web.example.controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.web.example.task.HelloWorldBean;
import com.kiiik.web.example.task.QuartzManager;
import com.kiiik.web.example.task.QuartzManagerBean;

import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月3日上午9:03:14
 */
@RestController
@RequestMapping("task")
public class TaskController {
	
	Log log = LogFactory.getLog(TaskController.class);
	
	@Autowired
	QuartzManagerBean mananger;
	
	@Autowired
	SchedulerFactory gSchedulerFactory ;
	
	String job_name = "task_name_cyb"; 
	
	private final String JOB_GROUP_NAME = QuartzManager.JOB_GROUP_NAME;  
	private final String TRIGGER_GROUP_NAME = QuartzManager.TRIGGER_GROUP_NAME;  
	
	
	
	@GetMapping("queryTasks")
	@ResponseBody
	public String queryTasks() {
		System.out.println("查看权限和角色");
		return "信息列表!";
	}

	@ResponseBody
	@GetMapping("addTask")
	@ApiOperation(notes="新增一个定时任务，默认间隔10s", value = "新增定时任务！")
	public String addTask() throws SchedulerException, ClassNotFoundException {
		Scheduler sche = gSchedulerFactory.getScheduler();  
		JobKey jobKey = new JobKey(job_name, JOB_GROUP_NAME);// 任务名，任务组，任务执行类  
		if(sche.checkExists(jobKey)){
			return "任务已经存在，请勿重复添加！";
		}
		String clazz = HelloWorldBean.class.getName();//方法内部无法获取注入的service  
        String cron = "*/10 * * * * ?";  //使用Class.forName动态的创建  
        mananger.addJob(sche, job_name, Class.forName(clazz), cron,JOB_GROUP_NAME,TRIGGER_GROUP_NAME);  
		return "添加task成功！（10秒间隔）"+clazz;
	}

	@ResponseBody
	@GetMapping("removeTask")
	@ApiOperation(notes="移除定时任务", value = "移除定时任务！")
	public String delete() throws SchedulerException {
		JobKey jobKey = new JobKey(job_name, JOB_GROUP_NAME);// 任务名，任务组，任务执行类  
		if(!gSchedulerFactory.getScheduler().checkExists(jobKey)){
			return "任务不存在！";
		}
		mananger.removeJob(gSchedulerFactory.getScheduler(), job_name, JOB_GROUP_NAME, TRIGGER_GROUP_NAME);
		return "删除task成功！"+job_name;
	}

	@ResponseBody
	@GetMapping("update") 
	@ApiOperation(notes="更新一个定时任务，修改默认间隔5s", value = "更新定时任务！")
	public String update() throws SchedulerException {
		Scheduler sche = gSchedulerFactory.getScheduler();  
		
		JobKey jobKey = new JobKey(job_name, JOB_GROUP_NAME);// 任务名，任务组，任务执行类  
		if(!gSchedulerFactory.getScheduler().checkExists(jobKey)){
			return "任务不存在！";
		}
		QuartzManager.modifyJobTime(sche, job_name, "*/5 * * * * ?");  
		return "更新任务执行时间成功！（5s间隔）"+job_name;
	}
	
	
	@ResponseBody
	@GetMapping("startAllTask")
	@ApiOperation(notes="启动所有定时任务", value = "启动所有定时任务！")
	public String startAllTask() throws SchedulerException {
		mananger.startJobs(gSchedulerFactory.getScheduler());
		return "启动所有任务成功！";
	}
	
	@ResponseBody
	@GetMapping("shutdownAllTask")
	@ApiOperation(notes="关闭所有定时任务,需要重新添加", value = "关闭所有定时任务！")
	public String shutdownAllTask() throws SchedulerException {
		mananger.shutdownJobs(gSchedulerFactory.getScheduler());
		return "关闭定时器成功";
	}
}
