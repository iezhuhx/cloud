package com.kiiik.web.example.jsr.bean;
import javax.validation.constraints.Min;

import com.sun.istack.internal.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 *作者 : iechenyb<br>
 *类描述: 
 *@NotEmpty 用在集合类上面
  @NotBlank 用在String上面
  @NotNull  用在基本类型上<br>
 *创建时间: 2018年10月19日
 */
@ApiModel("嵌套bean")
public class ChildBean {
	@Min(6)//必须为整数，否则报错误
	@NotNull
	@ApiModelProperty("必须大于等于6的整数！")
	private Long number;
	
	@NotNull
	@ApiModelProperty("密码非空！")
	private String password;

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
