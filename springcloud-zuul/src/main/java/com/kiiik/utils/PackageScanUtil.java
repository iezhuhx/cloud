package com.kiiik.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reflections.Reflections;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kiiik.pub.ann.IgnoreApi;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年12月20日
 */
public class PackageScanUtil {
	public static void main(String[] args) {
		System.out.println(getAllIgnoreController("com.kiiik"));
	}

	public static List<String> getAllControllerUri(String packageName) {
		Reflections reflections = new Reflections(packageName);
		Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(RequestMapping.class);
		List<String> uris = new ArrayList<>(classesList.size());
		for (Class<?> c : classesList) {
			// 获取所有的类的方法
			uris.add(c.getAnnotation(RequestMapping.class).value()[0]);
		}
		return uris;
	}
	
	public static List<String> getAllIgnoreController(String packageName) {
		Reflections reflections = new Reflections(packageName);
		Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(IgnoreApi.class);
		List<String> uris = new ArrayList<>(classesList.size());
		for (Class<?> c : classesList) {
			// 获取所有的类的方法
			uris.add(c.getSimpleName());
		}
		return uris;
	}

	Log log = LogFactory.getLog(PackageScanUtil.class);

	/**
	 * 获取指定文件下面的RequestMapping方法保存在mapp中
	 *
	 * @param packageName
	 * @return
	 */
	public static Map<String, ExecutorBean> getRequestMappingMethod(String packageName) {
		Reflections reflections = new Reflections(packageName);
		Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(Controller.class);
		// 存放url和ExecutorBean的对应关系
		Map<String, ExecutorBean> mapp = new HashMap<String, ExecutorBean>();
		for (Class<?> classes : classesList) {
			// 得到该类下面的所有方法
			Method[] methods = classes.getDeclaredMethods();

			for (Method method : methods) {
				// 得到该类下面的RequestMapping注解
				RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
				if (null != requestMapping) {
					ExecutorBean executorBean = new ExecutorBean();
					try {
						executorBean.setObject(classes.newInstance());
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					executorBean.setMethod(method);
					mapp.put(requestMapping.value()[0], executorBean);

				}
			}
		}
		return mapp;
	}
}

class ExecutorBean {
	private Object object;

	private Method method;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
}
