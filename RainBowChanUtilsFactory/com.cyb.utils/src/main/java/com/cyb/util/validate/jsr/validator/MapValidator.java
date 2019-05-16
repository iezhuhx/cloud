package com.cyb.util.validate.jsr.validator;
import com.cyb.util.string.CollectionUtil;
import com.cyb.util.validate.jsr.annotation.MapEmpty;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
@Slf4j
public class MapValidator implements ConstraintValidator<MapEmpty, Map<?,?>> {
	MapEmpty constraintAnnotation;
	public void initialize(MapEmpty constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;
	}

	public boolean isValid(Map<?,?> value, ConstraintValidatorContext context) {
		if(!CollectionUtil.isEmpty(value)) return true;
		log.info(constraintAnnotation.message());
		return false;
	}
}
