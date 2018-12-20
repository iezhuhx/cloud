package com.kiiik.web.system.vo;

import java.io.Serializable;

/**
 *作者 : iechenyb<br>
 *类描述: 修改或者新增某个角色的菜单时，传参数对象<br>
 *创建时间: 2018年10月23日
 */
public class RoleMenuVo2 implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer[] menuIds;
	Integer roleId;
	public Integer[] getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(Integer[] menuIds) {
		this.menuIds = menuIds;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
}
