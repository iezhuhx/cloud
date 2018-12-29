package com.cyb.adapter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月8日
 */
@Configuration
public class KiiikWebMvcConfigurer extends WebMvcConfigurerAdapter {
	Log logger = LogFactory.getLog(KiiikWebMvcConfigurer.class);
	
	
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new HandlerExceptionResolver() {
            public ModelAndView resolveException(
            		HttpServletRequest request, 
            		HttpServletResponse response,
            		Object handler, 
            		Exception e) {
            	System.err.println(request.getRequestURI()+","+request.getMethod()+","+e.getMessage());
                try {
					response.getWriter().write("BaseService Error："+e.getMessage());
				} catch (IOException e1) {
					e1.printStackTrace();
					
				}
            	return new ModelAndView();
            }
        });
    }
    //swagger-ui.html显示的关键
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**")/*.addResourceHandler("/public/**")*/
        .addResourceLocations("classpath:/static");
        super.addResourceHandlers(registry);
    }
    
    @Bean
    public HttpMessageConverter<String> responseBodyStringConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        return converter;
    }
   
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
        converters.add(responseBodyStringConverter());
    }
    
   
}
