package com.cyb.unit.base;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年7月19日
 */
public class JunitBase {
	Log log = LogFactory.getLog(JunitBase.class);
	public ApplicationContext ac = null;
	public JunitBase(){
		ac = new ClassPathXmlApplicationContext(
						/*"classpath:applicationContext.xml",
						"classpath:applicationContext-job.xml"
						,
						"classpath:applicationContext-redis.xml"*/
			 );// 从classpath中加载
	}
}
