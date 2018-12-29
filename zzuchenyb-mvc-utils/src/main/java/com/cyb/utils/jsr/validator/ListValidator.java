package com.cyb.utils.jsr.validator;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.cyb.utils.jsr.annotation.ListEmpty;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
public class ListValidator implements ConstraintValidator<ListEmpty, List<?>> {
	Log log = LogFactory.getLog(ListValidator.class);
	ListEmpty constraintAnnotation;
	@Override
	public void initialize(ListEmpty constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(List<?> value, ConstraintValidatorContext context) {
		if(CollectionUtils.isEmpty(value)) return false;
		System.out.println("list校验");
		return false;
	}
}
