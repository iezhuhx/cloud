package com.ww.activiti.controller;

import java.util.Map;

/**
 * @Author iechenyb<br>
 * @Desc 类描述<br>
 * @CreateTime 2020年5月25日 下午1:02:34
 */
public class CommandInfo {
	String procId;
	String userName;
	String message ;
	Map<String, Object> taskPamater;

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	public Map<String, Object> getTaskPamater() {
		return taskPamater;
	}

	public void setTaskPamater(Map<String, Object> taskPamater) {
		this.taskPamater = taskPamater;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
