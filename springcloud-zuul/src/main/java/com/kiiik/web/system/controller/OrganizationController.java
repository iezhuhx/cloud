package com.kiiik.web.system.controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.kiiik.pub.bean.R;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.web.employee.entity.EmployeeEntity;
import com.kiiik.web.system.service.impl.OrganizationServiceImpl;
import com.kiiik.web.system.utils.TreeNode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月7日
 */
@RestController
@RequestMapping("org")
@Api(value = "组织机构管理模块", description = "组织机构基本信息操作API", tags = "OrganizationApi")
public class OrganizationController {
	Log log = LogFactory.getLog(OrganizationController.class);
	@Autowired
	OrganizationServiceImpl orgService;
	
	@Autowired
	GenericService genericService;
	
	@ApiOperation("组织结构树")
	@GetMapping("tree")
	public R<TreeNode> orgTree(){
		return new R<TreeNode>(orgService.getOranizationTree()).success();
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 根据员工信息获取公司和部门信息<br>
	 *创建时间: 2018年11月26日
	 *@param emp
	 *@return
	 */
	@ApiOperation("查询员工信息")
	@GetMapping("findEmp")
	public R<JSONArray> listEmp(EmployeeEntity emp){
		if(!StringUtils.isEmpty(emp.getPassword())){
			emp.setPassword(null);//密码查询禁用
		}
		return orgService.listEmp(emp);
	}
	
}
