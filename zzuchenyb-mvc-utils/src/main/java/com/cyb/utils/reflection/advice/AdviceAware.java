package com.cyb.utils.reflection.advice;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.MethodProxy;

public interface AdviceAware {
	public void before();
	public void after();
	public void afterThrow();
	public void afterReturn();
	public void around(Object obj, Method method, Object[] args, MethodProxy proxy);
}
