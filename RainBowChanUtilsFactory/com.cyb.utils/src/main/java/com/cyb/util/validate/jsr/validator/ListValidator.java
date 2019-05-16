package com.cyb.util.validate.jsr.validator;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cyb.util.string.CollectionUtil;
import com.cyb.util.validate.jsr.annotation.ListEmpty;
import lombok.extern.slf4j.Slf4j;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
@Slf4j
public class ListValidator implements ConstraintValidator<ListEmpty, List<?>> {
	ListEmpty constraintAnnotation;
	public void initialize(ListEmpty constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;
	}

	public boolean isValid(List<?> value, ConstraintValidatorContext context) {
		if(!CollectionUtil.isEmpty(value)) return true;
		System.out.println("list校验");
		return false;
	}
}
