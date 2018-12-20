package com.kiiik;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kiiik.pub.bean.SystemConfig;
import com.kiiik.pub.bean.YamlEntity;
import com.kiiik.pub.filter.AccessErrorFilter;
import com.kiiik.pub.filter.AccessFilter;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.utils.req.RequestParamAnalysis;

@ServletComponentScan
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@Controller
public class ZuulAuthServiceStarter {
	
	@Autowired
	YamlEntity yaml;
	
	@Autowired
	SystemConfig user;
	
	public static void main(String[] args) {
		SpringApplication.run(ZuulAuthServiceStarter.class, args);
		
	}
	
	@GetMapping("info")
	@ResponseBody
	public String infor(){
		return "增值服务服务端程序！";
	}
	
	@Autowired
	GenericService genericService;
	
	@Autowired
	RequestParamAnalysis analysis;
	    
	@Bean
	public AccessFilter accessFilter() {
		System.err.println(user.getSwaggerStaticUris()+",aaa="+yaml.getSpecialStr());
		AccessFilter filter = new AccessFilter();
		filter.setGenericService(genericService);
		filter.setAnalysis(analysis);
		return filter;
	}
	
	@Bean
	public AccessErrorFilter accessExFilter() {
		return new AccessErrorFilter();
	}
	
	
	@RequestMapping(value = {"/",""})
    public String index() {
       return "redirect:index.html";//swagger-ui
	}
	
}
