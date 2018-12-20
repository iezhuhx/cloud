package com.kiiik.web.system.mapper;

import java.util.List;

import com.kiiik.web.system.vo.RoleMenuVo;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
public interface MenuMapper {
	public List<RoleMenuVo> systemRoleMenus();//获取系统的所有菜单与角色关系 如 role_admin <--> ur1,ur2
}
