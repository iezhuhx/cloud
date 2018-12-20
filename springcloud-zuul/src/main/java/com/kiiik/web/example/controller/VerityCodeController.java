package com.kiiik.web.example.controller;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kiiik.pub.ann.IgnoreApi;
import com.kiiik.pub.bean.R;
import com.kiiik.utils.VerifyCodeUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月6日
 */
@Controller
@RequestMapping("code")
@Api
@IgnoreApi
public class VerityCodeController {
	Log log = LogFactory.getLog(VerityCodeController.class);
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取验证码<br>
	 *创建时间: 2017年7月15日
	 *@return
	 */
	@GetMapping(value="/authImage")
	@ApiOperation("跳转到验证码的操作界面")
    public String authImage(){
    	return "/code/authImage";
    }
	
	@GetMapping(value="/getImage")
	@ApiOperation("获取验证码")
    public void authImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		// 存入会话session
		HttpSession session = request.getSession(true);
		// 删除以前的
		session.removeAttribute("verCode");
		session.removeAttribute("codeTime");
		session.setAttribute("verCode", verifyCode.toLowerCase());
		session.setAttribute("codeTime", System.currentTimeMillis());
		// 生成图片
		int w = 100, h = 30;
		OutputStream out = response.getOutputStream();
		VerifyCodeUtils.outputImage(w, h, out, verifyCode);
    }
	
	@GetMapping(value="validImage")
	@ResponseBody
	@ApiOperation("验证码校验")
    public R<String> validImage(String code,HttpSession session){
    	Object verCode = session.getAttribute("verCode");
    	if (null == verCode) {
    		return new R<String>().fail("验证码已失效，请重新输入");
    	}
    	String verCodeStr = verCode.toString();
    	if(verCodeStr == null || code == null || code.isEmpty() || !verCodeStr.equalsIgnoreCase(code)){
    		return new R<String>().fail("验证码错误");
    	}/* else if((now-past)/1000/60>5){
    		request.setAttribute("errmsg", "验证码已过期，重新获取");
    		return "验证码已过期，重新获取";
    	} */else {
    		//验证成功，删除存储的验证码
    		session.removeAttribute("verCode");
    		return new R<String>().success("验证码正确！");
    	}
    }
}
