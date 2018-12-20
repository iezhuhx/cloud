package com.kiiik.pub.advice;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kiiik.pub.bean.R;

import net.sf.json.JSONObject;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月15日
 *  定制白板提示
 *  Whitelabel Error Page
	This application has no explicit mapping for /error, so you are seeing this as a fallback.
	Fri Nov 23 14:00:52 CST 2018
	There was an unexpected error (type=Not Found, status=404).
	No message available
 */
@Controller
public class ErrorControllerAdvice implements ErrorController {
	Log log = LogFactory.getLog(ErrorControllerAdvice.class);
	 private final static String ERROR_PATH = "/error";
	 
	    /**
	     * Supports the HTML Error View
	     *
	     * @param request
	     * @return
	     */
	    @RequestMapping(value = ERROR_PATH, produces = "text/html")
	    @ResponseBody
	    public String errorHtml(HttpServletRequest request,HttpServletResponse response,Exception e) {
	    	 System.out.println("普通请求");
	    	 if(!isProduction) {
	             return JSONObject.fromObject(buildBody(request,response,true)).toString();
	         }else{
	             return JSONObject.fromObject(buildBody(request,response,false)).toString();
	         }
	    	 
	    }
	 
	    boolean isProduction = false;
	    /**
	     * Supports other formats like JSON, XML
	     *
	     * @param request
	     * @return
	     */
	    @RequestMapping(value = ERROR_PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
	    @ResponseBody
	    public Object error(HttpServletRequest request ,HttpServletResponse response,Exception e) {
	    	 log.info(e.toString());
	    	 if(!isProduction) {
	             return buildBody(request,response,true);
	         }else{
	             return buildBody(request,response,false);
	         }
	    }
	 
	    /**
	     * Returns the path of the error page.
	     *
	     * @return the error path
	     */
	    public String getErrorPath() {
	        return ERROR_PATH;
	    }
	    @Autowired
	    private ErrorAttributes errorAttributes;

	    @SuppressWarnings({"unused" })
		private R<String> buildBody(HttpServletRequest request,HttpServletResponse response,Boolean includeStackTrace){
	        Map<String,Object> errorAttributes = getErrorAttributes(request, includeStackTrace);
	        Integer status=(Integer)errorAttributes.get("status");
	        String path=(String)errorAttributes.get("path");
	        String messageFound=(String)errorAttributes.get("message");
	        if(response.getStatus()==500){
	        	messageFound = "服务器故障！";
	        }else if(response.getStatus()==404){
	        	messageFound="请求尚未开发！";
	        }else if(response.getStatus()==403){
	        	messageFound="请求被拒绝！";
	        }
	        String message="";
	        String trace ="";
	        if(!StringUtils.isEmpty(path)){
	            message=String.format("["+response.getStatus()+"]Requested path %s with result %s",path,messageFound);
	        }
	        if(includeStackTrace) {
	             trace = (String) errorAttributes.get("trace");
	             if(!StringUtils.isEmpty(trace)) {
	                 message += String.format(" and trace %s", trace);
	             }
	        }
	        return new R<String>().refuse(message);
	    }
	    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
	        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
	        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
	    }
}
