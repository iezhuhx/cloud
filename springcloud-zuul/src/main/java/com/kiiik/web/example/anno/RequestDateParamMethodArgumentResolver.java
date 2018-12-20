package com.kiiik.web.example.anno;

import java.text.SimpleDateFormat;

import javax.servlet.ServletException;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年10月31日
 */
@Component
public class RequestDateParamMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {
	/**
	 * 获取当前参数的注解信息
	 *
	 * @param methodParameter
	 *            需要被解析的Controller参数
	 * @return
	 */
	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter methodParameter) {
		RequestDateParam annotation = (RequestDateParam) methodParameter.getParameterAnnotation(RequestDateParam.class);
		return new NamedValueInfo(annotation.value(), annotation.required(), (String) null);
	}

	/**
	 * 在这里进行参数的类型转换
	 *
	 * @param s
	 * @param methodParameter
	 *            需要被解析的Controller参数
	 * @param nativeWebRequest
	 *            当前request
	 * @return 转换后的参数值
	 * @throws Exception
	 * http://localhost/api/requestDateParamTest?date=2018-01-02 12:00:00
	 * 使用swagger-ui自定义标签无法识别绑定参数。
	 */
	@Override
	protected Object resolveName(String s, MethodParameter methodParameter, NativeWebRequest nativeWebRequest)
			throws Exception {
		String content = nativeWebRequest.getParameter(s);//获取date为空
		if (content == null) {
			return null;
		} else {
			try {
				RequestDateParam annotation = methodParameter.getParameterAnnotation(RequestDateParam.class);
				SimpleDateFormat dateFormat = new SimpleDateFormat(annotation.pattern().getValue());
				return dateFormat.parse(content);
			} catch (Exception e) {
				throw new IllegalArgumentException("Date format conversion error", e);
			}
		}
	}

	/**
	 * 解析器是否支持当前参数
	 *
	 * @param methodParameter
	 *            需要被解析的Controller参数
	 * @return
	 */
	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.hasParameterAnnotation(RequestDateParam.class);
	}

	/**
	 * 当前参数值为空且注解的默认值也为空则抛出异常
	 *
	 * @param name
	 *            参数名
	 * @param parameter
	 *            需要被解析的Controller参数
	 * @throws ServletException
	 */
	@Override
	protected void handleMissingValue(String name, MethodParameter parameter) throws ServletException {
		throw new MissingServletRequestParameterException(name, parameter.getParameterType().getSimpleName());
	}

}
