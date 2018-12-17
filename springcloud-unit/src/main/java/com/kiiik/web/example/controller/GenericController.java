package com.kiiik.web.example.controller;

import java.util.List;

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

import com.kiiik.pub.bean.res.ResultBean;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.utils.RandomUtils;
import com.kiiik.web.example.bean.TestBean;

@RestController
@RequestMapping("generic")
public class GenericController {
	Log log = LogFactory.getLog(GenericController.class);

	@Autowired
	GenericService genericService;

	@SuppressWarnings("unchecked")
	@GetMapping("/add")
	public ResultBean<String> addUser(Integer total) {
		TestBean po = null;
		if (total == null) {
			total = 10;
		}
		for (int i = 0; i < total; i++) {
			po = new TestBean();
			// po.setId(i); 不设置 采用自增长方式
			po.setName(RandomUtils.getChineaseName());
			po.setAccount(RandomUtils.getPingYin(po.getName()));
			po.setPassword(RandomUtils.getPassWord(8));
			genericService.insertDBEntity(po);
			try {

			} catch (Exception e) {
				// 忽略生产中文乱码的问题
			}
		}
		return new ResultBean<String>("执行成功！").success();
	}

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 根据主键删除记录<br>
	 * 创建时间: 2017年7月15日hj12
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("delByKey")
	public ResultBean<String> delUserByKey(Integer id) {
		TestBean po = new TestBean();
		po.setId(id);
		genericService.deleteDBEntityByKey(po);
		return new ResultBean<String>("执行成功！").success();
	}

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 根据属性删除记录<br>
	 * 创建时间: 2017年7月15日hj12
	 * 
	 * @param po
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("del")
	public ResultBean<Integer> delUser(TestBean po) {
		int count = genericService.deleteDBEntity(po);
		return new ResultBean<Integer>(count).success();
	}

	@GetMapping("upd")
	@SuppressWarnings("unchecked")
	public ResultBean<String> updUser(TestBean po) {
		genericService.updateDBEntityByKey(po);
		return new ResultBean<String>("执行成功！").success();
	}
	
	
	
	
	
	
	

	@SuppressWarnings("unchecked")
	@GetMapping("queryMapping")
	public ResultBean<List<TestBean>> queryUser(TestBean po) {
		List<TestBean> data = genericService.queryDBEntityList(po);
		return new ResultBean<List<TestBean>>(data).success();
	}

	// 排序查询
	@SuppressWarnings("unchecked")
	@GetMapping("queryMappingOrder")
	public ResultBean<List<TestBean>> queryUserOrder(TestBean po) {
		List<TestBean> data = genericService.queryDBEntityList(po, "password asc", "name desc");
		return new ResultBean<List<TestBean>>(data).success();
	}

	// 分页查询
	@SuppressWarnings("unchecked")
	@GetMapping("queryByPage")
	public ResultBean<List<TestBean>> queryUserByPage(String account, int page, int pageSize) {
		TestBean po = new TestBean();
		po.setAccount(account);
		List<TestBean> data = genericService.queryDBEntityList(po, page, pageSize, "password asc", "name desc");
		return new ResultBean<List<TestBean>>(data).success();
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@GetMapping("queryComplex")
	public ResultBean<List<TestBean>> queryUserComplex() {
		// 复杂查询
		// 查出 id > 5 且 buy_date <= 当前 的 list .and().col("buyDate").lte(new
		// Date())
		List<TestBean> list4 = genericService.queryDBEntityListComplex(TestBean.class, new ComplexCondition().col("id").gt(5));
		// 查出 id>5 或(test = 123 且 uid = null)
		// or 和 and 都有 or() or(Object)/ and() and(Object) 两种可以嵌套使用
		List<TestBean> list5 = genericService.queryDBEntityListComplex(TestBean.class, new ComplexCondition().col("id").gt(5)
				.or(new ComplexCondition().col("name").eq("123").and().col("id").notNull()));
		return  new ResultBean<List<TestBean>>(list4).success();

	}
}
