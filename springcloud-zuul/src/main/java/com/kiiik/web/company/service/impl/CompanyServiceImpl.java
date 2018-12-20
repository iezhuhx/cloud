package com.kiiik.web.company.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kiiik.pub.bean.R;
import com.kiiik.pub.contant.RedisKeyContants;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.service.BaseService;
import com.kiiik.web.company.dao.CompanyDao;
import com.kiiik.web.company.entity.CompanyEntity;
import com.kiiik.web.company.service.CompanyService;
import com.kiiik.web.department.entity.DepartmentEntity;
import com.kiiik.web.employee.entity.EmployeeEntity;

/**
 * 业务处理层
 * 作者: iechenyb
 * 邮件: zzuchenyb@sina.com
 * 日期: 2018-11-08 09:34:39
 */
@Service
public class CompanyServiceImpl extends BaseService implements CompanyService {
	//当前业务数据库服务接口
	@Autowired
	CompanyDao  companyDao;
	
    /**
	 * 
	 *作者 :  iechenyb<br>
	 *方法描述: 新增记录<br>
	 *创建时间: 2018-11-08 09:34:39
	 *@param 
	 *@return
	 */
	 public R<String> addCompanyEntity(CompanyEntity entity){
		 
		 CompanyEntity tmp = new CompanyEntity();
		 tmp.setSubcompanyname(entity.getSubcompanyname());//查询公司信息是否存在
		 if(!CollectionUtils.isEmpty(this.genericDao.queryDBEntityList(tmp))){
			 return  new R<String>().fail("公司名称["+entity.getSubcompanyname()+"]已经存在!");
		 }
		 
		 tmp = new CompanyEntity();
		 tmp.setId(entity.getSupsubcomid());//查询上级公司是否存在
		 if(CollectionUtils.isEmpty(this.genericDao.queryDBEntityList(tmp))){
			 return  new R<String>().fail("上级公司不存在!");
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
	 public R<String> updCompanyEntity(CompanyEntity entity){
		 CompanyEntity tmp = new CompanyEntity();
		 tmp = genericDao.queryDBEntitySingleComplex(CompanyEntity.class, 
					new ComplexCondition()
					.and()
					.col("subcompanyname")
					.eq(entity.getSubcompanyname())
		 			.and().col("id").notIn(entity.getId()));
		 if(tmp!=null){
			 return  new R<String>().fail("公司名称["+entity.getSubcompanyname()+"]已经存在!");
		 }
		 
		 tmp = new CompanyEntity();
		 tmp.setId(entity.getSupsubcomid());//查询上级公司是否存在
		 if(CollectionUtils.isEmpty(this.genericDao.queryDBEntityList(tmp))){
			 return  new R<String>().fail("上级公司不存在!");
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
	 public R<String> delCompanyEntity(List<Integer> ids){
	    //公司是否存在子公司
		 if(!CollectionUtils.isEmpty(
	    		 genericDao.queryDBEntityListComplex(CompanyEntity.class,
	    		new ComplexCondition()
	    		.and()
	    		.col("supsubcomid").inList(ids))
	    		)){
	    	return new R<String>().fail("存在子公司信息，不能删除！");
	    }
	    //公司是否存在子部门
	    if(!CollectionUtils.isEmpty(
	    		 genericDao.queryDBEntityListComplex(DepartmentEntity.class,
	    		new ComplexCondition()
	    		.and()
	    		.col("subcompanyid1").inList(ids))
	    		)){
	    	return new R<String>().fail("存在子部门信息，不能删除！");
	    }
	    //公司是否存在员工
	    if(!CollectionUtils.isEmpty(
	    		 genericDao.queryDBEntityListComplex(EmployeeEntity.class,
	    		new ComplexCondition()
	    		.and()
	    		.col("subcompanyid1").inList(ids))
	    		)){
	    	return new R<String>().success("存在员工信息，不能删除!");
	    }
	    
	    CompanyEntity entity = new CompanyEntity();
		int count = genericDao.deleteDBEntityByKeyBatchs(entity,ids);
		if(count==0){
			return new R<String>().fail("删除记录失败!");
		}else{
			return new R<String>().success("删除记录成功!");
		}
	 }
	 
	 
	 @Cacheable(value={RedisKeyContants.CompanyNameMap},keyGenerator=RedisKeyContants.KEYGENERATOR)
	 public Map<String,String> getCompanyNameById(){
		System.err.println("查询公司名称信息！");
		List<CompanyEntity> data = genericDao.queryDBEntityList(new CompanyEntity());
		Map<String,String> cnMap = null;
		if(data!=null&&data.size()>0){
			cnMap = new HashMap<String, String>();
			for(CompanyEntity com:data){
				cnMap.put(com.getId().toString(), com.getSubcompanyname());
			}	
		}
		return cnMap;
	 }
 
}
