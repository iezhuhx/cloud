package com.kiiik.web.employee.entity;

import java.io.Serializable;
import com.kiiik.pub.mybatis.annotation.DBEntity;
import com.kiiik.pub.mybatis.annotation.DBColumn;
import com.kiiik.pub.mybatis.annotation.KeyColumn;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 数据库实体
 * 作者: iechenyb
 * 邮件: zzuchenyb@sina.com
 * 日期: 2018-11-08 09:34:39
 */
@DBEntity("t_sys_employee")
public class EmployeeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")
	@ApiModelProperty(value="主键，请求时参数忽略")
	private Integer id;
	
			
	@DBColumn("loginid")
	@ApiModelProperty(value="仅更新或者查询时，传参数！")
	private String loginid;
	
			
	@DBColumn("password")
	@ApiModelProperty(value="仅更新或者查询时，传参数！")
	private String password;
	
			
	@DBColumn("lastname")
	@ApiModelProperty(value="仅更新或者查询时，传参数！")
	private String lastname;
	
			
	@DBColumn("mobile")
	@ApiModelProperty(value="仅更新或者查询时，传参数！")
	private String mobile;
	
			
	@DBColumn("email")
	@ApiModelProperty(value="仅更新或者查询时，传参数！")
	private String email;
	
			
	@DBColumn("departmentid")
	@ApiModelProperty(value="仅更新或者查询时，传参数！")
	private Integer departmentid;
	
			
	@DBColumn("subcompanyid1")
	@ApiModelProperty(value="仅更新或者查询时，传参数！")
	private Integer subcompanyid1;
	
	
	//设置值方法
	public void setId(Integer id) {
		this.id = id;
	}
	//获取值方法
	public Integer getId() {
		return id;
	}
	//设置值方法
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	//获取值方法
	public String getLoginid() {
		return loginid;
	}
	//设置值方法
	public void setPassword(String password) {
		this.password = password;
	}
	//获取值方法
	public String getPassword() {
		return password;
	}
	//设置值方法
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	//获取值方法
	public String getLastname() {
		return lastname;
	}
	//设置值方法
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	//获取值方法
	public String getMobile() {
		return mobile;
	}
	//设置值方法
	public void setEmail(String email) {
		this.email = email;
	}
	//获取值方法
	public String getEmail() {
		return email;
	}
	//设置值方法
	public void setDepartmentid(Integer departmentid) {
		this.departmentid = departmentid;
	}
	//获取值方法
	public Integer getDepartmentid() {
		return departmentid;
	}
	//设置值方法
	public void setSubcompanyid1(Integer subcompanyid1) {
		this.subcompanyid1 = subcompanyid1;
	}
	//获取值方法
	public Integer getSubcompanyid1() {
		return subcompanyid1;
	}
}
