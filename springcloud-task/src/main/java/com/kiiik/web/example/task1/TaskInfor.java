package com.kiiik.web.example.task1;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kiiik.web.example.task1.job.SpringBeanJob;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月13日
 */
public class TaskInfor {
	Log log = LogFactory.getLog(TaskInfor.class);
	String id;//记录主键
	String name;//指定job名字
	String cron;
	String job_id;//自定义job唯一主键
	String job_group;//自定义job组名称
	String job_trigger;//自定义job触发器
	String job_trigger_group;//自定义job触发器组
	String job_desc;
	Class<?> target;
	public TaskInfor(){}
	public TaskInfor(String id,String name,String cron,String desc){
		this(id,name,cron);
		this.job_desc = desc;
	}
	public TaskInfor(String id,String name){
		this.id = id;
		this.job_id=SpringBeanTaskUtil.job_prefix+id;
		this.job_group=SpringBeanTaskUtil.job_group_prefix+id;
		this.job_trigger=SpringBeanTaskUtil.trigger_prefix+id;
		this.job_trigger_group=SpringBeanTaskUtil.trigger_group_prefix+id;
		this.name = name;
	}
	public TaskInfor(String id,String name,String cron){
		this(id,name);
		this.cron = cron;
	}
	public TaskInfor(String id, String name, String cron, Class<?> target) {
		this(id,name,cron);
		this.target = target;
	}
	public TaskInfor(String id, String name, String cron,String desc, Class<?> target) {
		this(id,name,cron,desc);
		this.target = target;
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
	
	public static TaskInfor getDefault(){
		return  new TaskInfor("1","testjob","*/10 * * * * ?",SpringBeanJob.class);
	}
	public static TaskInfor getDefault(String id){
		return  new TaskInfor(id,"testjob"+id,"*/10 * * * * ?",SpringBeanJob.class);
	}
	public Class<?> getTarget() {
		return target;
	}
	public void setTarget(Class<?> target) {
		this.target = target;
	}
	public String getJob_id() {
		return job_id;
	}
	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}
	public String getJob_group() {
		return job_group;
	}
	public void setJob_group(String job_group) {
		this.job_group = job_group;
	}
	public String getJob_trigger() {
		return job_trigger;
	}
	public void setJob_trigger(String job_trigger) {
		this.job_trigger = job_trigger;
	}
	public String getJob_trigger_group() {
		return job_trigger_group;
	}
	public void setJob_trigger_group(String job_trigger_group) {
		this.job_trigger_group = job_trigger_group;
	}
	public String getJob_desc() {
		return job_desc;
	}
	public void setJob_desc(String job_desc) {
		this.job_desc = job_desc;
	}
	
}
