package com.kiiik.web.example.task1.ann;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KiiikTask {

	/**
	 *是否可以并发
	 */
	boolean concurrent() default false;
	/**
	 * 并发数
	 * @return
	 */
	int concurrentThreadsNumber() default 10;
	
	/**
	 * 工程启动的时候是否启动任务。
	 * @return
	 */
	
	boolean startJobOnStartUp() default false;
	
	String cron() default "";
	//job的描述
	String desc() default "";

}