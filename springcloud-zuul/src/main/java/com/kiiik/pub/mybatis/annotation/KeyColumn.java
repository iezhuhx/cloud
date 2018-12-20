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
@Target(ElementType.FIELD)
public @interface KeyColumn {
 
	/**
	 * 是否使用生成的主键,默认false
	 * 
	 */
	boolean useGeneratedKeys() default false;
}
