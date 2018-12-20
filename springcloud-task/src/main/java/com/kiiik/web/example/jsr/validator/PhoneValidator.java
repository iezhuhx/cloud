package com.kiiik.web.example.jsr.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kiiik.utils.ValidatorUtils;
import com.kiiik.web.example.jsr.anno.IsPhone;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
public class PhoneValidator implements ConstraintValidator<IsPhone, String> {
	Log log = LogFactory.getLog(PhoneValidator.class);

	@Override
	public void initialize(IsPhone constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(ValidatorUtils.isMobile(value)){
			return true;
		}
		return false;
	}
}
