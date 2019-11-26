package com.cyb.utils.enviroment;

import java.io.File;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年12月20日
 */
@Component
public class EnvUtils implements ApplicationContextAware {
	Log log = LogFactory.getLog(EnvUtils.class);

	private  ApplicationContext context = null;

	/*
	 * (non Javadoc)
	 * 
	 * @Title: setApplicationContext
	 * 
	 * @Description: spring获取bean工具类
	 * 
	 * @param applicationContext
	 * 
	 * @throws BeansException
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	// 传入线程中
	@SuppressWarnings("unchecked")
	public  <T>  T getBean(String beanName) {
		return (T) context.getBean(beanName);
	}

	// 国际化使用
	public String getMessage(String key) {
		return context.getMessage(key, null, Locale.getDefault());
	}

	/// 获取当前环境
	public  String getActiveProfile() {
		return context.getEnvironment().getActiveProfiles()[0];
	}

	public File getRunDir() {
		String basePath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
		return new File(basePath);
	}
}
