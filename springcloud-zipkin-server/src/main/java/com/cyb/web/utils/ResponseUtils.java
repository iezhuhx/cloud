package com.cyb.web.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.cyb.web.contant.ExceptionCode;

public class ResponseUtils {
	Log log = LogFactory.getLog(ResponseUtils.class);
	public static ModelAndView getErrorView(HttpServletRequest request,HttpServletResponse response,Exception e){
		ModelAndView view = new ModelAndView();
    	view.addObject("path", request.getRequestURI());
    	view.addObject("error",e.getMessage() );//e.getMessage()
    	view.addObject("timestamp",new Date().getTime());
    	view.addObject("status",response.getStatus());
    	view.addObject("message", ExceptionCode.ERRORCODE.get(response.getStatus()));
		view.setViewName("/error1");
		return view;
	}
}
