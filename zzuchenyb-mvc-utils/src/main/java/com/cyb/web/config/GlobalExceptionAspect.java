package com.cyb.web.config;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.cyb.utils.bean.RMeta;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月21日
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionAspect  {
	Log log = LogFactory.getLog(GlobalExceptionAspect.class);
 
    /*
    *  400-Bad Request
    */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RMeta handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        log.error("无法读取JSON...", e);
        return new RMeta().failure("无法读取JSON");
    }
 
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RMeta handleValidationException(MethodArgumentNotValidException e)
    {
    	MethodArgumentNotValidException manv = (MethodArgumentNotValidException) e;
		List<ObjectError> errors = manv.getBindingResult().getAllErrors();
		StringBuffer sb = new StringBuffer();
		for (ObjectError er : errors) {
			sb.append(er.getDefaultMessage()+ " ");
		}
        log.error("参数验证异常...", e);
        return new RMeta().failure("参数验证异常!,"+sb.toString());
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public RMeta handleValidationException(BindException e)
    {
    	BindException be = (BindException) e;
		List<ObjectError> errors = be.getAllErrors();
		StringBuffer sb = new StringBuffer();
		for (ObjectError er : errors) {
			sb.append(er.getDefaultMessage() + " ");
		}
        log.error("参数验证异常...", e);
        return new RMeta().failure("参数验证异常!,"+sb.toString());
    }
 
    /**
     * 404-NOT_FOUND
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public RMeta handlerNotFoundException(NoHandlerFoundException e)
    {
        log.error("请求的资源不可用",e);
        return new RMeta().failure("请求的资源不可用");
    }
 
    /*
    * 405 - method Not allowed
    * HttpRequestMethodNotSupportedException 是ServletException 的子类，需要Servlet API 支持
    *
    */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RMeta handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        log.error("不合法的请求方法...",e);
        return new RMeta().failure("不合法的请求方法");
    }
 
    /**
     * 415-Unsupported Media Type.HttpMediaTypeNotSupportedException是ServletException的子类，需要Serlet API支持
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
    public RMeta handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error("内容类型不支持...", e);
        return new RMeta().failure("内容类型不支持");
    }
 
   /* *//**
     * 500 - Internal Server Error
     *//*
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TokenException.class)
    public Response handleTokenException(Exception e) {
        log.error("令牌无效...", e);
        return new Response().failure("令牌无效");
    }*/
 
    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public RMeta handleException(Exception e) {
        log.error("内部服务错误...", e);
        return new RMeta().failure("内部服务错误");
    }
 
}