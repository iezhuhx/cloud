package com.cyb.web.config;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月21日
 */
@ControllerAdvice
@Component
public class MyDefaultHandlerExceptionResolver extends DefaultHandlerExceptionResolver {
 
	Log log = LogFactory.getLog(MyDefaultHandlerExceptionResolver.class);
	
	@ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String url = request.getServletPath();
        System.out.println("==========="+url);
        try {
            if (ex instanceof HttpRequestMethodNotSupportedException) {
                return handleHttpRequestMethodNotSupported((HttpRequestMethodNotSupportedException) ex, request,
                        response, handler);
            }
            else if (ex instanceof HttpMediaTypeNotSupportedException) {
                return handleHttpMediaTypeNotSupported((HttpMediaTypeNotSupportedException) ex, request, response,
                        handler);
            }
            else if (ex instanceof HttpMediaTypeNotAcceptableException) {
                return handleHttpMediaTypeNotAcceptable((HttpMediaTypeNotAcceptableException) ex, request, response,
                        handler);
            }
            else if (ex instanceof MissingPathVariableException) {
                return handleMissingPathVariable((MissingPathVariableException) ex, request,
                        response, handler);
            }
            else if (ex instanceof MissingServletRequestParameterException) {
                return handleMissingServletRequestParameter((MissingServletRequestParameterException) ex, request,
                        response, handler);
            }
            else if (ex instanceof ServletRequestBindingException) {
                return handleServletRequestBindingException((ServletRequestBindingException) ex, request, response,
                        handler);
            }
            else if (ex instanceof ConversionNotSupportedException) {
                return handleConversionNotSupported((ConversionNotSupportedException) ex, request, response, handler);
            }
            else if (ex instanceof TypeMismatchException) {
                return handleTypeMismatch((TypeMismatchException) ex, request, response, handler);
            }
            else if (ex instanceof HttpMessageNotReadableException) {
                return handleHttpMessageNotReadable((HttpMessageNotReadableException) ex, request, response, handler);
            }
            else if (ex instanceof HttpMessageNotWritableException) {
                return handleHttpMessageNotWritable((HttpMessageNotWritableException) ex, request, response, handler);
            }
            else if (ex instanceof MethodArgumentNotValidException) {
                return handleMethodArgumentNotValidException((MethodArgumentNotValidException) ex, request, response,
                        handler);
            }
            else if (ex instanceof MissingServletRequestPartException) {
                return handleMissingServletRequestPartException((MissingServletRequestPartException) ex, request,
                        response, handler);
            }
            else if (ex instanceof BindException) {
                return handleBindException((BindException) ex, request, response, handler);
            }
            else if (ex instanceof NoHandlerFoundException) {
                return handleNoHandlerFoundException((NoHandlerFoundException) ex, request, response, handler);
            }
            else if (ex instanceof AsyncRequestTimeoutException) {
                return handleAsyncRequestTimeoutException(
                        (AsyncRequestTimeoutException) ex, request, response, handler);
            }
        }
        catch (Exception handlerException) {
            if (logger.isWarnEnabled()) {
                logger.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", handlerException);
            }
        }

        //这里调用父类的异常处理方法，实现其他不需要的异常交给SpringMVC处理
        return super.doResolveException(request, response, handler, ex);

    }
    /*
    *  400-Bad Request
    */
   /* @ResponseStatus(HttpStatus.BAD_REQUEST)
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
    }*/
 
    /**
     * 404-NOT_FOUND
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handlerNotFoundException(
    		NoHandlerFoundException e)
    {
        log.error("请求的资源不可用",e);
        return  "请求的资源不可用";
    }
 
    /*
    * 405 - method Not allowed
    * HttpRequestMethodNotSupportedException 是ServletException 的子类，需要Servlet API 支持
    *
    */
    /*@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        log.error("不合法的请求方法...",e);
        return new Response().failure("不合法的请求方法");
    }*/
 
    /**
     * 415-Unsupported Media Type.HttpMediaTypeNotSupportedException是ServletException的子类，需要Serlet API支持
     */
   /* @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
    public Response handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error("内容类型不支持...", e);
        return new Response().failure("内容类型不支持");
    }*/
 
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
    /*@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        log.error("内部服务错误...", e);
        return new Response().failure("内部服务错误");
    }*/
}
