package com.cyb.utils.controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cyb.app.reptile.ProxyInfor;
import com.cyb.utils.response.ResponseUtils;
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
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日
	 *@return
	 */
	protected HttpServletResponse getCurrentResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取请求方法类型  get put post delete options...<br>
	 *创建时间: 2017年7月15日
	 *@return
	 */
	protected String requestType(){
		return getCurrentRequest().getMethod();
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日
	 *@param filePath
	 *@throws FileNotFoundException
	 *@throws IOException
	 *@throws Exception
	 */
	protected void downloadFile(String filePath) throws FileNotFoundException, IOException, Exception {
		downloadFile(new File(filePath));
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日
	 *@param file
	 *@throws FileNotFoundException
	 *@throws IOException
	 *@throws Exception
	 */
	protected void downloadFile(File file) throws FileNotFoundException, IOException, Exception {
		/*HttpServletResponse response = getCurrentResponse();
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename="+file.getName());
		response.setCharacterEncoding("utf-8");
		FileUtils.fileUpload(new FileInputStream(file), response.getOutputStream());
		*/
		ResponseUtils.downFile(getCurrentResponse(), file);	
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
	
	protected String getCurIp() {
		return getHttpServletRequest().getRemoteHost();
	}
	protected String getRemoteAddr() {
		return getHttpServletRequest().getRemoteAddr();	
	}
	protected int getCurPort() {
		return getHttpServletRequest().getRemotePort();
	}
	
	protected ProxyInfor clientInfor() {
		return new ProxyInfor(getCurIp(),getCurPort());
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
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日
	 *@param name
	 *@return
	 */
	protected Object getParamterFromRequest(String name){
		return getHttpServletRequest().getParameter(name);
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日
	 *@param name
	 *@return
	 */
	protected Object getAttributeFromSession(String name){
		return getHttpSession().getAttribute(name);
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日
	 *@param name
	 */
	protected void removeAttributeFromSession(String name){
		getHttpSession().removeAttribute(name);
	}
	
}
