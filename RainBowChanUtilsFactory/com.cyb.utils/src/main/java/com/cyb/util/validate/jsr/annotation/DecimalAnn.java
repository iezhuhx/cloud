package com.cyb.util.validate.jsr.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cyb.util.validate.jsr.validator.DecimalValidator;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年10月19日
 */
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { DecimalValidator.class })
public @interface DecimalAnn {
	String message() default "小数格式不正确！";

	double min() default Double.MIN_VALUE;

	double max() default Double.MAX_VALUE;

	int precision() default 0;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
