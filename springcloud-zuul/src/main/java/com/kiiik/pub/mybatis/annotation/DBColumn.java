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
public @interface DBColumn {
 
	String value() default ""; //数据库中的字段名
	String insertIfNull() default "";  // 示例   100,'abc' --字符串注意带引号, defalut ,now() 
	String updateIfNull() default "";
	boolean needTimestamp() default false;//日期格式的数据采用此属性，
}