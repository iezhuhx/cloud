package com.cyb.util.validate.jsr.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
import com.cyb.util.string.StringUtil;
import com.cyb.util.validate.jsr.annotation.PatternAnn;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PatternValidator implements ConstraintValidator<PatternAnn, String> {
	PatternAnn constraintAnnotation;
	
	public void initialize(PatternAnn constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(!StringUtil.isEmpty(value)) return true;
		log.info("--->"+constraintAnnotation.regex());
		return false;
	}
}
