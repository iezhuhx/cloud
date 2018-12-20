package com.kiiik.web.employee.controller;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.kiiik.pub.ann.KiiikCachesParam;
import com.kiiik.pub.ann.KiiikCachesParams;
import com.kiiik.pub.bean.KiiikPage;
import com.kiiik.pub.bean.PageData;
import com.kiiik.pub.bean.R;
import com.kiiik.pub.contant.RedisKeyContants;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.web.employee.entity.EmployeeEntity;
import com.kiiik.web.employee.service.EmployeeService;
import com.kiiik.web.system.service.impl.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 请求控制层
 *
 * 作者: iechenyb
 * 邮件: zzuchenyb@sina.com
 * 日期: 2018-11-08 09:34:39
 */
@RestController
@RequestMapping("employee")
@Api(value = "职工信息查询", description = "职工基本信息操作API", tags = "EmployeeApi")
public class EmployeeController {

	Log log = LogFactory.getLog(EmployeeController.class);
	/**
	*通用的service接口
	**/
	@Autowired
	GenericService genericService;
	/**
	*服务接口
	**/
    @Autowired
    private EmployeeService employeeService;

     /**
     * 
     *作者 : iechenyb<br>
     *数据列表分页查询<br>
     *创建时间: 2018-11-08 09:34:39
     *@param 
     *@return
     */
	@GetMapping("list")
	@ApiOperation("分页查询")
	public R<PageData<EmployeeEntity>> listUsersPage(EmployeeEntity entity,KiiikPage page) {
		if(page.needAll()){//当分页参数不传时传回所有记录
		    return new R<PageData<EmployeeEntity>>(new PageData<EmployeeEntity>(genericService.queryDBEntityListLike(entity))).success();
	   }else{
			Page<EmployeeEntity> datas = genericService.queryDBEntityListLike(entity, page);
			return new R<PageData<EmployeeEntity>>(new PageData<EmployeeEntity>(datas,page)).success();
	   }
	}
    
    /**
     * 
     *作者 : iechenyb<br>
     *新增记录<br>
     *创建时间: 2018-11-08 09:34:39
     *@param 
     *@return
     */
	@PostMapping("add")
	@ApiOperation("新增信息")
	@KiiikCachesParams(
	  caches = { 
		@KiiikCachesParam(cacheName=RedisKeyContants.EMPNOUSERNAMEMAP,clazz=UserServiceImpl.class)
	  }
	)
	public R<String> addEmployeeEntity(@RequestBody EmployeeEntity entity){
		return employeeService.addEmployeeEntity(entity);
		
	}
	
	/**
     * 
     *作者 : iechenyb<br>
     *更新记录<br>
     *创建时间: 2018-11-08 09:34:39
     *@param 
     *@return
     */
	@PutMapping("update")
	@ApiOperation("更新信息")
	@KiiikCachesParams(
	  caches = { 
		@KiiikCachesParam(cacheName=RedisKeyContants.EMPNOUSERNAMEMAP,clazz=UserServiceImpl.class)
	  }
	)
	public R<String> updEmployeeEntity(@RequestBody EmployeeEntity entity){
		return employeeService.updEmployeeEntity(entity);
	}
	
	 /**
     * 
     *作者 : iechenyb<br>
     *根据主键删除记录<br>
     *创建时间: 2018-11-08 09:34:39
     *@param 
     *@return
     */
	@DeleteMapping("deleteByIds")
	@ApiOperation("根据主键删除信息")
	@KiiikCachesParams(
	  caches = { 
		@KiiikCachesParam(cacheName=RedisKeyContants.EMPNOUSERNAMEMAP,clazz=UserServiceImpl.class)
	  }
	)
	public R<String> delEmployeeEntity(@RequestParam("ids") List<Integer> ids){
		return employeeService.delEmployeeEntity(ids);
	}
}
