package com.kiiik.web.example.jsr.bean;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.kiiik.web.example.jsr.anno.IsPhone;
import com.sun.istack.internal.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
@ApiModel("统一验证bean")
public class ValidateBean {
	@NotBlank(message="用户名不能为空！")//字符串不能为空柏
	@ApiModelProperty("用户名,不可以为空")
	private String username;
	
	@Min(6)//必须为整数，否则报错误
	@NotNull
	@ApiModelProperty("必须大于等于6的整数！")
	private Long number;
	
	@NotNull
	@NotBlank(message="密码不能为空！")
	@ApiModelProperty("密码，非空")
	private String password;
	
	
	@IsPhone
	@Length(min=11, max=11)
	private String phone;
	
	/*@NotEmpty
	@ApiModelProperty("对象测试！")
	ChildBean user;
	public ChildBean getUser() {
		return user;
	}
	public void setUser(ChildBean user) {
		this.user = user;
	}*/
	
	@NotEmpty
	private  List<String> list;
	
	@NotEmpty
	private  Map<String,String> map;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
