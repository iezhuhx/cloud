package com.kiiik.pub.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
/**
 * error type 服务未启动：{ec:500,es:"forwarding error"}
 * 404 走errorcocntroller
 * 500 参数异常
 * 关闭默认的异常处理逻辑
 * zuul.SendErrorFilter.error.disable=true 
 * @author DHUser
 *
 */
public class AccessErrorFilter extends ZuulFilter {

	@Override
	public String filterType() {
		return "error";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		try {
			RequestContext context = RequestContext.getCurrentContext();
			ZuulException exception = this.findZuulException(context.getThrowable());
			HttpServletResponse response = context.getResponse();
			response.setContentType("application/json; charset=utf8");
			response.setStatus(exception.nStatusCode);
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
				writer.print("{code:" + exception.nStatusCode + ",message:\"" + exception.getMessage() + "\"}");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					writer.close();
				}
			}

		} catch (Exception var5) {
			// ReflectionUtils.rethrowRuntimeException(var5);
			var5.printStackTrace();
		}

		return null;
	}

	ZuulException findZuulException(Throwable throwable) {
		if (ZuulRuntimeException.class.isInstance(throwable.getCause())) {
			return (ZuulException) throwable.getCause().getCause();
		} else if (ZuulException.class.isInstance(throwable.getCause())) {
			return (ZuulException) throwable.getCause();
		} else {
			return ZuulException.class.isInstance(throwable) ? (ZuulException) throwable
					: new ZuulException(throwable, 500, (String) null);
		}
	}
}
