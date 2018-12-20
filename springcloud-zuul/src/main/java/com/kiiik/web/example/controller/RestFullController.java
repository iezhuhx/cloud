package com.kiiik.web.example.controller;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.pub.ann.IgnoreApi;
import com.kiiik.pub.bean.KiiikPage;
import com.kiiik.pub.bean.R;
import com.kiiik.web.example.bean.TestBean;

import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月13日
 */
@RestController
@RequestMapping("rest")
@IgnoreApi
public class RestFullController {
	Log log = LogFactory.getLog(RestFullController.class);
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取信息<br>
	 *创建时间: 2018年11月20日
	 *@param id
	 *@return
	 */
	@GetMapping("entity/{id}")
	@ApiOperation("根据主键查询用户信息")
	public R<String> get(@PathVariable String id ){
		log.info("get");
		return new R<String>(id).success();
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 分页查询<br>
	 *创建时间: 2018年11月20日
	 *@param testBean
	 *@param page
	 *@return
	 */
	@GetMapping("entitys")
	@ApiOperation("批量分页查询")
	public R<TestBean> get(TestBean testBean,KiiikPage page ){
		log.info("get");
		return new R<TestBean>(testBean).success();
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 增加信息<br>
	 *创建时间: 2018年11月20日
	 *@param object
	 *@return
	 */
	@ApiOperation("新增信息")
	@PostMapping("entity")
	public R<TestBean> add(@RequestBody TestBean testBean){
		log.info("add");
		return new R<TestBean>(testBean).success();
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 更新信息<br>
	 *创建时间: 2018年11月20日
	 *@param object
	 *@return
	 */
	@PutMapping("entity")
	@ApiOperation("更新信息")
	public  R<TestBean> update(@RequestBody TestBean testBean){
		log.info("update");
		return new R<TestBean>(testBean).success();
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 删除信息<br>
	 *创建时间: 2018年11月20日
	 *@param id
	 *@return
	 */
	@DeleteMapping("entity/{id}")
	@ApiOperation("删除信息，支持批量")
	public R<String> delete(@PathVariable String[] id){
		log.info("delete");
		List<String> ids = new ArrayList<String>();
		for(int i=0;i<id.length;i++){
			ids.add(id[i]);
		}
		return new R<String>(ids.toString()).success();
	}
	
}
