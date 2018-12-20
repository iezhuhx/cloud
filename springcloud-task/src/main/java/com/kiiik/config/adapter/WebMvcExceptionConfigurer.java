package com.kiiik.config.adapter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.kiiik.pub.bean.res.ResultBean;
import com.kiiik.pub.exception.VasException;
import com.kiiik.utils.ResponseUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月8日
 */
@Configuration
public class WebMvcExceptionConfigurer extends WebMvcConfigurerAdapter {
	Log logger = LogFactory.getLog(WebMvcExceptionConfigurer.class);
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new HandlerExceptionResolver() {
            @Override
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
                logger.error("进入全局异常处理逻辑......"+e.getMessage());
            	ResultBean<String> result = new ResultBean<String>();
            	result.fail();
            	result.data("error");
                if (handler instanceof HandlerMethod) {
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
                    if (e instanceof VasException) {//业务失败的异常，如“账号或密码错误”
                        result.msg(e .getMessage());
                        logger.error(e .getMessage());
                    } else if(e instanceof BindException){
                     	BindException be = (BindException)e;
                     	List<ObjectError> errors  = be.getAllErrors();
                     	StringBuffer sb = new StringBuffer();
                     	for(ObjectError er:errors){
                     		String param = er.getCodes()[0];
                     		String errMsg = param.substring(param.lastIndexOf(".")+1, param.length())+er.getDefaultMessage();
                     		sb.append(errMsg+" ");
                     	}
                     	result.setEs(sb.toString());
                    }else if(e instanceof MethodArgumentNotValidException){
                    	MethodArgumentNotValidException manv = (MethodArgumentNotValidException)e;
                    	List<ObjectError> errors  = manv.getBindingResult().getAllErrors();
                     	StringBuffer sb = new StringBuffer();
                     	for(ObjectError er:errors){
                     		String param = er.getCodes()[0];
                     		String errMsg = param.substring(param.lastIndexOf(".")+1, param.length())+er.getDefaultMessage();
                     		sb.append(errMsg+" ");
                     	}
                     	result.setEs(sb.toString());
                    }                    
                    else{ 
	                    result.msg("接口信息:[" + request.getRequestURI() + "] 错误信息：["+e.getMessage()+"]");
	                    String message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
	                            request.getRequestURI(),
	                            handlerMethod.getBean().getClass().getName(),
	                            handlerMethod.getMethod().getName(),
	                            e.getMessage());
	                    logger.error(message, e);
                    }
                } else {
                    if (e instanceof NoHandlerFoundException) {
                        result.msg("接口 [" + request.getRequestURI() + "] 不存在");
                    } else {
                        result.fail("system error");
                        result.msg(e.getMessage());
                        logger.error(e.getMessage(), e);
                    }
                }
                ResponseUtils.writeResult(response, result);
                return new ModelAndView();
            }
        });
    }
}
