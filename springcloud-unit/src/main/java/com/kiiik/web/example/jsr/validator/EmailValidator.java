package com.kiiik.web.example.jsr.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kiiik.web.example.jsr.anno.IsEmail;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
public class EmailValidator implements ConstraintValidator<IsEmail, String> {
	Log log = LogFactory.getLog(EmailValidator.class);

	@Override
	public void initialize(IsEmail constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		return false;
	}
}
