package com.cyb.test.validate.bean;

import com.cyb.util.validate.jsr.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.GroupSequence;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Map;

/**
 *作者 : iechenyb<br>
 *类描述: 同一组内有序<br>
 *创建时间: 2018年10月19日
 */
@Setter
@Getter
@GroupSequence({ValidateBean2.class,ValidateBean2.First.class,ValidateBean2.Second.class})
public class ValidateBean2 {
	//@NotNull(message = "Long不能为空")
	@Digits(integer = 2,fraction = 0,message = "Lv必须为100以内的整数!",groups = {Default.class,ValidateBean2.First.class})
	private Long Lv;
	@Digits(integer = 2,fraction = 0,message = "llv必须为100以内的整数!",groups = {Default.class,ValidateBean2.Second.class})
	private long llv;
	@Digits(integer = 2,fraction = 0,message = "Iv必须为100以内的整数!",groups = {ValidateBean2.Second.class})
	private Integer Iv;
	@Digits(integer = 2,fraction = 0,message = "iiv必须为100以内的整数!",groups = {ValidateBean2.Second.class})
	private int iiv;
	@PatternAnn(message="自定义正则表达式！[1-9]{2,5}", regex = "[1-9]{2,5}",groups = {ValidateBean2.Second.class})
	private String password;
	@Phone(message="手机号码格式不正确！",groups = {ValidateBean2.Second.class})
	private String phone;
	@Email(message="邮箱格式不正确！",groups = {ValidateBean2.Second.class})
	private String email;
	@Pattern(message="hibernate正则校验不正确！[1-9]{2,5}", regexp = "[1-9]{2,5}",groups = {ValidateBean2.Second.class})
	private String pattern;
	@DecimalAnn(min=0.0,max=10.0,precision=2,message = "Dv 0-10",groups = {ValidateBean2.Second.class})
	private Double Dv;
	@DecimalAnn(min=0.0,max=10.0,precision=2,message = "ddv为0-10的数字！",groups = {ValidateBean2.Second.class})
	private double ddv;
	@ListEmpty(groups = {ValidateBean2.Second.class})
	private List<String> list;
	@MapEmpty(groups = {ValidateBean2.Second.class})
	private Map<String,Object> map;
	public interface First{}
    public interface Second{}
}
