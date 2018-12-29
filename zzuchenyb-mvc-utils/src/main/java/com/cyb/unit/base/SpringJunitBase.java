package com.cyb.unit.base;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年7月19日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = { /*"classpath*:applicationContext.xml",  
					  "classpath*:applicationContext-job.xml" ,
					  "classpath*:applicationContext-mail.xml" ,
					  "classpath*:applicationContext-redis.xml" */})
public class SpringJunitBase {
	
}
