package com.kiiik.web.example.task1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年12月13日
 */
public class TaskFactory {
	Log log = LogFactory.getLog(TaskFactory.class);
	public static List<TaskInfor> tasks = new ArrayList<>();
	static {
		/**
		 * 测试时，每修改一次时间，重启一下任务！
		 */
		//tasks.add(new TaskInfor("1", "5秒一次", "*/5 * * * * ?"));//ok
		//tasks.add(new TaskInfor("2", "每分钟一次", "0 * * * * ?"));//ok
		//tasks.add(new TaskInfor("3", "每小时一次", "0 0 * * * ?"));//ok
		tasks.add(new TaskInfor("4", "每天执行一次", "0 0 23 * * ?"));
		tasks.add(new TaskInfor("5", "每周执行一次", "*/5 * * ? * MON"));
		tasks.add(new TaskInfor("6", "每月执行一次", "*/5 * * L * ?"));
		tasks.add(new TaskInfor("7", "每季度执行一次", "*/5 * * L 3,6,9,12 ?"));//error
		tasks.add(new TaskInfor("8", "每年度执行一次", "*/5 * * L 12 ?"));//error
		tasks.add(new TaskInfor("9", "每月第三个星期五的10:15分运行", "*/5 * * ? * 6#3"));
		tasks.add(new TaskInfor("10", "每月执行一次", "*/5 * * 1 * ?"));
		tasks.add(new TaskInfor("11", "每季度执行一次", "*/5 * * 1 4,7,10,1 ?"));
		tasks.add(new TaskInfor("12", "每年度执行一次", "*/5 * * 1 1 ?"));//下个周期第一天计算 计算上周期
		tasks.add(new TaskInfor("13", "每半年一次", "*/5 * * L 6,12 ?"));
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2018年1月1日
	 *@param task
	 */

	public static void add(TaskInfor task) {
		tasks.add(task);
	}

	public static Map<String, TaskInfor> map() {
		Map<String, TaskInfor> map = new HashMap<>();
		for (TaskInfor t : tasks) {
			map.put(t.getId(), t);
		}
		return map;
	}
}
