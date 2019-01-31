package com.cyb.utils.reflection.advice;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.MethodProxy;

public class TimeAdvice implements AdviceAware{
	public void before(){
		System.out.println("时间:before()!");
	}

	public void after(){
		System.out.println("时间:after()!");
	}

	@Override
	public void around(Object obj, Method method, Object[] args, MethodProxy proxy) {
		try {
			//"执行前逻辑"
			method.invoke(obj, args);
			//执行后逻辑
		} catch (Exception e) {
			e.printStackTrace();
			
		} 
	}

	@Override
	public void afterThrow() {
		
	}

	@Override
	public void afterReturn() {
		
	}
}
