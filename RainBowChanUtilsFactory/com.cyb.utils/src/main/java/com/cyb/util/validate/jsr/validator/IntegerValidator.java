package com.cyb.util.validate.jsr.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cyb.util.string.StringUtil;
import com.cyb.util.validate.jsr.annotation.IntegerAnn;
import lombok.extern.slf4j.Slf4j;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
@Slf4j
@Deprecated
public class IntegerValidator implements ConstraintValidator<IntegerAnn, Integer> {
	IntegerAnn constraintAnnotation;
	public void initialize(IntegerAnn constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;
	}

	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if(StringUtil.isEmpty(value)) return false;
		long val = Integer.valueOf(value);
		if(val<constraintAnnotation.min()||val>constraintAnnotation.max())
			return false;
		log.error(constraintAnnotation.max()+"-"+constraintAnnotation.max());
		return true;
	}
}
