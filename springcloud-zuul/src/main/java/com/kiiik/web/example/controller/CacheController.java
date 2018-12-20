package com.kiiik.web.example.controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.pub.ann.IgnoreApi;
import com.kiiik.web.example.bean.TestBean;
/**
 *作者 : iechenyb<br>
 *类描述: 缓存一般写到service层，不写到controller，目前写法仅供测试！<br>
 *创建时间: 2018年11月20日
 */
import com.kiiik.web.example.service.CacheService;
import com.kiiik.web.rsa.service.RsaService;
@RestController
@RequestMapping("cache")
@IgnoreApi
public class CacheController {
	Log log = LogFactory.getLog(CacheController.class);
	
	@Autowired
	CacheService service;
	@Autowired
	RsaService rsaService;
	
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 新增用户并刷新缓存，根据id缓存<br>
	 *创建时间: 2018年11月21日
	 *@param person
	 *@return
	 */
	@PostMapping("person")
	public TestBean save(TestBean person){
		return service.save(person);
	}	
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述:更新person,删除name为person的所有缓存（相当于刷新）<br>
	 *创建时间: 2018年11月21日
	 *@param person
	 *@return
	 */
	@PutMapping("person/clear/cache")//不可用
	public void upate(){//不能带参数，否则表示清空指定的参数的缓存
		 service.update();
	}	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 根据id查询用户进行缓存<br>
	 *创建时间: 2018年11月21日
	 *@param id
	 *@return
	 */
	@GetMapping("person/{id}")
	public TestBean findTestBeanById(@PathVariable String id){
		return service.findTestBeanById(id);
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 自定义key的有效期<br>
	 *创建时间: 2018年11月21日
	 *@param id
	 *@return
	 */
	@GetMapping("person/time/{id}")
	public TestBean findTestBeanByIdTime(@PathVariable String id){
		return service.findOne(new TestBean(id,"8767890","iechenyb","111111"));
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 对象参数进行自定义key生成<br>
	 *创建时间: 2018年11月21日
	 *@param person
	 *@return
	 */
	@GetMapping("person")
	public TestBean findTestBean(TestBean person){//这里是比较复杂的查询条件，实际中可能是HashMap之类存在各种参数的东东
		return service.findTestBeanByFuza(person);
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 测试返回空值是否缓存<br>
	 *创建时间: 2018年11月21日
	 *@param person
	 *@return
	 */
	@GetMapping("person/null/test")
	public TestBean findTestNullBeanCache(TestBean person){//这里是比较复杂的查询条件，实际中可能是HashMap之类存在各种参数的东东
		return service.findTestNullBeanByFuza(person);
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 测试条件是否缓存<br>
	 *创建时间: 2018年11月21日
	 *@param person
	 *@return
	 */
	@GetMapping("person/condition/test")
	public TestBean findTestConditionCache(TestBean person){//这里是比较复杂的查询条件，实际中可能是HashMap之类存在各种参数的东东
		return service.findTestConditionCache(person);
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 测试unless是否缓存<br>
	 *创建时间: 2018年11月21日
	 *@param person
	 *@return
	 */
	@GetMapping("person/unless/test")
	public TestBean findTestUnlessCache(TestBean person){//这里是比较复杂的查询条件，实际中可能是HashMap之类存在各种参数的东东
		return service.findTestUnlessCache(person);
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 测试抛出异常时，是否进行缓存<br>
	 *创建时间: 2018年11月21日
	 *@param person
	 *@return
	 * @throws Exception 
	 */
	@GetMapping("person/exception/test")
	public TestBean findTestExceptionCache(TestBean person) throws Exception{//这里是比较复杂的查询条件，实际中可能是HashMap之类存在各种参数的东东
		return service.findTestExceptionCache(person);
	}
	
	@DeleteMapping("person")
	public String clearCacheByKey(String name){//这里是比较复杂的查询条件，实际中可能是HashMap之类存在各种参数的东东
		 service.clearCache(name);
		 return "清除成功";
	}
	
	@Autowired
	CacheManager cacheManager;
	
	@PostMapping("rsa")
	public  String getPublicKey(){
		return rsaService.getPublicKey();
	}
}
