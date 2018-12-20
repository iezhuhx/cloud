package com.kiiik.web.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户角色关系参数")
public class UserRole2  {
	
	@ApiModelProperty("角色id，为空时，表示删除！")
	Integer[] roleIds;
	
	@ApiModelProperty("用户id，不能为空！")
	Integer userId;
	
	public Integer[] getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(Integer[] roleIds) {
		this.roleIds = roleIds;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
