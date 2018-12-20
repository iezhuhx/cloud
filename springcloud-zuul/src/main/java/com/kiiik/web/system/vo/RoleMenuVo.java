package com.kiiik.web.system.vo;

import java.io.Serializable;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月23日
 */
public class RoleMenuVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String roleName;
	String url;
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
