package com.cyb.web.test.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cyb.web.test.service.TestService;
import com.cyb.web.test.service.TestServiceImpl2;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月20日
 */
@Controller
public class HomeController {
	/**
	 * 通过bean注解创建
	 */
	@Autowired
	TestService service;
	protected String getRemoteUser() {
		String user = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getHeader("X-AUTH-ID");
		return user;
	}
	
	@Autowired
	TestServiceImpl2 service1;
    @GetMapping(value="/home")
    @ResponseBody
    public Map<String,String> goHome(){
        Map<String ,String> map = new HashMap<String, String>();
        map.put("userName", "MVC4.1!中文");
        map.put("user", getRemoteUser());
        service.sayHello("@bean");
        service1.sayHello("@sevice");
        return map;
    }
    
    @GetMapping(value="/ex")
    @ResponseBody
    public Map<String,String> ex(int number){
        Map<String ,String> map = new HashMap<String, String>();
        map.put("userName", "MVC4.1!");
        service.sayHello("home");
        System.out.println("aaa="+10/number);
        return map;
    }
  
}