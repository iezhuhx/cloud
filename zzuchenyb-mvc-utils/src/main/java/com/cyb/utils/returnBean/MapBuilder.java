package com.cyb.utils.returnBean;

import java.util.HashMap;

/**
 * 返回数据
 * 
 * @author iechenyb
 * @email zzuchenyb@sina.com
 * @date 2019年1月3日14:30:35
 */
public class MapBuilder extends HashMap<String, Object> {
	
	private static final long serialVersionUID = 1L;

	public static MapBuilder newInstance() {
		return new MapBuilder();
	}
	
	public  MapBuilder put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	public static MapBuilder build(){
		return new MapBuilder();
	}
	public <T1> MapBuilder data(T1 data){
		super.put("d", data);
		return this;
	}
	
	public static void main(String[] args) {
		MapBuilder r = MapBuilder.newInstance()//静态方法
				.put("a", "a")//非静态方法
				.put("b", "b");//非静态方法
		System.out.println(r);//构造时有限制 必须先调用error或者ok
	}
}
