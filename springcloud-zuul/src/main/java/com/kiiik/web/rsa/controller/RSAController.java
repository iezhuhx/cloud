package com.kiiik.web.rsa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kiiik.pub.bean.R;
import com.kiiik.web.rsa.service.RsaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年11月6日
 */
@Api
@Controller
@RequestMapping("rsa")
public class RSAController {
	
	@Autowired
	RsaService utils;

	@GetMapping("getPublicKey")
	@ResponseBody
	@ApiOperation("获取公钥信息")
	public R<String> getPubValue() throws Exception {
		return new R<String>(utils.getPublicKey()).success();
	}
	
	/*@GetMapping("getPrivateKeys")
	@ResponseBody
	@ApiOperation("获取密钥信息")
	public ResultBean<String> getPriValue() throws Exception {
		return new ResultBean<String>(utils.getPrivateKey()).success();
	}*/
	
	@GetMapping("encode")
	@ResponseBody
	@ApiOperation("公钥加密")
	public R<String> encode(String password) throws Exception {
		return new R<String>(utils.encodePassword(password)).success();
	}
   
	
	/*@GetMapping("refreshRSA")
	@ApiOperation("刷新公秘钥信息")
	@ResponseBody
	public String refreshRSA() throws Exception {
		utils.genRSASer();
		return "RSA信息刷新成功!";
	}*/

	@ResponseBody
	@GetMapping("validatePasswordSTD")
	@ApiOperation("密码校验")
	public R<String> validatePasswrod(String encrypted,String decrypted) throws Exception {
		String orginPassword = utils.decodePassword(encrypted);
		String infor = "解析密码:"+orginPassword+"，原密码:"+decrypted;
		if(orginPassword.equals(delSpace(decrypted))){
			return new R<String>(infor).success("密码解析成功");
		}else{
			return new R<String>(infor).fail("密码解析失败");
		}
	}
	
	//使用 Java 正则表达式,去除两边空格。
    public static String delSpace(String str) throws Exception {  
        if (str == null) {  
            return null;  
        }  
        String regStartSpace = "^[　 ]*";  
        String regEndSpace = "[　 ]*$";  
        // 连续两个 replaceAll   
        // 第一个是去掉前端的空格， 第二个是去掉后端的空格   
        String strDelSpace = str.replaceAll(regStartSpace, "").replaceAll(regEndSpace, "");  
        return strDelSpace;  
    }
	
}
