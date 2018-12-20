package com.kiiik.web.employee.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.kiiik.pub.bean.R;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.mybatis.dao.GenericDao;
import com.kiiik.web.company.entity.CompanyEntity;
import com.kiiik.web.department.entity.DepartmentEntity;
import com.kiiik.web.employee.dao.EmployeeDao;
import com.kiiik.web.employee.entity.EmployeeEntity;
import com.kiiik.web.employee.service.EmployeeService;

/**
 * 业务处理层
 * 作者: iechenyb
 * 邮件: zzuchenyb@sina.com
 * 日期: 2018-11-08 09:34:39
 */
@Service
public class EmployeeServiceImpl  implements EmployeeService {
    //通用的数据库服务接口
    @Autowired
	GenericDao genericDao;
	
	//当前业务数据库服务接口
	@Autowired
	EmployeeDao  employeeDao;
	
    /**
	 * 
	 *作者 :  iechenyb<br>
	 *方法描述: 新增记录<br>
	 *创建时间: 2018-11-08 09:34:39
	 *@param 
	 *@return
	 */
	 public R<String> addEmployeeEntity(EmployeeEntity entity){
	    EmployeeEntity tmp = new EmployeeEntity();
		tmp.setLoginid(entity.getLoginid());
		if(!CollectionUtils.isEmpty(genericDao.queryDBEntityList(tmp))){
			 return new R<String>().fail("职工登录账号"+entity.getLoginid()+"已经存在!");
		}
		//查看员工信息是否存在
		DepartmentEntity department = new DepartmentEntity();
		department.setId(entity.getDepartmentid());
		department = genericDao.queryDBEntitySingle(department);
		if(department==null){
			return new R<String>().fail("员工所在部门不存在!");
		}
		CompanyEntity company = new CompanyEntity();
		company.setId(entity.getSubcompanyid1());
		company = genericDao.queryDBEntitySingle(company);
		if(company==null){
			return new R<String>().fail("员工所在公司不存在!");
		}
	 	int count = genericDao.insertDBEntity(entity);
		if(count==0){
			return new R<String>().fail("新增记录失败!");
		}else{
			return new R<String>().success("新增记录成功!");
		}
	 }
	 
	/**
	 * 
	 *作者 :  iechenyb<br>
	 *方法描述: 更新记录<br>
	 *创建时间: 2018-11-08 09:34:39
	 *@param 
	 *@return
	 */
	 public R<String> updEmployeeEntity(EmployeeEntity entity){
		EmployeeEntity tmp = new EmployeeEntity();
		tmp = genericDao.queryDBEntitySingleComplex(EmployeeEntity.class, 
					new ComplexCondition()
					.and()
					.col("loginid")
					.eq(entity.getLoginid())
		 			.and().col("id").notIn(entity.getId()));
		if(tmp!=null){
			 return  new R<String>().fail("职工登录账号["+entity.getLoginid()+"]已经存在!");
		} 
		if(!StringUtils.isEmpty(entity.getDepartmentid())){//更新时可以为空，不更新
			DepartmentEntity department = new DepartmentEntity();
			department.setId(entity.getDepartmentid());
			department = genericDao.queryDBEntitySingle(department);
			if(department==null){
				return new R<String>().fail("员工所在部门不存在!");
			}
		}
		if(!StringUtils.isEmpty(entity.getSubcompanyid1())){//更新时可以为空，不更新
			CompanyEntity company = new CompanyEntity();
			company.setId(entity.getSubcompanyid1());
			company = genericDao.queryDBEntitySingle(company);
			if(company==null){
				return new R<String>().fail("员工所在公司不存在!");
			}
		}
	 	int count = genericDao.updateDBEntityByKey(entity);
		if(count==0){
			return new R<String>().fail("更新记录失败!");
		}else{
			return new R<String>().success("更新记录成功!");
		}
	 }
	 
	/**
	 * 
	 *作者 :  iechenyb<br>
	 *方法描述: 删除记录<br>
	 *创建时间: 2018-11-08 09:34:39
	 *@param 
	 *@return
	 */
	 public R<String> delEmployeeEntity(List<Integer> ids){
		int count = genericDao.deleteDBEntityByKeyBatchs(new EmployeeEntity(),ids);
		if(count==0){
			return new R<String>().fail("删除记录失败!");
		}else{
			return new R<String>().success("删除记录成功!");
		}
	 }
 
}
