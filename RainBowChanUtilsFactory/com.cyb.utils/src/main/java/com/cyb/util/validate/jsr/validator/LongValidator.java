package com.cyb.util.validate.jsr.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cyb.util.string.StringUtil;
import com.cyb.util.validate.jsr.annotation.LongAnn;
import lombok.extern.slf4j.Slf4j;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
@Slf4j
@Deprecated
public class LongValidator implements ConstraintValidator<LongAnn, String> {
	LongAnn constraintAnnotation;
	
	public void initialize(LongAnn constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtil.isEmpty(value)) return false;
		long val = Long.valueOf(value);
		if(val<constraintAnnotation.min()||val>constraintAnnotation.max()) {
			    log.error(value+constraintAnnotation.message());
				return false;
		}
		log.error(constraintAnnotation.max()+"-"+constraintAnnotation.max());
		return true;
	}
}
