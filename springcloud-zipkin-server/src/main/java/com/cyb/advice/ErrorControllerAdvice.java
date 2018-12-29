package com.cyb.advice;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cyb.web.bean.ResultBean;
import com.cyb.web.contant.ExceptionCode;
import com.cyb.web.utils.ResponseUtils;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月15日
 */
@Controller
public class ErrorControllerAdvice implements ErrorController {
	Log log = LogFactory.getLog(ErrorControllerAdvice.class);
	 private final static String ERROR_PATH = "/error1";
	    /**
	     * Supports the HTML Error View
	     *
	     * @param request
	     * @return
	     */
	    @GetMapping(value = ERROR_PATH, produces = "text/html")
	    @ResponseBody
	    public ModelAndView errorHtml(HttpServletRequest request,HttpServletResponse response,Exception e) {
	    	System.out.println("text/html error!");
			return ResponseUtils.getErrorView(request, response, e);
	    }
	 
	    boolean isProduction = false;
	    /**
	     * Supports other formats like JSON, XML
	     *
	     * @param request
	     * @return
	     */
	    @GetMapping(value = ERROR_PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
	    @ResponseBody
	    public Object error(HttpServletRequest request ,HttpServletResponse response,Exception e) {
	    	System.out.println("json req error!");
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
	  
	   
		@SuppressWarnings("unchecked")
		private ResultBean<String> buildBody(HttpServletRequest request,HttpServletResponse response,Boolean includeStackTrace){
	        String message=ExceptionCode.ERRORCODE.get(response.getStatus());
	        return new ResultBean<String>().fail(message);
	    }
}
