package com.cyb.util.validate.jsr.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cyb.util.string.StringUtil;
import com.cyb.util.validate.jsr.annotation.Email;
import lombok.extern.slf4j.Slf4j;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
@Slf4j
public class EmailValidator implements ConstraintValidator<Email, String> {
	Email constraintAnnotation;
	public void initialize(Email constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(!StringUtil.isEmpty(value)) return true;
		log.error("email:"+value);
		return false;
	}
}
