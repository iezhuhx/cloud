package com.cyb.utils.response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.HandlerMethod;

import com.alibaba.fastjson.JSON;
import com.cyb.contant.Contants;
import com.cyb.utils.exception.UserSessionTimeoutException;
import com.cyb.utils.file.FileUtils;
import com.cyb.utils.returnBean.RThis;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月15日
 */
public class ResponseUtils {
	static Log log = LogFactory.getLog(ResponseUtils.class);
	public static void writeResult(HttpServletResponse response, RThis<String> result) {
        response.setCharacterEncoding(Contants.UTF8);
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return ;
    }
	
	public static void downFile(HttpServletResponse response,File file) throws FileNotFoundException, IOException, Exception{
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename="+file.getName());
		response.setCharacterEncoding("utf-8");
		FileUtils.fileUpload(new FileInputStream(file), response.getOutputStream());
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
	public static RThis<String> HandlerMethodExceptionDispatcher(
			HttpServletRequest request, 
			HttpServletResponse response,
			Object handler,Exception e){
		RThis<String> result = new RThis<String>(Contants.BLANK).fail();
		if (e instanceof AccessDeniedException) {
			result.refuse("无权限访问！");
		} else if (e instanceof UserSessionTimeoutException) {
			result.sessionTimeOut("尚未登录或者会话过期！");
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
		}else{ 
        	HandlerMethod handlerMethod = (HandlerMethod) handler;
        	result.fail(e.getMessage());
        	String message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                    request.getRequestURI(),
                    handlerMethod.getBean().getClass().getName(),
                    handlerMethod.getMethod().getName(),
                    e.getMessage());
            log.error(message, e);
        }
		return result;
	}
	
}
