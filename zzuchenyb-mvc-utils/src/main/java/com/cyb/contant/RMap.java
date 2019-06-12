package com.cyb.contant;


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
}
