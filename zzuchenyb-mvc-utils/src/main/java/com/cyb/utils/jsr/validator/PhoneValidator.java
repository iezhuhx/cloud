package com.cyb.utils.jsr.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.cyb.utils.jsr.annotation.Phone;
import com.cyb.utils.validator.ValidatorUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {
	Log log = LogFactory.getLog(PhoneValidator.class);
	Phone constraintAnnotation;
	@Override
	public void initialize(Phone constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		log.error(value);
		if(StringUtils.isEmpty(value)) return false;
		if(ValidatorUtils.isMobile(value)){
			return true;
		}
		return false;
	}
}
