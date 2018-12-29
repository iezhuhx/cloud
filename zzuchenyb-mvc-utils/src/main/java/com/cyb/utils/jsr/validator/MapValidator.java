package com.cyb.utils.jsr.validator;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.cyb.utils.jsr.annotation.MapEmpty;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
public class MapValidator implements ConstraintValidator<MapEmpty, Map<?,?>> {
	Log log = LogFactory.getLog(MapValidator.class);
	MapEmpty constraintAnnotation;
	@Override
	public void initialize(MapEmpty constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(Map<?,?> value, ConstraintValidatorContext context) {
		if(CollectionUtils.isEmpty(value)) return false;
		System.out.println("map 校验");
		return false;
	}
}
