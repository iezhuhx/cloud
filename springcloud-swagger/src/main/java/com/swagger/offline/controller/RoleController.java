package com.swagger.offline.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swagger.offline.model.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @version V1.0
 * @Title:
 * @ClassName: RoleController.java
 * @Description:
 * @Copyright 2016-2018  - Powered By 研发中心
 * @author: iechenyb
 * @date: 2018-01-22 16:08
 */
@Controller
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "角色信息查询", description = "用户基本信息操作API", tags = "RoleApi", 
consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {

    @ApiOperation(value = "/getRole2", notes = "根据姓名查询用户信息 ")
    @GetMapping(value = "getRole")
    @ResponseBody
    public User getRole(@RequestParam("name") String name) {
    	User user = new User();
        user.setId(123456789);
        user.setName(name);
        user.setAge(27);
        user.setAddress("重庆沙坪坝区");
        user.setSex("男");
        return user;
    }

    @ApiOperation(value = "/addRole2", notes = "添加一个用户")
    @PostMapping(value = "addRole")
    @ResponseBody
    public User addRole(@RequestBody User user) {
        return user;
    }
}
