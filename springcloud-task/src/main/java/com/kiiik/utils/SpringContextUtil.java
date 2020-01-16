package com.kiiik.utils;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
@Component
public class SpringContextUtil implements ApplicationContextAware {
	final String PROD="prod";
	final String TEST="test";
	final String DEV="dev";
	final String INTEGRA = "integra";
	
	@Autowired
    private ApplicationContext context = null;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
	
	public String getActive(){
		return context.getEnvironment().getActiveProfiles()[0];
	}
	public boolean isPro(){
		return PROD.equals(getActive());
	}
	public boolean isTest(){
		return TEST.equals(getActive());
	}
	public boolean isDev(){
		return DEV.equals(getActive());
	}
	public boolean isINTEGRA(){
		return INTEGRA.equals(getActive());
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

}

