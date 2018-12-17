package com.kiiik.web.example.controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */

import com.kiiik.pub.bean.res.ResultBean;
import com.kiiik.web.example.jsr.bean.ValidateBean;
@RestController
@RequestMapping("jsr")
public class JSRController {
	Log log = LogFactory.getLog(JSRController.class);
	
	@SuppressWarnings("unchecked")
	@PostMapping("validateBean")
	public ResultBean<String> validateBean(@RequestBody @Validated ValidateBean bean){
		System.out.println(bean);
		return new ResultBean<String>("").success();
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("validateParam")
	public ResultBean<String> validateParam( @Validated @NotBlank String param1){
		System.out.println(param1);
		return new ResultBean<String>("").success(param1);
	}
}
