package com.cyb.utils.jsr.utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月28日
 */
public class JSRValidatorUtils {
	Log log = LogFactory.getLog(JSRValidatorUtils.class);
	
	public static <T> List<String> validateCommonBeanParams(T bean) {
		List<String> tips= new ArrayList<>();
		// 调用JSR303验证工具，校验参数
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate(bean);
		Iterator<ConstraintViolation<T>> iter = violations.iterator();
		if (iter.hasNext()) {
			String errMessage = iter.next().getMessage();
			tips.add(errMessage);
		}
		System.out.println(tips);
		return tips;
	}
}
