package com.cyb.utils.exception;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月25日
 */
public class ExceptionCode {
	Log log = LogFactory.getLog(ExceptionCode.class);
	/**
	 *  200 请求已成功，请求所希望的响应头或数据体将随此响应返回。

		301 被请求的资源已永久移动到新位置。
		
		302 请求的资源现在临时从不同的 URI 响应请求。
		
		400 1、语义有误，当前请求无法被服务器理解。2、请求参数有误。
		
		401 当前请求需要用户验证。
		
		403 服务器已经理解请求，但是拒绝执行它。
		
		404 请求失败，请求所希望得到的资源未被在服务器上发现。
		
		500 服务器遇到了一个未曾预料的状况，无法完成对请求的处理，会在程序码出错时出现。
		
		501 服务器不支持当前请求所需要的某个功能。无法识别请求的方法。
		
		502 作为网关或者代理工作的服务器尝试执行请求时，从上游服务器接收到无效的响应。
		
		503 由于临时的服务器维护或者过载，服务器当前无法处理请求。
	 */
	
	public static Map<Integer, String> ERRORCODE = null;
	static{
		ERRORCODE = new java.util.HashMap<Integer,String>();
		System.out.println("init...");
		ERRORCODE.put(400, "请求无法被解析或者参数有误！");
		ERRORCODE.put(401, "用户未授权！");
		ERRORCODE.put(403, "拒绝访问！");
		ERRORCODE.put(404, "请求内容不存在！");
		
		ERRORCODE.put(500, "服务器发生异常！");
		ERRORCODE.put(501, "网关或者代理失败！");
		ERRORCODE.put(503, "服务器维护中或者过载！");
		System.out.println(ERRORCODE);
	}
	
	public static void main(String[] args) {
		System.out.println(ERRORCODE.get(403));
	}
}
