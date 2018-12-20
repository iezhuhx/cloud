package com.kiiik.web.system.controller;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
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
import com.kiiik.pub.controller.BaseController;
import com.kiiik.pub.exception.UserSessionTimeoutException;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.web.system.po.Menu;
import com.kiiik.web.system.service.impl.MenuServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月18日
 */
@RestController
@RequestMapping("menu")
@Api(value = "菜单管理模块", description = "菜单基本信息操作API", tags = "MenuApi")
public class MenuController extends BaseController{
	Log log = LogFactory.getLog(MenuController.class);
	
	@Autowired
	GenericService genericService;
	
	
    /**
     * 
     *作者 : iechenyb<br>
     *<br>
     *创建时间: 2017年7月15日
     *@param user
     *@return
     */
	@ApiOperation("菜单信息查询")
	@GetMapping("list")
	public R<PageData<Menu>> listMenus(Menu menu,KiiikPage page){
		if(page.needAll()){//当分页参数不传时传回所有记录
		    return new R<PageData<Menu>>(new PageData<Menu>(genericService.queryDBEntityListLike(menu))).success();
	   }else{
			Page<Menu> datas = genericService.queryDBEntityListLike(menu, page);
			return new R<PageData<Menu>>(new PageData<Menu>(datas,page)).success();
	   }
	}
	

	@PostMapping("add")
	@ApiOperation("菜单信息新增")
	public R<String> addMenu(@RequestBody @Validated Menu menu){
		return menuService.saveMenu(menu); 
	}
	
	@DeleteMapping("deleteByIds")
	@ApiOperation(value="根据主键删除菜单")
	public R<String> delMenu(@RequestParam("ids") List<Integer> ids) throws Exception{
		return menuService.delMenu(ids);
	}

	@PutMapping("update")
	@KiiikCachesParams(caches={
			@KiiikCachesParam(clazz=MenuServiceImpl.class,cacheName=RedisKeyContants.RoleMenus)
	})
	@ApiOperation(value="更新菜单信息")
	public R<String> updMenu(@RequestBody Menu menu){
		return menuService.updMenu(menu);
	}
	
	
	@Autowired
	MenuServiceImpl menuService;
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取系统菜单<br>
	 *创建时间: 2017年7月15日
	 *@param roleId
	 */
	@GetMapping("getSystemMenuTree")
	@ApiOperation(value="系统菜单树")
	public R<Menu> getSystemMenuTree(){
		return new R<Menu>(menuService.getSystemMenuTree()).success();
	}
	
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取用户的系统菜单<br>
	 *创建时间: 2017年7月15日
	 *@param roleId
	 * @throws Exception 
	 */
	@GetMapping("getUserSystemMenuTree")
	@ApiOperation(value="用户系统菜单")
	public R<Menu> getUserSystemMenuTree(Integer userId) throws UserSessionTimeoutException{
		if(StringUtils.isEmpty(userId)){
			userId = getSystemUser().getId();
		}
		return new R<Menu>(menuService.getUserSystemMenuTree(userId)).success();
	}
	
}

