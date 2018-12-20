package com.kiiik.web.system.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.kiiik.pub.contant.Contants_Test;
import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.pub.exception.UserStatusException;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.web.employee.entity.EmployeeEntity;
import com.kiiik.web.system.po.User;
import com.kiiik.web.system.vo.SystemUser;
import com.kiiik.web.system.vo.UserRoleVo;

@Component
public class SystemUserDetailsService implements UserDetailsService {
    
    @Autowired
    private  GenericService genericService;
    
    @Autowired
    UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String empno) throws UsernameNotFoundException,UserStatusException {
    	User user = new User();
    	user.setEmpNo(empno);
    	String showName=KiiikContants.BLANK;
    	user = genericService.queryDBEntitySingle(user);
    	if(user==null){
    		throw new UsernameNotFoundException(KiiikContants.USERNAMEPASSWORDEXCEPTION);
    	}
    
    	//状态未启永，抛出异常
    	if(user.getIsEffect().intValue()!=KiiikContants.USER_ISEFFECT_OK){
    		throw new UserStatusException(KiiikContants.USER_ACCOUNT_BLOCK_UP);
    	}
    	
    	List<UserRoleVo> roles = userService.getUserRoles(user.getId());
    	SystemUser sysUser =  new SystemUser(
				user.getEmpNo()
				,user.getPassword()
				,user.getIsEffect()==1?true:false,
				true,
				true,
				true,
				getAuthoritiesById(roles,empno,KiiikContants.DEFAULT_PASSWORD.equals(user.getPassword())));
    	sysUser.setId(user.getId());
    	EmployeeEntity emp = new EmployeeEntity();
    	emp.setLoginid(empno);
    	emp = genericService.queryDBEntitySingle(emp);
    	if(emp!=null){
    		showName = emp.getLastname();
    	}
    	sysUser.setShowUserName(showName);
    	if(StringUtils.isEmpty(user.getLastLoginTime())){
    		sysUser.setLastLonginTime(0l);
    	}else{
    		sysUser.setLastLonginTime(user.getLastLoginTime());
    	}
    	if(StringUtils.isEmpty(user.getLoginSum())){
    		sysUser.setLoginCount(0);
    	}else{
    		sysUser.setLoginCount(user.getLoginSum());
    	}
		return sysUser;
    }
    
    public Collection<? extends GrantedAuthority> getAuthoritiesById(List<UserRoleVo> roles,String empno,boolean defaultPassword) {
    	List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
    	if(defaultPassword){//如果使用了默认的密码，则仅仅付一个角色，手动维护
    		list.add(new SimpleGrantedAuthority(KiiikContants.DEFAULT_PASSWORD_MODIFY_ROLE));
    	}else{
			if(!CollectionUtils.isEmpty(roles)){
				for(UserRoleVo role:roles){
					list.add(new SimpleGrantedAuthority(role.getRoleName()));
				}
			}
    	}
    	list.add(Contants_Test.testRoles(empno));
		return list;
	}
    
}