package com.kiiik.web.example.controller;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月13日
 */
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.pub.bean.res.ResultBean;
import com.kiiik.web.example.task1.SftpTask;
import com.kiiik.web.example.task1.TaskFactory;
import com.kiiik.web.example.task1.TaskInfor;

@RestController
@RequestMapping("cron/task")
public class CronController {
	Log log = LogFactory.getLog(CronController.class);

	@Autowired
	SftpTask job;

	public static String setSystemTime(String date, String time) {
		String osName = System.getProperty("os.name");
		String dateTimeMessage = date + " " + time;
		try {
			if (osName.matches("^(?i)Windows.*$")) { // Window 系统
				String cmd;

				cmd = " cmd /c date " + date; // 格式：yyyy-MM-dd
				Runtime.getRuntime().exec(cmd);

				cmd = " cmd /c time " + time; // 格式 HH:mm:ss
				Runtime.getRuntime().exec(cmd);
			} else if (osName.matches("^(?i)Linux.*$")) {// Linux 系统
				String command = "date -s " + "\"" + date + " " + time + "\"";// 格式：yyyy-MM-dd
																				// HH:mm:ss
				Runtime.getRuntime().exec(command);
			} else {

			}
		} catch (IOException e) {
			return e.getMessage();
		}
		return dateTimeMessage;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("modSystemTime")
	public ResultBean<String> setsystime(String yyyy_mm_dd) {
		setSystemTime(yyyy_mm_dd, "12:00:00");
		return new ResultBean<String>().success("修改操作系统时间成功！");
	}

	@GetMapping("restartAllTask")
	public ResultBean<String> startAllTask() {
		for (TaskInfor t : TaskFactory.tasks) {
			if (job.exist(t)) {
				job.removeJob(t);
			} else {
				job.addJob(t);
			}
		}
		return new ResultBean<String>("重新启动所有任务成功");
	}

	@GetMapping("stopAllTask")
	public ResultBean<String> stopAllTask() {
		for (TaskInfor t : TaskFactory.tasks) {
			if (!job.exist(t)) {
				System.err.println("不存在任务！" + t);
			} else {
				job.removeJob(t);
			}
		}
		return new ResultBean<String>("所有任务终止成功！");
	}

	@GetMapping("list")
	public ResultBean<Map<String, TaskInfor>> listTask() {
		return new ResultBean<Map<String, TaskInfor>>(TaskFactory.map());
	}

	@SuppressWarnings("unchecked")
	@GetMapping("add")
	public ResultBean<String> addTask(TaskInfor task) {
		if (TaskFactory.map().containsKey(task.getId())) {
			return new ResultBean<>().fail("存在相公的任务！");
		} else {
			TaskFactory.add(task);
			job.addJob(task);
		}
		return new ResultBean<>("添加成功！");
	}

	@GetMapping("restart")
	public ResultBean<String> restartTask(String id) {
		TaskInfor cur = TaskFactory.map().get(id);
		job.removeJob(cur);
		job.addJob(cur);
		return new ResultBean<>("更新成功！");
	}

	@SuppressWarnings("unchecked")
	@GetMapping("updCron")
	public ResultBean<String> updTask(TaskInfor task) {
		TaskInfor cur = TaskFactory.map().get(task.getId());
		if (task.getCron().equals(cur.getCron())) {
			return new ResultBean<>().fail("任务定时策略没有发生变化！");
		}
		job.removeJob(task);
		job.addJob(task);
		return new ResultBean<>("更新成功！");
	}

	@GetMapping("stopTask")
	public ResultBean<String> delTask(String id) {
		job.removeJob(TaskFactory.map().get(id));
		return new ResultBean<>("任务移除成功！");
	}

	@SuppressWarnings("unchecked")
	@GetMapping("startTask")
	public ResultBean<String> startTask(String id) {
		if (job.exist(TaskFactory.map().get(id))) {
			return new ResultBean<String>().fail("任务已经存在！");
		}
		job.addJob(TaskFactory.map().get(id));
		return new ResultBean<String>("任务启动成功！");
	}
}
