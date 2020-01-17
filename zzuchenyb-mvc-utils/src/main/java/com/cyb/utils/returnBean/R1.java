package com.cyb.utils.returnBean;

import java.util.HashMap;
import java.util.Map;

import com.cyb.utils.response.ResponseStatus;

/**
 * 返回数据
 * 
 * @author iechenyb
 * @email zzuchenyb@sina.com
 * @date 2019年1月3日14:30:35
 */
public class R1 extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	public static String CODE="ec";
	public static String MSG="es";
	public R1() {
		put(CODE, ResponseStatus.SUCCESS);
	}
	
	public static R1 error() {
		return error(ResponseStatus.FAIL, "未知异常，请联系管理员");
	}
	
	public static R1 error(String msg) {
		return error(ResponseStatus.FAIL, msg);
	}
	
	public static R1 error(String code, String msg) {
		R1 r = new R1();
		r.put(CODE, code);
		r.put(MSG, msg);
		return r;
	}

	public static R1 ok(String msg) {
		R1 r = new R1();
		r.put(MSG, msg);
		return r;
	}
	
	public static R1 ok(Map<String, Object> map) {
		R1 r = new R1();
		r.putAll(map);
		return r;
	}
	
	public static R1 ok() {
		return new R1();
	}

	public R1 put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	
	public <T1> R1 data(T1 data){
		super.put("d", data);
		return this;
	}
	
	public static void main(String[] args) {
		R1 r = R1.error("错误")//静态方法
				.put("a", "a")//非静态方法
				.put("b", "b");//非静态方法
		System.out.println(r);//构造时有限制 必须先调用error或者ok
	}
}
