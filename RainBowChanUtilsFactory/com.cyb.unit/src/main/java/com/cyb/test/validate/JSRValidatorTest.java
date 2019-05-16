package com.cyb.test.validate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.cyb.bean.response.body.ResultMap;
import com.cyb.test.validate.bean.ValidateBean;
import com.cyb.test.validate.bean.ValidateBean2;
import lombok.extern.slf4j.Slf4j;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月28日
 */
@Slf4j
public class JSRValidatorTest {

	public static <T> List<String> validateCommonBeanParams(T bean) {
		List<String> tips= new ArrayList<String>();
		// 调用JSR303验证工具，校验参数
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate(bean);
		Iterator<ConstraintViolation<T>> iter = violations.iterator();
		log.info("==============================");
		if (iter.hasNext()) {
			String errMessage = iter.next().getMessage();
			log.error("validate result:"+errMessage);
			tips.add(errMessage);
		}
		return tips;
	}
	public static void main(String[] args) {
		checkBean1();
	}

    /**
     * 无序校验
     */
	public static void checkBean1(){
        ValidateBean aa = new ValidateBean();
        aa.setLv(20l);
        aa.setLlv(30);
        aa.setIv(20);
        aa.setIiv(30);
        aa.setDv(3d);
        aa.setDdv(20.36d);
        aa.setPassword("9");
        aa.setPattern("12");
        aa.setEmail("12sdf@qq.com");
        aa.setPhone("13938469052");
        List<String> a = new ArrayList<String>();
        a.add("str");
        aa.setList(a);
        aa.setMap(ResultMap.build().put("a","v"));
        JSRValidatorTest.validateCommonBeanParams(aa);
    }


    /**
     * 有序校验
     */
    public static void checkBean2(){
        ValidateBean2 aa = new ValidateBean2();
        aa.setLv(200l);
        aa.setLlv(3000);
        JSRValidatorTest.validateCommonBeanParams(aa);
    }

}
