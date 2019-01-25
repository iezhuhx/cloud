package com.cyb.app.task;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月25日
 */
public class Task {
	Class<?> target;
	boolean enable=false;
	String cron ;
	Task(Class<?> target,boolean enable,String cron){
		this.target = target;
		this.enable = enable;
		this.cron = cron;
	}
	public Class<?> getTarget() {
		return target;
	}
	public void setTarget(Class<?> target) {
		this.target = target;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
}
