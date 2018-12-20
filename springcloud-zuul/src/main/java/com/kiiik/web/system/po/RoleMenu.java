package com.kiiik.web.system.po;

import com.kiiik.pub.mybatis.annotation.DBColumn;
import com.kiiik.pub.mybatis.annotation.DBEntity;
import com.kiiik.pub.mybatis.annotation.KeyColumn;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@DBEntity("t_sys_role_menu")
@ApiModel("角色菜单关系")
public class RoleMenu {
	/**
	 * @作者:iechenyb</br>
	 * @功能描述：</br>
	 * @创建时间：2016年11月3日下午1:31:32</br>
	 */
	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")	
	@ApiModelProperty(value="主键")
	private Integer id;  
	
	@DBColumn("roleid")
	@ApiModelProperty(value="角色主键")
	private Integer roleId; 
	
	@DBColumn("menuid")
	@ApiModelProperty(value="菜单主键")
	private Integer menuId;
	public Integer getId() {
		return id;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	} 	
}
