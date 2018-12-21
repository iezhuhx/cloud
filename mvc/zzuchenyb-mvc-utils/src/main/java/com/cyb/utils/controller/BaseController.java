package com.cyb.utils.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月18日
 */
public class BaseController {
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
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取当前HttpServletRequest对象<br>
	 *创建时间: 2017年7月15日
	 *@return
	 */
	protected HttpServletRequest getHttpServletRequest() {
		//获取到当前线程绑定的请求对象
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取当前session对象<br>
	 *创建时间: 2017年7月15日
	 *@return
	 */
	protected HttpSession getHttpSession() {
		//获取到当前线程绑定的请求对象
		return getHttpServletRequest().getSession();
	}
	
	protected Object getParamterFromRequest(String name){
		return getHttpServletRequest().getParameter(name);
	}
	
	protected Object getAttributeFromSession(String name){
		return getHttpSession().getAttribute(name);
	}
	
	protected void removeAttributeFromSession(String name){
		getHttpSession().removeAttribute(name);
	}
	
}
