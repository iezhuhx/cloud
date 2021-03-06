package com.cyb.bean.response.body;


import com.cyb.bean.response.constant.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * 
 * @author iechenyb
 * @email zzuchenyb@sina.com
 * @date 2019年1月3日14:30:35
 */
public class RMap extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	public static String CODE="ec";
	public static String MSG="es";
	public RMap() {
		put(CODE, ResponseStatus.SUCCESS);
	}
	
	public static RMap error() {
		return error(ResponseStatus.FAIL, "未知异常，请联系管理员");
	}
	
	public static RMap error(String msg) {
		return error(ResponseStatus.FAIL, msg);
	}
	
	public static RMap error(String code, String msg) {
		RMap r = new RMap();
		r.put(CODE, code);
		r.put(MSG, msg);
		return r;
	}

	public static RMap ok(String msg) {
		RMap r = new RMap();
		r.put(MSG, msg);
		return r;
	}
	
	public static RMap ok(Map<String, Object> map) {
		RMap r = new RMap();
		r.putAll(map);
		return r;
	}
	
	public static RMap ok() {
		return new RMap();
	}

	public RMap put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	
	public <T1> RMap data(T1 data){
		super.put("d", data);
		return this;
	}
	
	public static void main(String[] args) {
		RMap r = RMap.error("错误")//静态方法
				.put("a", "a")//非静态方法
				.put("b", "b");//非静态方法
		System.out.println(r);//构造时有限制 必须先调用error或者ok
	}
}
