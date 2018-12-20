package com.kiiik.web.system.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.kiiik.pub.bean.R;
import com.kiiik.pub.service.BaseService;
import com.kiiik.web.company.service.CompanyService;
import com.kiiik.web.department.service.DepartmentService;
import com.kiiik.web.employee.entity.EmployeeEntity;
import com.kiiik.web.system.mapper.OrganizationMapper;
import com.kiiik.web.system.utils.OrganizationTreeUtils;
import com.kiiik.web.system.utils.TreeNode;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月7日
 */
@Service
public class OrganizationServiceImpl extends BaseService {
	
	@Autowired
	OrganizationMapper orgMapper;
	
	public TreeNode getOranizationTree(){
		List<TreeNode> departs = orgMapper.getDepartmentList();
		List<TreeNode> companys = orgMapper.getCompanyList();
		TreeNode rootDept = new TreeNode("0","树根","0");
		TreeNode rootComp = new TreeNode("0","树根","0");
		OrganizationTreeUtils.madeTree(departs, rootDept);//部门组装好
		//遍历公司，如果一级部门的公司id等于当前公司，则添加到公司的部门列表上去
		for(TreeNode company:companys){
			List<TreeNode> departments = rootDept.getChildren();
			for(TreeNode department:departments){
				//判断一级部门节点的所在公司信息
				if(department.getReservedField().equals(company.getId())){
					if(CollectionUtils.isEmpty(company.getDeparments())){
						company.setDeparments(new ArrayList<TreeNode>());
					}
					company.getDeparments().add(department);
				}
			}
		}
		OrganizationTreeUtils.madeTree(companys, rootComp);//部门组装好
		return rootComp;	
	}
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	UserServiceImpl userService;
	
	public R<JSONArray> listEmp(EmployeeEntity emp){
		emp.setPassword(null);//密码查询禁用e.
		List<EmployeeEntity> es =  genericDao.queryDBEntityList(emp);
		System.out.println(es);
		if(es!=null&&es.size()>0){
			JSONArray arr = (JSONArray) JSON.toJSON(es);
			int total = arr.size();
			for(int i=0;i<total;i++){
				JSONObject em = (JSONObject) arr.get(i);
				if(companyService.getCompanyNameById().get(em.getString("subcompanyid1"))!=null){
					em.put("companyName", companyService.getCompanyNameById().get(em.getString("subcompanyid1")));
				}
				if(departmentService.getDepartmentNameById().get(em.getString("departmentid"))!=null){
					em.put("departmentName", departmentService.getDepartmentNameById().get(em.getString("departmentid")));
				}
			}
			return new R<JSONArray>(arr).success();
		}else{
			return new R<JSONArray>(new JSONArray()).success();
		}
	}
	class TransferJsonSerializer extends JsonSerializer<Object> {  
	    @Override  
	    public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider)  
	            throws IOException, JsonProcessingException {  
	        jgen.writeString("");
	    }  
	} 
}
