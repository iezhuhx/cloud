package com.cyb.web.config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cyb.web.test.service.TestService;
import com.cyb.web.test.service.TestServiceImpl;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月21日
 */
@Configuration
//@EnableWebMvc
public class BeanConfig {
	Log log = LogFactory.getLog(BeanConfig.class);
	
	@Bean
    public TestService testService(){
    	return new TestServiceImpl();
    }
}
