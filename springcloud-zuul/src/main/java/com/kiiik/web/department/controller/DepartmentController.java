package com.kiiik.web.department.controller;


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
import com.kiiik.pub.bean.KiiikPage;
import com.kiiik.pub.bean.PageData;
import com.kiiik.pub.bean.R;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.web.department.entity.DepartmentEntity;
import com.kiiik.web.department.service.DepartmentService;

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
@RequestMapping("department")
@Api(value = "部门信息查询", description = "部门基本信息操作API", tags = "DepartmentApi")
public class DepartmentController {

	Log log = LogFactory.getLog(DepartmentController.class);
	/**
	*通用的service接口
	**/
	@Autowired
	GenericService genericService;
	/**
	*服务接口
	**/
    @Autowired
    private DepartmentService departmentService;

   
    /**
     * 
     *作者 : iechenyb<br>
     *数据列表<br>
     *创建时间: 2018-11-08 09:34:39
     *@param 
     *@return
     */
    @GetMapping("/list")
    @ApiOperation("列表信息")
    public R<PageData<DepartmentEntity>> list(DepartmentEntity entity,KiiikPage page){
    	if(page.needAll()){//当分页参数不传时传回所有记录
		    return new R<PageData<DepartmentEntity>>(new PageData<DepartmentEntity>(genericService.queryDBEntityListLike(entity))).success();
	   }else{
			Page<DepartmentEntity> datas = genericService.queryDBEntityListLike(entity, page);
			return new R<PageData<DepartmentEntity>>(new PageData<DepartmentEntity>(datas,page)).success();
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
	public R<String> addDepartmentEntity(@RequestBody DepartmentEntity entity){
		return departmentService.addDepartmentEntity(entity);
		
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
	public R<String> updDepartmentEntity(@RequestBody DepartmentEntity entity){
		return departmentService.updDepartmentEntity(entity);
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
	public R<String> delDepartmentEntity(@RequestParam(value = "ids") List<Integer> ids){
		return departmentService.delDepartmentEntity(ids);
	}
}
