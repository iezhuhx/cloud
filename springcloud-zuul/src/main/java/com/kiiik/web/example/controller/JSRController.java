package com.kiiik.web.example.controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */

import com.kiiik.pub.ann.IgnoreApi;
import com.kiiik.pub.bean.R;
import com.kiiik.web.example.jsr.bean.ValidateBean;
@RestController
@RequestMapping("jsr")
@IgnoreApi
public class JSRController {
	Log log = LogFactory.getLog(JSRController.class);
	@PostMapping("validate")
	public R<String> validateBean(@RequestBody @Validated ValidateBean bean){
		System.out.println(bean);
		return new R<String>("").success();
	}
}
