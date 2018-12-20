package com.kiiik.web.system.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.cyb.computer.ComputerUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kiiik.pub.bean.KiiikPage;
import com.kiiik.pub.bean.R;
import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.pub.contant.RedisKeyContants;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.service.BaseService;
import com.kiiik.utils.ConcurrentDateUtil;
import com.kiiik.web.employee.entity.EmployeeEntity;
import com.kiiik.web.system.mapper.UserMapper;
import com.kiiik.web.system.po.Menu;
import com.kiiik.web.system.po.User;
import com.kiiik.web.system.po.UserRole;
import com.kiiik.web.system.utils.TreeUtils;
import com.kiiik.web.system.vo.SystemUser;
import com.kiiik.web.system.vo.UserCompanyInfor;
import com.kiiik.web.system.vo.UserRoleVo;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
@Service
public class UserServiceImpl extends BaseService {
	Log log = LogFactory.getLog(UserServiceImpl.class);
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 保存用户角色信息<br>
	 *创建时间: 2017年7月15日hj12
	 *@param roleIds
	 *@param userId
	 *@return
	 */
	public int saveUserRoles(Integer[] roleIds,Integer userId){
		
		UserRole ur=null;
		ur = new UserRole();
		ur.setUserId(userId);
		this.genericDao.deleteDBEntity(ur);//删除之前的角色信息
		ur = null;
		
		int total = roleIds.length;
		List<Object> urs = new ArrayList<Object>(total);
		for(int i=0;i<total;i++){
			ur = new UserRole();
			ur.setUserId(userId);
			ur.setRoleId(roleIds[i]);
			urs.add(ur);
		}
		return this.genericDao.insertDBEntityBatch(urs);
	}
	
	@Autowired
	UserMapper userMapper;
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2018年11月8日
	 *@param userId
	 *@return
	 */
	public List<UserRoleVo> getUserRoles(Integer userId){
		return userMapper.getUserRoles(userId);
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2018年11月8日
	 *@param userId
	 *@return
	 */
	public Menu getUserMenus(Integer userId){
		List<Menu> menusAll = userMapper.getUserMenus(userId);
		Menu root = TreeUtils.getRoot();
		TreeUtils.madeTree(menusAll,root);
		return root;
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 记录用户登录状态<br>
	 *创建时间: 2018年11月8日10:13:46
	 *@param user
	 */
	public void recordLoginStatus(SystemUser user) {
		try {
			// 1 查询当前用户
			User newUserInfo = new User();
			newUserInfo.setId(user.getId());
			User oldUserInfor = this.genericDao.queryDBEntitySingle(newUserInfo);//查询的时候时间是时间戳
			if (oldUserInfor != null) {
				newUserInfo.setLastLoginIp(oldUserInfor.getLoginIp());
				newUserInfo.setLoginIp(ComputerUtil.getRealIP());
				if(StringUtils.isEmpty(oldUserInfor.getLoginSum())){
					oldUserInfor.setLoginSum(0);//第一次登录 进行初始化
				}
				newUserInfo.setLoginSum( oldUserInfor.getLoginSum()+ 1);
				newUserInfo.setLastLoginTime(ConcurrentDateUtil.timeStamp2Date(oldUserInfor.getLoginTime()));
				newUserInfo.setLoginTime(ConcurrentDateUtil.date2long14());
				this.genericDao.updateDBEntityByKey(newUserInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Cacheable(value=RedisKeyContants.EMPNOUSERNAMEMAP,keyGenerator=RedisKeyContants.KEYGENERATOR)
	public Map<String,String> getUserNameEmpNoMap() {
		System.err.println("查询数据库");
		List<EmployeeEntity> data = this.genericDao.queryDBEntityList(new EmployeeEntity());//查询所有
		Map<String,String> map = new HashMap<String,String>();
		if(CollectionUtils.isEmpty(data)){
			return null;//返回空不缓存
		}else{
			for(EmployeeEntity e:data){
				map.put(e.getLoginid(), e.getLastname());
			}
			return map;
		}
	}
	
	@Cacheable(value={RedisKeyContants.USERINFO},keyGenerator=RedisKeyContants.KEYGENERATOR)
	public Map<String,String> getUserInforMap() {
		Map<String,String> data = null;
		List<User> users = this.genericDao.queryDBEntityList(new User());
		if(!CollectionUtils.isEmpty(users)){
			data = new HashMap<>();
			for(User u:users){
				u.setPassword(KiiikContants.BLANK);
				if(u.getLastLoginTime()!=null){
					data.put(u.getEmpNo(), u.getLastLoginTime().toString());
				}else{
					data.put(u.getEmpNo(), KiiikContants.ZERO.toString());
				}
				
			}
			return data;
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 分页查询用户信息<br>
	 *创建时间: 2018年11月23日
	 *@param user
	 *@param page
	 *@return
	 */
	public Page<User> getUsers(User user,KiiikPage page){
		if(page.needAll()){
			PageHelper.startPage(1, 0);
		}else{
			PageHelper.startPage(page.getCurPage(), page.getPageSize());
		}
		Page<User> users = userMapper.getUsers(user);
		return users;
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取职工公司相关的信息<br>
	 *创建时间: 2018年11月27日
	 *@param empNo
	 *@return
	 */
	public UserCompanyInfor getUserCompanyInfor(String empNo){
		UserCompanyInfor infor = userMapper.getUserCompanyInfor(empNo);
		if(infor == null){
			return new UserCompanyInfor();
		}else{
			return infor;
		}
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 删除用户，将角色进行删除<br>
	 *创建时间: 2018年11月27日
	 *@param ids
	 *@return
	 */
	public  R<String> deleteUsers(List<Integer> ids){
		List<UserRole> urs = genericDao.queryDBEntityListComplex(
				UserRole.class,
				new ComplexCondition().col("userId").inList(ids));
		List<Integer> urIds = new ArrayList<Integer>();
		for(UserRole ur_ :urs){
			urIds.add(ur_.getId());
		}
		genericDao.deleteDBEntityByKeyBatchs(new UserRole(),urIds);//先删除用户角色信息
		int count = genericDao.deleteDBEntityByKeyBatchs(new User(),ids);
		if(count==0){
			return new R<String>().fail("删除失败！");
		}else{
			return new R<String>().success("删除成功！");
		}
	}
	
}
