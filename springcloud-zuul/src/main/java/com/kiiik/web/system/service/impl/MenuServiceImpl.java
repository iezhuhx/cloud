package com.kiiik.web.system.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kiiik.pub.bean.R;
import com.kiiik.pub.contant.Contants_Test;
import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.pub.contant.RedisKeyContants;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.service.BaseService;
import com.kiiik.web.system.mapper.MenuMapper;
import com.kiiik.web.system.mapper.UserMapper;
import com.kiiik.web.system.po.Menu;
import com.kiiik.web.system.po.RoleMenu;
import com.kiiik.web.system.utils.TreeUtils;
import com.kiiik.web.system.vo.RoleMenuVo;
@Service
public class MenuServiceImpl extends BaseService {
	Log log = LogFactory.getLog(MenuServiceImpl.class);
	@Autowired
	UserMapper userMapper;
	@Autowired
	MenuMapper menuMapper;
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取系统菜单<br>
	 *创建时间: 2017年7月15日hj12
	 *@param roleId
	 */
	public Menu getSystemMenuTree(){
		List<Menu> menusAll = this.genericDao.queryDBEntityList(new Menu(),"ordor asc");
		Menu root = TreeUtils.getRoot();
		TreeUtils.madeTree(menusAll,root);
		return root;
	}
	
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取系统菜单<br>
	 *创建时间: 2017年7月15日hj12
	 *@param roleId
	 */
	public Menu getUserSystemMenuTree(Integer userId){
		List<Menu> menusAll = userMapper.getUserMenus(userId);
		Menu root = TreeUtils.getRoot();
		TreeUtils.madeTree(menusAll,root);
		return root;
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取角色指派的菜单<br>
	 *创建时间: 2017年7月15日hj12
	 *@param roleId
	 * @return 
	 */
	public List<RoleMenu> getAssignedRoleMenu(Integer roleId){
		RoleMenu rm = new RoleMenu();
		rm.setRoleId(roleId);
		return this.genericDao.queryDBEntityList(rm);
	}
	//缺省的权限信息
	private static String[] defaultsPrivilegs = new String[]{
				"/user/updatePassword:"+KiiikContants.DEFAULT_PASSWORD_MODIFY_ROLE
				};
	/**
	 * 当角色菜单信息发生变化时，通知缓存更新
	 * */
	@Cacheable(value =RedisKeyContants.RoleMenus,keyGenerator="cacheKeyGenerator")
	public List<RoleMenuVo> systemRoleMenus(){
			System.err.println("查询系统的角色菜单关系！");
			List<RoleMenuVo> auths = menuMapper.systemRoleMenus();
			auths.addAll(Contants_Test.testRoleMenu());
			RoleMenuVo vo = new RoleMenuVo();
			try{
				for(int i=0;i<defaultsPrivilegs.length;i++){//默认的密码修改权限
					String url = defaultsPrivilegs[i].split(":")[0];
					String role = defaultsPrivilegs[i].split(":")[1];
					vo.setRoleName(role);
					vo.setUrl(url);
				}
			}catch(Exception e){
				e.printStackTrace();//添加默认权限错误
			}
			return auths;
	}
	
	public int saveRMBatch(Integer[] menuIds,Integer roleId){
		RoleMenu rm = new RoleMenu();
		rm.setRoleId(roleId);
		genericDao.deleteDBEntity(rm);//清除历史记录
		List<Object> entitys= new ArrayList<Object>();
		RoleMenu rm_tmp ;
		for(int i=0;i<menuIds.length;i++){
			rm_tmp = new RoleMenu();
			rm_tmp.setMenuId(menuIds[i]);
			rm_tmp.setRoleId(roleId);
			entitys.add(rm_tmp);
			rm_tmp = null;
		}
		if(entitys.size()>0){
			return this.genericDao.insertDBEntityBatch(entitys);
		}else{
			return 0;
		}
	}
	
	public R<String> saveMenu(Menu menu){
		Menu menu_tmp = null;
		if(menu.getParentId()==null){
			menu.setParentId(KiiikContants.ROOTID);
		}
		if(KiiikContants.ROOTID!=menu.getParentId()){//不是根节点id值
			//从数据库查询id值对应父菜单记录
			menu_tmp = new Menu();
			menu_tmp.setId(menu.getParentId());
			menu_tmp = genericDao.queryDBEntitySingle(menu_tmp);
			if(menu_tmp==null){
				return new R<String>().fail("父菜单信息不存在！");
			}
		}
		menu_tmp = genericDao.queryDBEntitySingleComplex(Menu.class, 
				new ComplexCondition()
				.and()
				.col("url")
				.eq(menu.getUrl()));
		if(menu_tmp==null){
			genericDao.insertDBEntity(menu);
			return new R<String>().success("菜单插入成功!");
		}else{
			return new R<String>().fail("菜单地址已经存在！");
		}
	}
	
	public R<String> updMenu(Menu menu){
		Menu menu_tmp = null;
		menu_tmp = genericDao.queryDBEntitySingleComplex(Menu.class, 
				new ComplexCondition().col("id").notIn(menu.getId()).and().col("url").eq(menu.getUrl()));
		if(menu_tmp!=null){
			return new R<String>().fail("角色名已经存在！");
		}else{
			int count = genericDao.updateDBEntityByKey(menu);
			return new R<String>().success("更新成功！更新记录数"+count);
		}
	}


	public R<String> delMenu(List<Integer> ids) {
		// 查询子菜单或者目录
		if (!CollectionUtils.isEmpty(genericDao.queryDBEntityListComplex(Menu.class,
				new ComplexCondition()
				.and()
				.col("parentId")
				.inList(ids)))) {
			return new R<String>().fail("存在子菜单信息，不能删除！");
		}

		if (!CollectionUtils.isEmpty(genericDao.queryDBEntityListComplex(RoleMenu.class,
				new ComplexCondition()
				.and()
				.col("menuId")
				.inList(ids)))) {
			return new R<String>().fail("菜单正在被角色使用，不能删除！");
		}
		int count = genericDao.deleteDBEntityByKeyBatchs(new Menu(), ids);
		if (count > 0) {
			return new R<String>().success("删除记录成功！");
		} else {
			return new R<String>().fail("删除记录失败！");
		}
	}
}
