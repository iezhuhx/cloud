package com.cyb.web.config;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月21日
 */
@Component
public class GlobalHandlerExceptionResolver 
implements HandlerExceptionResolver{
	Log log = LogFactory.getLog(GlobalHandlerExceptionResolver.class);

	public ModelAndView resolveException(
			HttpServletRequest request, 
			HttpServletResponse response, Object handler,
			Exception ex) {
		log.error(ex.getMessage());
		return null;
	}
}
