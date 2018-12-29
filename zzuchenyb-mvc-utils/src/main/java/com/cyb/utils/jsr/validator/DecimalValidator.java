package com.cyb.utils.jsr.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.cyb.utils.jsr.annotation.Decimal;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
public class DecimalValidator implements ConstraintValidator<Decimal, Double> {
	Log log = LogFactory.getLog(DecimalValidator.class);
	Decimal constraintAnnotation;
	@Override
	public void initialize(Decimal constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(Double value, ConstraintValidatorContext context) {
		if(StringUtils.isEmpty(value)) return false;
		log.error(constraintAnnotation.max()+"-"+constraintAnnotation.max()+":"+constraintAnnotation.precision());
		return false;
	}
}
