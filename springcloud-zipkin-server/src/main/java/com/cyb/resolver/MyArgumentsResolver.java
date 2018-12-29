package com.cyb.resolver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月31日
 */
public class MyArgumentsResolver implements HandlerMethodArgumentResolver{
	Log log = LogFactory.getLog(MyArgumentsResolver.class);
	 /**
     * 解析器是否支持当前参数
     */
    public boolean supportsParameter(MethodParameter parameter) {
        // 指定参数如果被应用MyParam注解，则使用该解析器。
        // 如果直接返回true，则代表将此解析器用于所有参数
        return parameter.hasParameterAnnotation(MyParam.class);
    }

    /**
     * 将request中的请求参数解析到当前Controller参数上
     * @param parameter 需要被解析的Controller参数，此参数必须首先传给{@link #supportsParameter}并返回true
     * @param mavContainer 当前request的ModelAndViewContainer
     * @param webRequest 当前request
     * @param binderFactory 生成{@link WebDataBinder}实例的工厂
     * @return 解析后的Controller参数
     */
    public Object resolveArgument(MethodParameter parameter, 
    		ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, 
                                  WebDataBinderFactory binderFactory) throws Exception {
        System.out.println("paramter"+parameter);
        return null;
    }
}
