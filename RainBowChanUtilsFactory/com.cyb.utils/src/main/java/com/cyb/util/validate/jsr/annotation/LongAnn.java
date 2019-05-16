package com.cyb.util.validate.jsr.annotation;

import com.cyb.util.validate.jsr.validator.LongValidator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
@Target({METHOD,FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {LongValidator.class})
@Deprecated
public @interface LongAnn {
	 String message() default "整数格式不正确！";
	 long min() default -999999999;
	 long max() default 999999999;
	 Class<?>[] groups() default { };
	 Class<? extends Payload>[] payload() default { };
}
