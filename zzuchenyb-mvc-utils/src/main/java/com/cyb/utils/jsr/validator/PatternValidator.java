package com.cyb.utils.jsr.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
import com.cyb.utils.jsr.annotation.Pattern;
public class PatternValidator implements ConstraintValidator<Pattern, String> {
	Log log = LogFactory.getLog(PatternValidator.class);
	Pattern constraintAnnotation;
	
	@Override
	public void initialize(Pattern constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isEmpty(value)) return false;
		log.error(constraintAnnotation.regex());
		return false;
	}
}
