package com.kiiik.pub.listener;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.kiiik.utils.SpringContextUtil;
import com.kiiik.web.example.task1.SpringBeanTaskUtil;
import com.kiiik.web.example.task1.TaskInfor;
import com.kiiik.web.example.task1.ann.KiiikTask;
/**
 * @author iechenyb
 * @time 2018年9月05日 12:58:46
 * 启动监听
 */
@Component
public class CenterAuthApplicationStartListener 
implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	Environment env;
	
	@Autowired
	SpringContextUtil scu;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		//beanName-classObject
		initKiiikTask();
		
	}
	
	@Autowired
	SpringBeanTaskUtil stu;
	public void  initKiiikTask(){
		int count = 1;
		Map<String,Object> tasks = scu.getContext().getBeansWithAnnotation(KiiikTask.class);
		for(String beanName:tasks.keySet()){
			KiiikTask taskAnn = tasks.get(beanName).getClass().getAnnotation(KiiikTask.class);
			if(taskAnn.startJobOnStartUp()){//如果定义为项目启动是开启任务
				TaskInfor task = new TaskInfor(""+count++,
						beanName,
						taskAnn.cron(),
						taskAnn.desc(),
						tasks.get(beanName).getClass());
				/*task.setCron(taskAnn.cron());
				task.setJob_desc(taskAnn.desc());
				task.setJob_id(job_id);//数据库表的主键，有则不再更新。
				task.setName(beanName);*/
				stu.addJob(task, null);
			}
		}
	}
}
