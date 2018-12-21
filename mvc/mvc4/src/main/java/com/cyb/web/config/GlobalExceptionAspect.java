package com.cyb.web.config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月21日
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionAspect  {
	Log log = LogFactory.getLog(GlobalHandlerExceptionResolver.class);
 
    /*
    *  400-Bad Request
    */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        log.error("无法读取JSON...", e);
        return new Response().failure("无法读取JSON");
    }
 
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleValidationException(MethodArgumentNotValidException e)
    {
        log.error("参数验证异常...", e);
        return new Response().failure("参数验证异常");
    }
 
    /**
     * 404-NOT_FOUND
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Response handlerNotFoundException(NoHandlerFoundException e)
    {
        log.error("请求的资源不可用",e);
        return new Response().failure("请求的资源不可用");
    }
 
    /*
    * 405 - method Not allowed
    * HttpRequestMethodNotSupportedException 是ServletException 的子类，需要Servlet API 支持
    *
    */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        log.error("不合法的请求方法...",e);
        return new Response().failure("不合法的请求方法");
    }
 
    /**
     * 415-Unsupported Media Type.HttpMediaTypeNotSupportedException是ServletException的子类，需要Serlet API支持
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
    public Response handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error("内容类型不支持...", e);
        return new Response().failure("内容类型不支持");
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
    public Response handleException(Exception e) {
        log.error("内部服务错误...", e);
        return new Response().failure("内部服务错误");
    }
 
}