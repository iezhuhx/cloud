package com.kiiik.web.system.mapper;

import java.util.List;

import com.github.pagehelper.Page;
import com.kiiik.web.system.po.Menu;
import com.kiiik.web.system.po.User;
import com.kiiik.web.system.vo.UserCompanyInfor;
import com.kiiik.web.system.vo.UserRoleVo;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
public interface UserMapper {
	List<UserRoleVo> getUserRoles(Integer userId);//获取用户的角色
	List<Menu> getUserMenus(Integer userId);//获取用户的菜单
	Page<User> getUsers(User user);
	UserCompanyInfor getUserCompanyInfor(String empNo);
}
