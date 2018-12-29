package com.cyb.web.test.controller;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cyb.utils.controller.BaseController;
import com.cyb.utils.jsr.bean.ValidateBean;
import com.cyb.web.test.service.TestService;
import com.cyb.web.test.service.TestServiceImpl2;
import com.wordnik.swagger.annotations.Api;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月20日
 */
@RestController
@RequestMapping("api")
@Api(value = "api",description="描述")
public class TestController extends BaseController{
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
	
	 @GetMapping(value="/clientInfor")
	 public String ip(){
		 return this.getCurIp()+","+this.getCurPort()+","+this.getRemoteAddr();
	 }
	
    @GetMapping(value="/check")
    public Map<String,String> goHome(){
        Map<String ,String> map = new HashMap<String, String>();
        map.put("userName", "MVC4.1!中文");
        map.put("user", getRemoteUser());
        service.sayHello("@bean");
        service1.sayHello("@sevice");
        return map;
    }
    
    @GetMapping(value="/excepton")
    public Map<String,String> ex(@RequestParam("number") int number){
        Map<String ,String> map = new HashMap<String, String>();
        map.put("userName", "MVC4.1!");
        service.sayHello("home");
        System.out.println("aaa="+10/number);
        return map;
    }
    
    
    @PostMapping(value="/jsrJavax")
    public Map<String,Object> jsrJava(@RequestBody @Valid ValidateBean bean){
        Map<String ,Object> map = new HashMap<String, Object>();
        map.put("bean", bean);
        service.sayHello("home");
        return map;
    }
    
    @PostMapping(value="/jsrSpring")
    public Map<String,Object> jsrSpring(@RequestBody @Validated ValidateBean bean){
        Map<String ,Object> map = new HashMap<String, Object>();
        map.put("bean", bean);
        service.sayHello("home");
        return map;
    }
  
}