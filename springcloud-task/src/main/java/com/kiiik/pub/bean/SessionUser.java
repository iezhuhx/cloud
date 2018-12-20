package com.kiiik.pub.bean;

import java.util.List;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年10月18日
 */
public class SessionUser {

	private String userName;//页面可能要显示员工名称
	private String empNo;
	private String userId;
	private List<String> roles;

	public SessionUser(String userName,String empNo,String userId){
		this.userName = userName;
		this.empNo = empNo;
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
