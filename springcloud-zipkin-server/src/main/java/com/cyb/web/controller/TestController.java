package com.cyb.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.cyb.resolver.MyParam;

@RestController
public class TestController {
    @GetMapping("/helloworld")
    public String helloworld() {
        return "helloworld";
    }

    @GetMapping("/test1")
    public String test1() {
        return "权限1";
    }
    
    @PostMapping("/post")
    public String post(String name) {
        return "post测试："+name;
    }
    
    @PostMapping("/file")
    public String post(MultipartFile file) {
        return "post测试："+file;
    }
    
    @GetMapping("/rq")
    public String rq(HttpServletRequest req,String para) {
    	 String user = ((ServletRequestAttributes) 
    			 RequestContextHolder.getRequestAttributes()).getRequest()
                 .getHeader("X-AUTH-ID");
        return user+",后端会话id："+req.getSession().getId()+",服务器ip："+req.getServerPort()+",参数："+para;
    }

    @GetMapping("/myparamresovler")
    public String test2(@MyParam int a) {
        return "权限2";
    }
}
