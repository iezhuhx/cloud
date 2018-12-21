package com.cyb.web.test.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.web.test.service.TestServiceImpl2;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月20日
 */
@RestController
public class WelcomeController {
	/**
	 * 通过bean注解创建
	 */
	@Autowired
	TestServiceImpl2 service;
	
    @GetMapping(value="/welcome")
    public Map<String,String> goHome(){
        Map<String ,String> map = new HashMap<String, String>();
        map.put("userName", "MVC4.1!");
        service.sayHello("home");
        return map;
    }
  
    //extends WebMvcConfigurerAdapter @Configuration
}