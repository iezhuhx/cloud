package com.kiiik.web.system.controller;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.web.system.po.Role;
import com.kiiik.web.system.po.UserRole;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月18日
 */
@RestController
@RequestMapping("role")
@Api(value = "角色管理模块", description = "角色基本信息操作API", tags = "RoleApi")
public class RoleController {
	Log log = LogFactory.getLog(RoleController.class);
	
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
	@ApiOperation("角色列表信息")
	@GetMapping("list")
	public R<PageData<Role>> listMenus(Role role,KiiikPage page){
		if(page.needAll()){//当分页参数不传时传回所有记录
		    return new R<PageData<Role>>(new PageData<Role>(genericService.queryDBEntityListLike(role))).success();
	   }else{
			Page<Role> datas = genericService.queryDBEntityListLike(role, page);
			return new R<PageData<Role>>(new PageData<Role>(datas,page)).success();
	   }
	}
	

	@PostMapping("add")
	@ApiOperation("新增角色信息")
	public R<String> addRole(@RequestBody Role role){
		Role role_tmp = null;
		role_tmp = genericService.queryDBEntitySingleComplex(Role.class, 
				new ComplexCondition()
				.and()
				.col("rolename")
				.eq(role.getRoleName()));
		if(role_tmp==null){
			genericService.insertDBEntity(role);
			return new R<String>().success("角色插入成功!");
		}
		return new R<String>().fail("角色名称已经存在！");
	}
	
	@DeleteMapping("deleteByIds")
	@ApiOperation("根据主键删除角色信息")
	public R<String> delRole(@RequestParam("ids") List<Integer> ids) throws Exception{
		//查询是否存在当前角色在用
		List<UserRole> data = genericService.queryDBEntityListComplex(UserRole.class, new ComplexCondition().and().col("roleid").inList(ids));
		if(!CollectionUtils.isEmpty(data)){
			return new R<String>().fail("删除的角色正在使用，不能删除！");
		}
		int count = genericService.deleteDBEntityByKeyBatchs(new Role(),ids);
		return new R<String>("删除"+count+"记录！").success();
	}
	
	/*@DeleteMapping("deleteById")
	@ApiOperation("根据主键删除角色信息")
	public ResultBean<String> delRole(Integer id){
		//查询是否存在当前角色在用
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(id);
		List<UserRole> data = genericService.queryDBEntityListComplex(UserRole.class, 
				new ComplexCondition().and()
				.col("roleid").inList(ids));
		if(!CollectionUtils.isEmpty(data)){
			return new ResultBean<String>().fail("删除的角色正在使用，不能删除！");
		}
		Role role = new Role();
		role.setId(id);
		genericService.deleteDBEntityByKey(role);
		return new ResultBean<String>().success("记录删除成功");
	}*/

	@PutMapping("update")
	@ApiOperation("更新角色信息")
	public R<String> updRole(@RequestBody Role role){
		Role role_tmp = null;
		role_tmp = genericService.queryDBEntitySingleComplex(Role.class, 
				new ComplexCondition().col("id").notIn(role.getId()).and().col("rolename").eq(role.getRoleName()));
		if(role_tmp!=null){
			return new R<String>().fail("角色名已经存在！");
		}else{
			int count = genericService.updateDBEntityByKey(role);
			return new R<String>().success("更新成功！更新记录数"+count);
		}
	}
}
