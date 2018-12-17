package com.kiiik.web.example.task1;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月13日
 */
public class TaskInfor {
	Log log = LogFactory.getLog(TaskInfor.class);
	String id;
	String name;
	String cron;
	public TaskInfor(){}
	public TaskInfor(String id,String name,String cron){
		this.id = id;
		this.name = name;
		this.cron = cron;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	
	public String toString(){
		return "["+id+"]["+name+"]["+cron+"]";
	}
	
}
