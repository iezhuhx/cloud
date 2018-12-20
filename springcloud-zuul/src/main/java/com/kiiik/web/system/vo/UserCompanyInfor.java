package com.kiiik.web.system.vo;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月26日
 */
@ApiModel("员工相关信息")
public class UserCompanyInfor implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subcompanyname,email,departmentname,mobile;
    
	@ApiModelProperty("公司名称")
	public String getSubcompanyname() {
		return subcompanyname;
	}

	public void setSubcompanyname(String subcompanyname) {
		this.subcompanyname = subcompanyname;
	}
    
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    @ApiModelProperty("部门信息")
	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
