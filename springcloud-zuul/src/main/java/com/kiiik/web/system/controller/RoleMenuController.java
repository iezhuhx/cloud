package com.kiiik.web.system.controller;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.kiiik.web.system.po.RoleMenu;
import com.kiiik.web.system.service.impl.MenuServiceImpl;
import com.kiiik.web.system.vo.RoleMenuVo2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月18日
 */
@RestController
@RequestMapping("rolemenu")
@Api(value = "角色菜单管理模块", description = "角色菜单基本信息操作API", tags = "RoleMenuApi")
public class RoleMenuController {
	Log log = LogFactory.getLog(RoleMenuController.class);
	
	@Autowired
	GenericService genericService;
	
	/**
     * 
     *作者 : iechenyb<br>
     *方法描述: 分页数据查询<br>
     *创建时间: 2018年11月19日
     *@param user
     *@param page
     *@return
     *@throws Exception
     */
	@ApiOperation("菜单信息查询")
	@GetMapping("list")
	public R<PageData<RoleMenu>> listRoleMenus(RoleMenu rmenu,KiiikPage page){
		if(page.needAll()){//当分页参数不传时传回所有记录
		    return new R<PageData<RoleMenu>>(new PageData<RoleMenu>(genericService.queryDBEntityListLike(rmenu))).success();
	   }else{
			Page<RoleMenu> datas = genericService.queryDBEntityListLike(rmenu, page);
			return new R<PageData<RoleMenu>>(new PageData<RoleMenu>(datas,page)).success();
	   }
	}
	
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 单个信息添加<br>
     *创建时间: 2017年7月15日
     *@param rmenu
     *@return
     */
	@PostMapping("add")
	@ApiOperation("角色菜单信息新增")
	@KiiikCachesParams(caches={
		@KiiikCachesParam(clazz=MenuServiceImpl.class,cacheName=RedisKeyContants.RoleMenus)
	})
	public R<String> addRoleMenu(@RequestBody RoleMenu rmenu){
		genericService.deleteDBEntity(rmenu);
		genericService.insertDBEntity(rmenu);
		return new R<String>().success("新增成功！");
	}
	
	@DeleteMapping("deleteByIds")
	@ApiOperation(value="根据主键删除角色菜单")
	@KiiikCachesParams(caches={
			@KiiikCachesParam(clazz=MenuServiceImpl.class,cacheName=RedisKeyContants.RoleMenus)
	})
	public R<String> delRoleMenu(@RequestParam("ids") List<Integer> ids) throws Exception{
		//查询菜单是否被某个角色使用，若使用则不能删除
		int count = genericService.deleteDBEntityByKeyBatchs(new RoleMenu(),ids);
		if(count>0){
			return new R<String>().success("记录删除成功！");
		}else{
			return new R<String>().fail("记录删除失败！");
		}
		
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 只保存叶子节点<br>
	 *创建时间: 2017年7月15日
	 *@param leafIds
	 *@param roleId
	 */
	@PostMapping("saveRoleMenuBatch")
	@ApiOperation("批量保存角色菜单信息")
	@KiiikCachesParams(caches={
			@KiiikCachesParam(clazz=MenuServiceImpl.class,cacheName=RedisKeyContants.RoleMenus)
	})
	public R<String> saveRMBatch(@RequestBody RoleMenuVo2 vo){
		if(StringUtils.isEmpty(vo.getRoleId())){
			return new R<String>().fail("角色信息不能为空！");//菜单为空时，说明删除角色权限
		}
		menuService.saveRMBatch(vo.getMenuIds(), vo.getRoleId());
		return new R<String>().success("角色菜单信息保存成功！");
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取角色的菜单<br>
	 *创建时间: 2017年7月15日
	 *@param roleId
	 */
	@GetMapping("getRoleMenu")
	@ApiOperation("获取当前角色的菜单信息")
	public R<List<RoleMenu>> getAssignedRoleMenu(Integer roleId){
		List<RoleMenu> rm = menuService.getAssignedRoleMenu(roleId);
		return new R<List<RoleMenu>>(rm).success();
	}
	
	@Autowired
	MenuServiceImpl menuService;
}

