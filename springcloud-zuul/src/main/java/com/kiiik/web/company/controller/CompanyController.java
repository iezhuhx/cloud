package com.kiiik.web.company.controller;


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
import com.kiiik.web.company.entity.CompanyEntity;
import com.kiiik.web.company.service.CompanyService;

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
@RequestMapping("company")
@Api(value = "公司信息查询", description = "公司基本信息操作API", tags = "CompanyApi")
public class CompanyController {

	Log log = LogFactory.getLog(CompanyController.class);
	/**
	*通用的service接口
	**/
	@Autowired
	GenericService genericService;
	/**
	*服务接口
	**/
    @Autowired
    private CompanyService companyService;

   
    /**
     * 
     *作者 : iechenyb<br>
     *数据列表<br>
     *创建时间: 2018-11-08 09:34:39
     *@param 
     *@return
     */
    @ApiOperation("分页查询")
    @GetMapping(value="/list")
    public R<PageData<CompanyEntity>> list(CompanyEntity entity,KiiikPage page){
    	if(page.needAll()){//当分页参数不传时传回所有记录
		    return new R<PageData<CompanyEntity>>(new PageData<CompanyEntity>(genericService.queryDBEntityListLike(entity))).success();
	   }else{
			Page<CompanyEntity> datas = genericService.queryDBEntityListLike(entity, page);
			return new R<PageData<CompanyEntity>>(new PageData<CompanyEntity>(datas,page)).success();
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
	public R<String> addCompanyEntity(@RequestBody CompanyEntity entity){
		return companyService.addCompanyEntity(entity);
		
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
	public R<String> updCompanyEntity(@RequestBody CompanyEntity entity){
		return companyService.updCompanyEntity(entity);
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
	public R<String> delCompanyEntity(@RequestParam("ids") List<Integer> ids){
		return companyService.delCompanyEntity(ids);
	}
}
