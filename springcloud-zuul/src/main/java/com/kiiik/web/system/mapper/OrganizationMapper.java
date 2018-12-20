package com.kiiik.web.system.mapper;

import java.util.List;

import com.kiiik.web.system.utils.TreeNode;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月7日
 */
public interface OrganizationMapper {
	public List<TreeNode> getCompanyList();
	public List<TreeNode> getDepartmentList();
}
