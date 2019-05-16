package com.cyb.util.validate.jsr.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cyb.util.string.StringUtil;
import com.cyb.util.validate.jsr.annotation.Phone;
import com.cyb.util.validate.pub.ValidatorUtils;
import lombok.extern.slf4j.Slf4j;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
@Slf4j
public class PhoneValidator implements ConstraintValidator<Phone, String> {
	Phone constraintAnnotation;
	public void initialize(Phone constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtil.isEmpty(value)) return false;
		if(ValidatorUtils.isMobile(value)){
			return true;
		}
		log.error(value+constraintAnnotation.message());
		return false;
	}
}
