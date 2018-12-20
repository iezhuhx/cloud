package com.kiiik.web.example.controller;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UrlPathHelper;

import com.kiiik.pub.ann.IgnoreApi;

import io.swagger.annotations.Api;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月6日
 */
@Api
@Controller
@RequestMapping("test/rsa")
@IgnoreApi
public class RSADevController {
	
	RouteLocator a;
	UrlPathHelper  h;
	public void test(){
	}
	/*
	private static String RSAKeyStore = System.getProperty("user.dir")+"/RSAKey.ser"; 
	Log log = LogFactory.getLog(RSADevController.class);
	public static Map<String,String> passwords=null;
	static{
		passwords = new HashMap<>();
		passwords.put("111111", "纯数字");//96E79218965EB72C92A549DD5A330112
		passwords.put("abcdef", "纯字母");//E80B5017098950FC58AAD83C8C14978E
		passwords.put("123dfdfg", "字母和数字组合");//522488100A714638782E066A14A1325A
		passwords.put("`!@#$%^&*()_+|?></{}[]", "特殊符号 英文");//转义字符\不可以用作密码 31C16E9EB76E91A6B9F0BF20DCCF7EBA
		passwords.put("·~！@#￥%……&*（）——+、、】【《》", "特殊符号中文");//10E551C3D87981220A9B9E5DED6DB7D9
		passwords.put("96E79218965EB72C92A549DD5A330112", "111111");
		passwords.put("E80B5017098950FC58AAD83C8C14978E", "abcdef");
		passwords.put("522488100A714638782E066A14A1325A", "123dfdfg");
		passwords.put("31C16E9EB76E91A6B9F0BF20DCCF7EBA", "`!@#$%^&*()_+|?></{}[]");
		passwords.put("10E551C3D87981220A9B9E5DED6DB7D9", "·~！@#￥%……&*（）——+、、】【《》");
	}
	
	@GetMapping("/")
	@ApiOperation("跳转到rsa登录页面")
	public ModelAndView  toRSALogin() throws Exception{
		ModelAndView view = new ModelAndView();
		view.setViewName("/rsa/rsa");
		view.addObject("m", getPublicKey());
		view.addObject("e",RSAUtil.getKeyPair(RSAKeyStore)
    			.getPublic()
    			.toString()
    			.replace("\n", "#")
    			.split("#")[2].trim()
    			.split(":")[1].trim());
		return view;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("getPublicKeys")
	@ResponseBody
	@ApiOperation("获取公钥信息")
	public ResultBean<Map<String,String>>  getMEValue() throws Exception{
		Map<String,String> data = new HashMap<>();
		data.put("m", getPublicKey());
		data.put("e",RSAUtil.getKeyPair(RSAKeyStore)
    			.getPublic()
    			.toString()
    			.replace("\n", "#")
    			.split("#")[2].trim()
    			.split(":")[1].trim());
		return new ResultBean<Map<String,String>>(data).success();
	}
	
	@GetMapping("refreshRSA")
	@ApiOperation("获取公钥")
	@ResponseBody
	public String  refreshRSA() throws Exception{
		System.out.println("秘钥文件存储"+RSAKeyStore);
		File file = new File(RSAKeyStore);
		if(file.exists()){
			file.delete();
		}
		file.createNewFile();
		RSAUtil.generateKeyPair(RSAKeyStore);
		return "RSA信息刷新成功!";
	}
	
	@GetMapping("getPublicKey")
	@ApiOperation("获取公钥")
	@ResponseBody
	public String  getPublicKey(){
		try {
			return RSAUtil.getKeyPair(RSAKeyStore)
					.getPublic()
					.toString()
					.replace("\n", "#")
					.split("#")[1].trim()
					.split(":")[1].trim();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@GetMapping("putNewPassword")
	@ApiOperation("新增一个验证密码")
	@ResponseBody
	public Map<String,String>  putNewPassword(String password){
		passwords.put(password, "");
		return passwords;
	}
	
	@GetMapping("listPassword")
	@ResponseBody
	@ApiOperation("查看已有密码列表")
	public Map<String,String>  listPassword(){
		return passwords;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("validatePasswordSTD")
	@ApiOperation("密码校验")
	public ResultBean<String> validatePasswrod(String password){
		String encodeStr = new String(Base64.decode(password.getBytes()));
    	byte[] en_result = new BigInteger(encodeStr, 16).toByteArray();
    	byte[] de_result;
		try {
			de_result = RSAUtil.decrypt(RSAUtil.getKeyPair(RSAKeyStore).getPrivate(),
					en_result);
			StringBuilder orignPassword = new StringBuilder(new String(de_result)).reverse();
			System.out.println("将请求进行解密:"+orignPassword.toString());
			String realPassword = URLDecoder.decode(orignPassword.toString(),"UTF-8");
	    	System.out.println("将请求进行解密:"+realPassword);
	    	if(passwords.containsKey(realPassword.toUpperCase())){//必须转换成大写
	    		return new ResultBean<String>(realPassword).success("密码校验成功，解密内容："+realPassword);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<String>().fail("密码校验失败！");
		}
		return new ResultBean<String>().fail("密码校验失败.");
		
	}
*/}
