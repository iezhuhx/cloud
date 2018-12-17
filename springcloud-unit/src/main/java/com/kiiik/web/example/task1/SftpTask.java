package com.kiiik.web.example.task1;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * https://blog.csdn.net/xiaobuding007/article/details/80455187 创建时间:
 * 2018年12月12日
 */
@Service
public class SftpTask {
	// 任务名前缀
	private final String job_prefix = "job_";
	// 任务组前缀
	private final String job_group_prefix = "job_group_";
	// 触发器前缀
	private final String trigger_prefix = "trigger_";
	// 触发组前缀
	private final String trigger_group_prefix = "trigger_group_";

	private QuartzManager quartzManager;

	public SftpTask(QuartzManager quartzManager) {
		this.quartzManager = quartzManager;
	}

	/**
	 * 根据配置生成cron表达式
	 */
	private String getCron() {
		String cron = "*/2 * * * * ?";
		return cron;
	}

	/**
	 * 添加定时任务
	 */
	public void addJob(TaskInfor task) {
		if(exist(task)) return;
		String id = task.getId();
		Map<String, Object> params = Maps.newHashMap();
		params.put("id", id);
		params.put("task", task);
		params.put("name", task.getName());
		quartzManager.addJob(job_prefix + id, job_group_prefix + id, trigger_prefix + id, trigger_group_prefix + id,
				SftpJob.class, task.getCron(), params);
	}

	public boolean exist(TaskInfor task) {
		String id = task.getId();
		if (quartzManager.notExists(trigger_prefix + id, trigger_group_prefix + id)) {
			return false;
		}
		return true;
	}

	/**
	 * 修改定时任务
	 */
	public void modifyJob(TaskInfor task) {
		String id = task.getId();
		if (exist(task)) {
			System.err.println("任务不存在！");
			// 任务不存在
			// addJob(dto);
		} else {
			// 任务存在
			quartzManager.modifyJobTime(trigger_prefix + id, trigger_group_prefix + id, getCron());
		}
	}

	/**
	 * 移除定时任务
	 */
	public void removeJob(TaskInfor task) {
		String id = task.getId();
		quartzManager.removeJob(job_prefix + id, job_group_prefix + id, trigger_prefix + id, trigger_group_prefix + id);
	}

}