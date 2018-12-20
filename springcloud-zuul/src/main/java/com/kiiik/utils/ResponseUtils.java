package com.kiiik.utils;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.HandlerMethod;

import com.kiiik.pub.bean.R;
import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.pub.exception.KiiikException;
import com.kiiik.pub.exception.UserSessionTimeoutException;
import com.kiiik.web.property.KiiikProperties;

import net.sf.json.JSONObject;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月15日
 */
public class ResponseUtils {
	static Log log = LogFactory.getLog(ResponseUtils.class);
	public static void writeResult(HttpServletResponse response, R<String> result) {
        response.setCharacterEncoding(KiiikContants.UTF8);
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSONObject.fromObject(result).toString());
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return ;
    }
	
	public static String getMessage(Exception e,KiiikProperties kiiik){
		if(KiiikContants.PROD.equals(KiiikContants.PROD)){
			return e.toString();
		}else{
			return e.getMessage();
		}
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 方法异常处理<br>
	 *创建时间: 2018年11月23日
	 *@param response
	 *@param e
	 *@param kiiik
	 */
	public static void HandlerMethodExceptionDispatcher(HttpServletRequest request, HttpServletResponse response,Object handler,Exception e,KiiikProperties kiiik){
		R<String> result = new R<String>();
		result.data(KiiikContants.BLANK);
		if (e instanceof AccessDeniedException) {
			result.refuse("无权限访问！");
			writeResult(response, result);
		} else if (e instanceof UserSessionTimeoutException) {
			result.sessionTimeOut("尚未登录或者会话过期！");
			writeResult(response, result);
		} else if(e instanceof InternalAuthenticationServiceException){
			result.fail(e.getMessage());// 
			writeResult(response, result);
		} else if (e instanceof BadCredentialsException) {
			result.fail("用户名或者密码错误！");// "密码为默认值，请修改!"
			writeResult(response, result);
		} else if (e instanceof KiiikException) {// 业务失败的异常，如“账号或密码错误”
			result.fail(getMessage(e, kiiik));
			log.error(e.getMessage());
			writeResult(response, result);
		} else if (e instanceof BindException) {
			BindException be = (BindException) e;
			List<ObjectError> errors = be.getAllErrors();
			StringBuffer sb = new StringBuffer();
			for (ObjectError er : errors) {
				String param = er.getCodes()[0];
				String errMsg = param.substring(param.lastIndexOf(".") + 1, param.length()) + er.getDefaultMessage();
				sb.append(errMsg + " ");
			}
			result.fail(sb.toString());
			writeResult(response, result);
		} else if (e instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException manv = (MethodArgumentNotValidException) e;
			List<ObjectError> errors = manv.getBindingResult().getAllErrors();
			StringBuffer sb = new StringBuffer();
			for (ObjectError er : errors) {
				String param = er.getCodes()[0];
				String errMsg = param.substring(param.lastIndexOf(".") + 1, param.length()) + er.getDefaultMessage();
				sb.append(errMsg + " ");
			}
			result.fail(sb.toString());
			writeResult(response, result);
		}else{ 
        	HandlerMethod handlerMethod = (HandlerMethod) handler;
        	result.fail(getMessage(e,kiiik));
        	String message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                    request.getRequestURI(),
                    handlerMethod.getBean().getClass().getName(),
                    handlerMethod.getMethod().getName(),
                    e.getMessage());
            log.error(message, e);
            ResponseUtils.writeResult(response, result);
        }
	}
	
}
