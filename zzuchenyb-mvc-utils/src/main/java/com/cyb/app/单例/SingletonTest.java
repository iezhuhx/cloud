package com.cyb.app.单例;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月31日
 */
public class SingletonTest {
	Log log = LogFactory.getLog(SingletonTest.class);
	public static void main(String[] args) throws ClassNotFoundException {
		//装载类时不初始化类
		Class.forName("com.cyb.app.单例.SingletonPattern");
		//装载类时初始化
		Class.forName("com.cyb.app.单例.SingletonPattern2");
		//显示构造器执行方法
		StaticInnerClassSingleton.getInstance();
		//不显示构造器执行方法
		StaticFieldSingleton.getInstance();
	}
}
