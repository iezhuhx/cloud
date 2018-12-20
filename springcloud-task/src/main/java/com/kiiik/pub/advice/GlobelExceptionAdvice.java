package com.kiiik.pub.advice;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kiiik.pub.bean.res.ResultBean;
import com.kiiik.pub.exception.VasException;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月15日
 */
@ControllerAdvice
@RestControllerAdvice
@RestController
public class GlobelExceptionAdvice {
	Log log = LogFactory.getLog(GlobelExceptionAdvice.class);
	 /**
	    * 系统异常处理，比如：404,500
	    * @param req
	    * @param resp
	    * @param e
	    * @return
	    * @throws Exception
	    */
	   @ExceptionHandler(value = Exception.class)
	   @ResponseBody
	   public ResultBean<String> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
	       log.error("", e);
	       ResultBean<String> r = new ResultBean<String>();
	       
	       if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
	            r.msg("请求尚未开发！"+e.getMessage());
	       } else {
	            r.msg("服务器故障或者繁忙！"+e.getMessage());
	       }
	       r.fail("");
	       return r;
	   }
	   @ExceptionHandler(value = VasException.class)
	    @ResponseBody
	    public Object baseErrorHandler(HttpServletRequest req, Exception e) throws Exception {
	      System.out.println("---BaseException Handler---Host {} invokes url {} ERROR: {}"+req.getRemoteHost()+ req.getRequestURL()+e.getMessage());
	        return e.getMessage();
	    }
}
