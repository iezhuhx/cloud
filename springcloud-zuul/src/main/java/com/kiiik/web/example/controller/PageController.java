package com.kiiik.web.example.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月10日
 */

import com.github.pagehelper.Page;
import com.kiiik.pub.ann.IgnoreApi;
import com.kiiik.pub.bean.R;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.web.example.bean.TestBean;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("page")
@IgnoreApi
public class PageController {
	Log log = LogFactory.getLog(PageController.class);

	@Autowired
	GenericService genericService;
	// 分页查询  每次重新查询时都需要设置成第一页   ，更具当前条件查询进行翻页
	@ApiOperation("根据分页查询(单表表属性),排序参数 为password asc,name desc")
	@GetMapping("queryByPage1")
	public R<Page<TestBean>> queryUserByPage1(int page, int pageSize,String[] orders) {
		TestBean po = new TestBean();
		//po.setAccount(account);//全表查询
		Page<TestBean> page1 = genericService.queryDBEntityList(po, page, pageSize,orders);// "password asc", "name desc"
		page1.getTotal();
		return new R<Page<TestBean>>(page1).success();
	}
	
	
	@ApiOperation("根据分页查询(单表自定义属性),排序参数 为password asc,name desc")
	@GetMapping("queryByPage2")
	public R<Page<TestBean>> queryUserByPage2(int page, int pageSize,String[] orders) {
		TestBean po = new TestBean();
		//po.setAccount(account);//全表查询
		Page<TestBean> page1 = genericService.queryDBEntityList(po, page, pageSize,orders);// "password asc", "name desc"
		return new R<Page<TestBean>>(page1).success();
	}
}
