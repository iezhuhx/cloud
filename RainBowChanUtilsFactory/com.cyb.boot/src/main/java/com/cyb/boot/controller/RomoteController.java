package com.cyb.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author iechenyb
 * @description
 * @createTime
 */
@RestController
public class RomoteController {
    //http://localhost:8080/debug?a=1&b=2
    @RequestMapping("debug")
    public String method(int a,int b){
        System.out.println("a+b="+a+b);
        return String.valueOf(a+b);
    }
}
