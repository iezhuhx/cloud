package com.cyb.web.config;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年12月21日
 */

public class MySimpleMappingExceptionResolver 
extends SimpleMappingExceptionResolver {
	Log log = LogFactory.getLog(MySimpleMappingExceptionResolver.class);

	public MySimpleMappingExceptionResolver() {

		System.out.println("MySimpleMappingExceptionResolver constructor called!");

	}

	@Override

	public String buildLogMessage(Exception ex, HttpServletRequest request) {

		System.out.println("Exception caught by Jerry");

		ex.printStackTrace();

		return "Spring MVC exception: " + ex.getLocalizedMessage();
	}
}
