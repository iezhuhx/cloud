package com.kiiik.web.example.controller;

import java.util.ArrayList;
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

import com.github.pagehelper.Page;
import com.kiiik.pub.ann.IgnoreApi;
import com.kiiik.pub.bean.KiiikPage;
import com.kiiik.pub.bean.PageData;
import com.kiiik.pub.bean.R;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.utils.RandomUtils;
import com.kiiik.web.company.entity.CompanyEntity;
import com.kiiik.web.example.bean.TestBean;
import com.kiiik.web.log.bean.SystemLog;

import io.swagger.annotations.ApiOperation;
/**
 * 
 * @author DHUser
 *通用的单元测试用例，测试dao层的逻辑
 */
@RestController
@RequestMapping("generic")
@IgnoreApi
public class GenericController {
	Log log = LogFactory.getLog(GenericController.class);

	@Autowired
	GenericService genericService;
	
	
	@ApiOperation("菜单信息查询-分页查询，包含全量查询")
	@GetMapping("listLogs")
	public R<PageData<SystemLog>> listMenus(SystemLog log,KiiikPage page){
		if(page.needAll()){//当分页参数不传时传回所有记录
		    return new R<PageData<SystemLog>>(new PageData<SystemLog>(genericService.queryDBEntityListLike(log))).success();
	   }else{
			Page<SystemLog> datas = genericService.queryDBEntityListLike(log, page);
			return new R<PageData<SystemLog>>(new PageData<SystemLog>(datas,page)).success();
	   }
	}
	
	
	public TestBean getRandomBean(){
		TestBean po = null;
		po = new TestBean();
		// po.setId(i); 不设置 采用自增长方式
		po.setName(RandomUtils.getChineaseName());
		po.setAccount(RandomUtils.getPingYin(po.getName()));
		po.setPassword(RandomUtils.getPassWord(8));
		return po;
	}
	
	@GetMapping("/add")
	@ApiOperation("数据生成")
	public R<String> addUser(Integer total) {
		
		genericService.insertDBEntity(getRandomBean());//单个插入测试
		
		genericService.insertDBEntityT(getRandomBean());
		
		List<TestBean> lstTB = new ArrayList<TestBean>(1);
		lstTB.add(getRandomBean());
		genericService.insertDBEntityBatchT(lstTB);//根据泛型批量插入
		
		
		List<Object> lstTBObj = new ArrayList<Object>(1);
		lstTBObj.add(getRandomBean());
		genericService.insertDBEntityBatch(lstTBObj);//根据对象插入
		
		
		return new R<String>("执行成功！").success();
	}

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 根据主键删除记录<br>
	 * 创建时间: 2017年7月15日hj12
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("delete")
	public R<String> delUserByKey() throws Exception {
		Integer id =RandomUtils.getNum(1,1000);
		TestBean po = new TestBean();
		po.setId(id.toString());
		genericService.deleteDBEntityByKey(po);//根据主键进行删除
		genericService.deleteDBEntityByKeyT(po);//根据泛型删除
		
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1000);ids.add(2000);
		genericService.deleteDBEntityByKeyBatchs(po, ids);
		genericService.deleteDBEntityByKeyBatchsT(po, ids);
		genericService.deleteDBEntityByKeyBatchs(CompanyEntity.class, ids);
		
		po.setName("iechenyb");//根据非空属性进行删除
		genericService.deleteDBEntity(po);
		genericService.deleteDBEntityT(po);
		return new R<String>("执行成功！").success();
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: page参数可以不传<br>
	 *创建时间: 2018年11月20日
	 *@param bean
	 *@param page
	 *@return
	 */
	@ApiOperation("根据分页查询(单表),排序参数 为password asc,name desc")
	@GetMapping("queryByPage")
	public R<PageData<TestBean>> queryUserByPage(TestBean entity, KiiikPage page) {
		
		Page<TestBean> pageData = null;
		List<TestBean> data;
		if(page.needAll()){
			data = genericService.queryDBEntityList(entity);
			data = genericService.queryDBEntityList(entity, "id asc");
			data = genericService.queryDBEntityListLike(entity);
			data = genericService.queryDBEntityListLike(entity,"id asc");
			System.out.println(new R<PageData<TestBean>>(new PageData<TestBean>(data)).success());
		}else{
			pageData = genericService.queryDBEntityList(entity,page);
			pageData = genericService.queryDBEntityList(entity, page,"id asc");
			pageData = genericService.queryDBEntityList(entity, page.getCurPage(),page.getPageSize(),"id asc"); 
			pageData = genericService.queryDBEntityListLike(entity,page);
			pageData = genericService.queryDBEntityListLike(entity, page,"id asc");
			pageData = genericService.queryDBEntityListLike(entity, page.getCurPage(),page.getPageSize(),"id asc");
			System.out.println(new R<PageData<TestBean>>(new PageData<TestBean>(pageData)).success());
		}
		return new R<PageData<TestBean>>().success("执行成功！");
	}

	@GetMapping("queryComplex")
	public R<List<TestBean>> queryUserComplex(KiiikPage page) {
		
		ComplexCondition condition = new ComplexCondition().col("id").gt(5);
		condition = new ComplexCondition().col("id").gt(5)
				.or(new ComplexCondition().col("name").eq("123").and().col("id").notNull());
		// 复杂查询
		// 查出 id > 5 且 buy_date <= 当前 的 list .and().col("buyDate").lte(new
		// Date())
		List<TestBean> data = null;
		Page<TestBean> paged = null;
		if(page.needAll()){
			data = genericService.queryDBEntityListComplex(TestBean.class, condition);
			TestBean obj = genericService.queryDBEntitySingleComplex(TestBean.class, condition);
			System.out.println(obj+"\n"+data);
		}else{
			paged = genericService.queryDBEntityListComplex(TestBean.class, condition,page);
			paged = genericService.queryDBEntityListComplex(TestBean.class, condition,page,"id asc");
			paged = genericService.queryDBEntityListComplex(TestBean.class, condition,page.getCurPage(),page.getPageSize(),"id asc");
			paged= genericService.queryDBEntityListComplexTop(TestBean.class, condition,100,"id asc");
			System.out.println(paged);
		}
		// 查出 id>5 或(test = 123 且 uid = null)
		// or 和 and 都有 or() or(Object)/ and() and(Object) 两种可以嵌套使用
		return  new R<List<TestBean>>().success();

	}
}
