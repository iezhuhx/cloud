package com.cyb.unit;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import com.cyb.web.test.service.TestService;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年12月21日
 */
public class RflectionsTest {
	Log log = LogFactory.getLog(RflectionsTest.class);

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 接口实现类扫描<br>
	 * 创建时间: 2017年7月15日
	 */
	@Test
	public void testReflectionsType() {
		Reflections reflections = new Reflections("com.cyb");
		Set<Class<? extends TestService>> classes = reflections.getSubTypesOf(TestService.class);
		for (Class<?> clazz : classes) {
			System.out.println("Found: " + clazz.getName());
		}
	}

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 接口实现类扫描<br>
	 * 创建时间: 2017年7月15日
	 */
	@Test
	public void testReflections() {
		Reflections reflections = new Reflections();
		Set<Class<? extends TestService>> classes = reflections.getSubTypesOf(TestService.class);
		for (Class<?> clazz : classes) {
			System.out.println("Found: " + clazz.getName());
		}
	}

	@Test
	public void testReflectionsWithAnn() {
		Reflections reflections = new Reflections();
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Service.class);
		for (Class<?> clazz : classes) {
			System.out.println("Found: " + clazz.getName());
		}
	}
}
