package com.kiiik.pub.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年11月2日
 */
public class RepeatedlyReadFilter implements Filter {
	Log log = LogFactory.getLog(RepeatedlyReadFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("包装类处理!!!");
		/*if (request instanceof HttpServletRequest) {
			requestWrapper = new RepeatedlyReadRequestWrapper((HttpServletRequest) request);
		}*/
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {

	}
}
