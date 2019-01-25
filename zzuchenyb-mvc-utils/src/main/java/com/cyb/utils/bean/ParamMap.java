package com.cyb.utils.bean;

import java.util.HashMap;

/**
 * 返回数据
 * 
 * @author iechenyb
 * @email zzuchenyb@sina.com
 * @date 2019年1月3日14:30:35
 */
public class ParamMap extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public static ParamMap build() {
		ParamMap r = new ParamMap();
		return r;
	}
	
	public ParamMap put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	
	public <T1> ParamMap data(T1 data){
		super.put("d", data);
		return this;
	}
	
	public static void main(String[] args) {
		ParamMap r = ParamMap
				.build()//静态方法
				.put("a", "a")//非静态方法
				.put("b", "b");//非静态方法
		System.out.println(r);//构造时有限制 必须先调用error或者ok
	}
}
