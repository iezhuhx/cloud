package com.cyb.utils.jsr.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.cyb.utils.jsr.annotation.Long;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
public class LongValidator implements ConstraintValidator<Long, Long> {
	Log log = LogFactory.getLog(LongValidator.class);
	Long constraintAnnotation;
	@Override
	public void initialize(Long constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		if(StringUtils.isEmpty(value)) return false;
		log.error(constraintAnnotation.max()+"-"+constraintAnnotation.max());
		return false;
	}
}
