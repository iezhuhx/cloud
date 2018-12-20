package com.kiiik.pub.controller;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kiiik.pub.bean.SessionUser;
import com.kiiik.pub.exception.UserSessionTimeoutException;
import com.kiiik.web.system.vo.SystemUser;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月18日
 */
public class BaseController {
	Configuration config = null;
	Log log = LogFactory.getLog(BaseController.class);
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取zuul转发传递的权限信息<br>
	 *创建时间: 2018年10月19日
	 *@return
	 */
	protected HttpServletRequest getCurrentRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	protected Authentication getCurrentAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	protected HttpServletRequest getHttpServletRequest() {
		//获取到当前线程绑定的请求对象
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	protected HttpSession getHttpSession() {
		//获取到当前线程绑定的请求对象
		return getHttpServletRequest().getSession();
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 只获取用户名 使用本方法<br>
	 *创建时间: 2017年7月15日hj12
	 *@return
	 * @throws Exception 
	 */
	protected SystemUser getSystemUser () throws UserSessionTimeoutException {
		try{
			UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken) getCurrentRequest().getUserPrincipal();
			return (SystemUser) principal.getPrincipal();
		}catch(Exception e){
			throw new UserSessionTimeoutException("尚未登录，请先登录！");
		}
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取用户名以外的信息比如角色，则需要用此方法<br>
	 *创建时间: 2017年7月15日hj12
	 *@return
	 * @throws Exception 
	 */
	protected SessionUser getSessionUser() throws Exception {
		SessionUser user = new SessionUser();
		SystemUser sysUser = getSystemUser();
		user.setUserId(sysUser.getId());
		user.setEmpNo(sysUser.getUsername());//获取职工号
		user.setUserName(sysUser.getShowUserName());//用户显示名称
		user.setRoles(collection2List(sysUser.getAuthorities()));
		return user;
	}
	
	private List<String> collection2List(Collection<GrantedAuthority> authorities) {
		List<String> roles = new ArrayList<>(authorities.size());
		for(GrantedAuthority gat:authorities){
			roles.add(gat.getAuthority());
		}
		return roles;
	}

	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 读取配置文件<br>
	 *创建时间: 2017年7月15日hj12
	 *@return
	 */
    public  Configuration getConfig(){
        if(config == null){
            synchronized (BaseController.class) {
                if(config == null)
					try {
						config = new PropertiesConfiguration("application.properties");
					} catch (ConfigurationException e) {
						e.printStackTrace();
						return null;
					}
            }
        }
        return config;
    }
}
