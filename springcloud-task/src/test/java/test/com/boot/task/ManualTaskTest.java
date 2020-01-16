package test.com.boot.task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kiiik.web.example.task.HelloWorldBean;
import com.kiiik.web.example.task.QuartzManager;
import com.kiiik.web.example.task.QuartzManagerBean;

import test.com.boot.pub.BaseUnit;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月10日
 */
public class ManualTaskTest extends BaseUnit{
	Log log = LogFactory.getLog(ManualTaskTest.class);
	@Autowired
	QuartzManagerBean mananger;
	
	@Autowired
	HelloWorldBean helloJob;
	
	@Autowired
	SchedulerFactory gSchedulerFactory ;
	
	private final String JOB_GROUP_NAME = "JOB_GROUP_SYSTEM";  
	private final String TRIGGER_GROUP_NAME = "TRIGGER_GROUP_SYSTEM";  
	@Test
	public void testHelloJob(){
		try {  
            
            Scheduler sche = gSchedulerFactory.getScheduler();  
            for(int i=0;i<20;i++){
            	//System.err.println(sche);//检查是否单例，结果是单例
            }
            String job_name = "动态任务调度";  
            System.out.println("【系统启动】开始(每1秒输出一次)...");  
            String clazz = HelloWorldBean.class.getName();  
            String cron = "*/10 * * * * ?";  //使用Class.forName动态的创建  
            mananger.addJob(sche, job_name, Class.forName(clazz), cron,JOB_GROUP_NAME,TRIGGER_GROUP_NAME);  
  
            Thread.sleep(3000);  
            System.out.println("【修改时间】开始(每2秒输出一次)...");  
            QuartzManager.modifyJobTime(sche, job_name, "10/2 * * * * ?");  
            Thread.sleep(4000);  
            System.out.println("【移除定时】开始...");  
            mananger.removeJob(sche, job_name,JOB_GROUP_NAME,TRIGGER_GROUP_NAME);  
            System.out.println("【移除定时】成功");  
  
            System.out.println("【再次添加定时任务】开始(每10秒输出一次)...");  
            mananger.addJob(sche, job_name, Class.forName(clazz), "*/10 * * * * ?",JOB_GROUP_NAME,TRIGGER_GROUP_NAME);  
            Thread.sleep(30000);  
            System.out.println("【移除定时】开始...");  
            mananger.removeJob(sche, job_name,JOB_GROUP_NAME,TRIGGER_GROUP_NAME);  
            System.out.println("【移除定时】成功");  
            
            mananger.addJob(sche, job_name, Class.forName(clazz), cron,JOB_GROUP_NAME,TRIGGER_GROUP_NAME); 
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
	
}
