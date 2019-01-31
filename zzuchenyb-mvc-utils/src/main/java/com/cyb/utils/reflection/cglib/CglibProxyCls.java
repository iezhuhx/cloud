package com.cyb.utils.reflection.cglib;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.cyb.utils.reflection.advice.AdviceAware;
import com.cyb.utils.reflection.advice.NoticeAdvice;
import com.cyb.utils.reflection.advice.TimeAdvice;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * https://yq.aliyun.com/articles/6668 创建时间: 2017年12月6日
 */
public class CglibProxyCls implements MethodInterceptor {
	private Enhancer enhancer = new Enhancer();
	NoticeAdvice advice = new NoticeAdvice();
	List<AdviceAware> advices=new ArrayList<>();

	public Object getProxy(Class<?> clazz) {
		// 设置需要创建子类的类
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		//默认添加两个增强
		advices.add(new NoticeAdvice());
		advices.add(new TimeAdvice());
		// 通过字节码技术动态创建子类实例
		return enhancer.create();
	}

	/**实现MethodInterceptor接口方法
	 * 直接进行代理
	 * 多个切面增强方执行先后顺序  类似同心圆，一端进入，另一端出！！！
	 */
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		Object result = null;
		
		for(AdviceAware aware:advices){
			aware.before();
		}
		try{
			result = proxy.invokeSuper(obj, args);
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
