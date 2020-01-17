package com.cyb.utils.returnBean.fin;

import java.util.HashMap;

import com.cyb.utils.response.ResponseStatus;

/**
 * 返回数据对象
 * 
 * @author iechenyb
 * @email zzuchenyb@sina.com
 * @date 2019年1月3日14:30:35
 */
public class CybMap extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	private static final String CODEKEY = "ec";
	private static final String DESCKEY = "es";

	// 默认成功
	public static CybMap build(String msg) {
		CybMap r = new CybMap();
		success(r);
		r.put(DESCKEY, msg);
		return r;
	}

	// 默认失败
	public static CybMap buildFail(String msg) {
		CybMap r = new CybMap();
		fail(r);
		r.put(DESCKEY, msg);
		return r;
	}

	// 默认成功
	public static CybMap ok(String msg) {
		return build(msg);
	}

	// 默认失败
	public static CybMap fail(String msg) {
		return buildFail(msg);
	}

	// 默认成功
	public static CybMap build() {
		return build("");
	}

	// 默认失败
	public static CybMap buildFail() {
		return buildFail("");
	}

	public static void success(CybMap r) {
		r.put(CODEKEY, ResponseStatus.SUCCESS);// 默认成功
		r.put(DESCKEY, ResponseStatus.DEFAULT_SUCCESS_MSG);
	}

	public static void fail(CybMap r) {
		r.put(CODEKEY, ResponseStatus.FAIL);// 默认成功
		r.put(DESCKEY, ResponseStatus.DEFAULT_FAIL_MSG);
	}

	public CybMap put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public CybMap putSuccessMsg(Object value) {
		success(this);
		super.put(DESCKEY, value);
		return this;
	}

	public CybMap putFailMsg(Object value) {
		fail(this);
		super.put(DESCKEY, value);
		return this;
	}

	public <T> CybMap data(T data) {
		super.put("d", data);
		return this;
	}

	public static void main(String[] args) {
		CybMap r = CybMap 
				.ok("成功提示信息！")//静态方法
				.put("a", "a")//非静态方法
				.put("b", "b");//非静态方法
		System.out.println(r);
		r = CybMap
				.fail("失败提示信息!")// 静态方法
				.put("a", "a")//非静态方法
				.put("b", "b");//非静态方法
		System.out.println(r);//构造时有限制 必须调用error或者ok
	}
}
