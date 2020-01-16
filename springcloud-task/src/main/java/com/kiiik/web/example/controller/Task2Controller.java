package com.kiiik.web.example.controller;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.kiiik.web.example.task1.ScheduleJobInfor;
import com.kiiik.web.example.task1.SpringBeanTaskUtil;
import com.kiiik.web.example.task1.TaskInfor;

import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月3日上午9:03:14
 */
@RestController
@RequestMapping("task2")
public class Task2Controller {
	
	Log log = LogFactory.getLog(Task2Controller.class);
	
	@Autowired
	SpringBeanTaskUtil taskUtil;
	
	@GetMapping("queryTasks")
	@ResponseBody
	public String queryTasks() {
		System.out.println("查看权限和角色");
		return "信息列表!";
	}

	@ResponseBody
	@GetMapping("addTask1")
	@ApiOperation(notes="新增一个定时任务，默认间隔10s", value = "新增定时任务！")
	public String addTask() throws SchedulerException, ClassNotFoundException {
		Map<String, Object> params = Maps.newHashMap();
		params.put("id", TaskInfor.getDefault().getId());
		params.put("task", TaskInfor.getDefault());
		params.put("name", TaskInfor.getDefault().getName());
		taskUtil.addJob(TaskInfor.getDefault(),params);
		return "添加task成功！（10秒间隔）";
	}
	
	@ResponseBody
	@GetMapping("addTask2")
	@ApiOperation(notes="新增一个定时任务，默认间隔10s", value = "新增定时任务！")
	public String addTask1() throws SchedulerException, ClassNotFoundException {
		Map<String, Object> params = Maps.newHashMap();
		params.put("id", TaskInfor.getDefault("2").getId());
		params.put("task", TaskInfor.getDefault("2"));
		params.put("name", TaskInfor.getDefault("2").getName());
		taskUtil.addJob(TaskInfor.getDefault("2"),params);
		return "添加task成功！（10秒间隔）";
	}

	@ResponseBody
	@GetMapping("removeTask")
	@ApiOperation(notes="移除定时任务", value = "移除定时任务！")
	public String delete() throws SchedulerException {
		taskUtil.removeJob(TaskInfor.getDefault());
		return "删除task成功！";
	}

	@ResponseBody
	@GetMapping("update") 
	@ApiOperation(notes="更新一个定时任务，修改默认间隔5s", value = "更新定时任务！")
	public String update() throws SchedulerException {
		TaskInfor task = TaskInfor.getDefault("2");//id=1
		if(taskUtil.exist(task)){
			task.setCron("*/5 * * * * ?");
			Map<String, Object> params = Maps.newHashMap();
			params.put("id", task.getId());
			params.put("task", task);
			params.put("name", task.getName());
			taskUtil.modifyJob(task,params);
		}else{
			return "任务不存在！";
		}
		return "更新任务执行时间成功！（5s间隔）";
	}
	
	
	@ResponseBody
	@GetMapping("startAllTask")
	@ApiOperation(notes="启动所有定时任务", value = "启动所有定时任务！")
	public String startAllTask() throws SchedulerException {
		taskUtil.startAll();
		return "启动所有任务成功！";
	}
	
	@ResponseBody
	@GetMapping("shutdownAllTask")
	@ApiOperation(notes="关闭所有定时任务,需要重新添加", value = "关闭所有定时任务！")
	public String shutdownAllTask() throws SchedulerException {
		taskUtil.shutdown();
		return "关闭定时器成功";
	}
	@ResponseBody
	@GetMapping("state")
	@ApiOperation(notes="获取任务状态", value = "获取任务状态")
	public String state() throws SchedulerException {
		return taskUtil.getState(TaskInfor.getDefault());
	}
	
	@ResponseBody
	@GetMapping("allJobs")
	@ApiOperation(notes="获取所有开启的task", value = "获取所有开启的task")
	public List<ScheduleJobInfor> allJobs() throws SchedulerException {
		return taskUtil.getAllJob();
	}
}
