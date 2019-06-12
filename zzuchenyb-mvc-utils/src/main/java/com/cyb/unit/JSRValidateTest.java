package com.cyb.unit;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;

import com.cyb.unit.base.JunitBase;
import com.cyb.utils.jsr.bean.ValidateBean;
import com.cyb.utils.jsr.utils.JSRValidatorUtils;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年12月28日
 */
public class JSRValidateTest extends JunitBase {
	Log log = LogFactory.getLog(JSRValidateTest.class);

	@NotNull(message = "reason信息不可以为空")
	@Pattern(regexp = "[1-7]{1}", message = "reason的类型值为1-7中的一个类型")
	private String reason;// 订单取消原因

	@Test
	public void validateCommonBeanParams() {
		ValidateBean bean = new ValidateBean();
		bean.setEmail("1111@qq.com");
		JSRValidatorUtils.validateCommonBeanParams(bean);
	}

	public void validateParams() {
		// 调用JSR303验证工具，校验参数
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<JSRValidateTest>> violations = validator.validate(this);
		Iterator<ConstraintViolation<JSRValidateTest>> iter = violations.iterator();
		if (iter.hasNext()) {
			String errMessage = iter.next().getMessage();
			throw new ValidationException(errMessage);
		}
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Test
	public void validateParam() {
		JSRValidateTest validateTestClass = new JSRValidateTest();
		validateTestClass.setReason("2");
		validateTestClass.validateParams(); // 调用验证的方法
	}
}
