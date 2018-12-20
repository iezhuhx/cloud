package com.kiiik.config;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月26日
 */
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.utils.EnvUtils;
import com.kiiik.web.property.KiiikProperties;

//@Configuration
public class GlobalCorsConfig {
	@Autowired
	KiiikProperties kiiik;
	@Autowired
	EnvUtils env;
    @Bean
    public CorsFilter corsFilter() {
    	 //1.添加CORS配置信息
         CorsConfiguration config = new CorsConfiguration();
         UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
    	 if(!KiiikContants.PROD.equals(env.getActiveProfile())){
	        //1) 允许的域,不要写*，否则cookie就无法使用了
	        // 允许跨域访问的域名
	        config.addAllowedOrigin("*");
	        //config.addAllowedOrigin("http://192.168.16.211:8080");
	        //2) 是否发送Cookie信息
	        config.setAllowCredentials(true);
	        //3) 允许的请求方式
	        /*config.addAllowedMethod("*");
	        config.addAllowedMethod("OPTIONS");
	        config.addAllowedMethod("HEAD");
	        config.addAllowedMethod("GET");
	        config.addAllowedMethod("PUT");
	        config.addAllowedMethod("POST");
	        config.addAllowedMethod("DELETE");
	        config.addAllowedMethod("PATCH");*/
	        // 4）允许的头信息
	        config.addAllowedHeader("*");
	        //2.添加映射路径，我们拦截一切请求
	        configSource.registerCorsConfiguration("/**", config);
    	}
        //3.返回新的CorsFilter.
        return new CorsFilter(configSource);
    }
}
