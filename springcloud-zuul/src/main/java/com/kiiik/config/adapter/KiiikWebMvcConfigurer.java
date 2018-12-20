package com.kiiik.config.adapter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.kiiik.pub.bean.R;
import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.utils.EnvUtils;
import com.kiiik.utils.ResponseUtils;
import com.kiiik.web.example.anno.RequestDateParamMethodArgumentResolver;
import com.kiiik.web.property.KiiikProperties;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月8日
 */
@Configuration
//@EnableWebMvc
public class KiiikWebMvcConfigurer extends WebMvcConfigurerAdapter {
	Log logger = LogFactory.getLog(KiiikWebMvcConfigurer.class);
	
	@Autowired
	KiiikProperties kiiik;
	
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new HandlerExceptionResolver() {
            @Override
            public ModelAndView resolveException(
            		HttpServletRequest request, 
            		HttpServletResponse response,
            		Object handler, 
            		Exception e) {
            	R<String> result = new R<String>();
            	result.data(KiiikContants.BLANK);
            	if(e.getMessage()!=null&&e.getMessage().contains("/favicon.ico")){return new ModelAndView();}
            	logger.error("异常信息:"+e.toString());
                if (handler instanceof HandlerMethod) {
                	ResponseUtils.HandlerMethodExceptionDispatcher(request, response, handler, e, kiiik);
                } else {
                    if (e instanceof NoHandlerFoundException) {
                        result.fail("接口 [" + request.getRequestURI() + "] 不存在");
                        ResponseUtils.writeResult(response, result);
                    } else if(e instanceof HttpRequestMethodNotSupportedException){
                    	result.fail("请求方法"+request.getMethod()+"不支持，请检查方法类型是否匹配！");
                    	ResponseUtils.writeResult(response, result);
                    }else {
                        result.fail("服务器端发现未知异常，请联系管理员!");
                        logger.error(e.getMessage(), e);
                        ResponseUtils.writeResult(response, result);
                    }
                }
                return new ModelAndView();
            }
        });
    }
    @Autowired
    EnvUtils utils;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	System.err.println("jar目录："+utils.getRunDir().getParentFile().getAbsolutePath());
    	 registry
    	 .addResourceHandler("/**")
         .addResourceLocations("classpath:/META-INF/resources/")
         .addResourceLocations("classpath:/resources/")
         .addResourceLocations("classpath:/static/")
         .addResourceLocations("classpath:/public/")
    	 //.addResourceLocations("file:D:/data/web/")//手动指定某个目录
    	 .addResourceLocations("file:"+utils.getRunDir().getParentFile().getAbsolutePath()+"/webapp/")
    	 .addResourceLocations("file:"+utils.getRunDir().getParentFile().getAbsolutePath()+"/app/");
    	 if(KiiikContants.DEV.equals(utils.getActiveProfile())){
    		 //开发环境
    		 registry
        	 .addResourceHandler("/**").addResourceLocations("file:"+utils.getRunDir().getParentFile().getParentFile().getAbsolutePath()+"/webapp/")
    		 .addResourceLocations("file:"+utils.getRunDir().getParentFile().getParentFile().getParentFile().getAbsolutePath()+"/app/");
    	 }
         registry.addResourceHandler("swagger-ui.html")
         .addResourceLocations("classpath:/META-INF/resources/");
         registry.addResourceHandler("/webjars/**")
         .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }
    
    @Bean
    public HttpMessageConverter<String> responseBodyStringConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        return converter;
    }
    
    /**
     * 修改StringHttpMessageConverter默认配置
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
        converters.add(responseBodyStringConverter());
    }
    
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RequestDateParamMethodArgumentResolver());    //添加自定义参数解析器
    }
}
