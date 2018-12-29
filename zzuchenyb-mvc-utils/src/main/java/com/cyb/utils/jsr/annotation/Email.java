package com.cyb.utils.jsr.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cyb.utils.jsr.validator.EmailValidator;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
@Target({METHOD,FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {EmailValidator.class})
public @interface Email {
 String message() default "邮箱格式不正确！";
 Class<?>[] groups() default { };
 Class<? extends Payload>[] payload() default { };
}
