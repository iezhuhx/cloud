package com.cyb.web.test.service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月21日
 */
public class TestServiceImpl implements TestService {
	Log log = LogFactory.getLog(TestServiceImpl.class);

	public void sayHello(String msg) {
			System.out.println("hello "+msg);
	}
}
