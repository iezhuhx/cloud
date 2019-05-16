package com.cyb.util.validate.jsr.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cyb.util.string.StringUtil;
import com.cyb.util.validate.jsr.annotation.DecimalAnn;
import lombok.extern.slf4j.Slf4j;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
@Slf4j
public class DecimalValidator implements ConstraintValidator<DecimalAnn, Double> {
	DecimalAnn constraintAnnotation;
	public void initialize(DecimalAnn constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;
	}

	public boolean isValid(Double value, ConstraintValidatorContext context) {
		if(!StringUtil.isEmpty(value)&&
				(value>=constraintAnnotation.min()&&value<=constraintAnnotation.max())
				) return true;
		log.error(constraintAnnotation.message()+constraintAnnotation.min()+"-"+constraintAnnotation.max()+":"+constraintAnnotation.precision());
		return false;
	}
}
