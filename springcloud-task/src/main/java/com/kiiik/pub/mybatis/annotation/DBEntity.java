package com.kiiik.pub.mybatis.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月11日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DBEntity {
 
	/**
	 * 数据库库名
	 * @return
	 */
	String database() default "";
	/**
	 * 数据库表名
	 * @return
	 */
	String value();
		
}
