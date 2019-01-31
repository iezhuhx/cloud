package com.cyb.utils.reflection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.utils.reflection.cglib.CglibProxyInterface;
import com.cyb.utils.reflection.cglib.CglibProxyCls;
import com.cyb.utils.reflection.service.AnimalSayImpl;
import com.cyb.utils.reflection.service.PersonSayImpl;
import com.cyb.utils.reflection.service.SayService;
/**
 *作者 : iechenyb<br>
 *类描述: 优化 如果是接口 从容器找bean实现类，否则直接进行代理！<br>
 *创建时间: 2017年12月6日
 */
public class CglibTest {
	public static Map<String,Class<?>> register = new HashMap<String,Class<?>>();
	static{
		register.put("com.cyb.utils.reflection.service.SayService", PersonSayImpl.class);
		register.put("com.cyb.utils.reflection.service.SayService", AnimalSayImpl.class);
	}
	Log log = LogFactory.getLog(CglibTest.class);
	public static void main(String[] args)  {
		try{
			 //通过生成子类的方式创建代理类   接口代理
			 SayService proxyImp = (SayService)new CglibProxyInterface().getProxy(SayService.class);  
			 proxyImp.say("aaaa"); 
			 System.out.println("*************************");
			 //通过生成子类的方式创建代理类   接口实现类代理
			 SayService personSayImpl = (SayService)new CglibProxyCls().getProxy(PersonSayImpl.class);  
			 personSayImpl.say("bbbb"); 
			 System.out.println("*************************");
			 //通过生成子类的方式创建代理类   接口实现类代理
			 SayService animalSayImpl = (SayService)new CglibProxyCls().getProxy(AnimalSayImpl.class);  
			 animalSayImpl.say("cccc"); 
			 System.out.println("*************************");
			 //类代理对象  代理接口不行！！！！！
			 try{
				 SayService animalSayImpl_ = (SayService)new CglibProxyCls().getProxy(SayService.class);  
				 animalSayImpl_.say("dddd");
			 }catch(Exception e){}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
