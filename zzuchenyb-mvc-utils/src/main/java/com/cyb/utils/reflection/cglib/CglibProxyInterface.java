package com.cyb.utils.reflection.cglib;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.cyb.utils.reflection.CglibTest;
import com.cyb.utils.reflection.advice.AdviceAware;
import com.cyb.utils.reflection.advice.NoticeAdvice;
import com.cyb.utils.reflection.advice.TimeAdvice;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * https://yq.aliyun.com/articles/6668 创建时间: 2017年12月6日
 */
public class CglibProxyInterface implements MethodInterceptor {
	private Enhancer enhancer = new Enhancer();
	private Class<?> clss;
	NoticeAdvice advice = new NoticeAdvice();
	List<AdviceAware> advices=new ArrayList<>();
	public Object getProxy(Class<?> clazz) {
		// 设置需要创建子类的类
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		this.clss = clazz;
		//默认添加两个增强,根据类获取增强对象
		advices.add(new NoticeAdvice());
		advices.add(new TimeAdvice());
		// 通过字节码技术动态创建子类实例
		return enhancer.create();
	}

	/**实现MethodInterceptor接口方法
	 * 根据接口找到类，然后进行代理！从容器中获取，通过实现aware方法
	 */
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		for(AdviceAware aware:advices){
			aware.before();//可以添加参数
		}
		Object object = CglibTest.register.get(clss.getName()).newInstance();
		// 通过代理类调用父类中的方法
		Object result = null;
		try{
			result = proxy.invokeSuper(object, args);
		}catch(Exception e){
			for(int i=advices.size()-1;i>=0;i--){
				advices.get(i).afterThrow();
			}
		}
		for(int i=advices.size()-1;i>=0;i--){
			advices.get(i).after();
		}
		return result;
	}

	
}
