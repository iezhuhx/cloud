package com.cyb.bean.response.body;

import com.cyb.bean.response.constant.ResponseStatus;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 作者 : iechenyb<br>
 * 类描述:返回实体信息<br>
 * 创建时间: 2018年1月10日
 */
public class R3<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String es;
	
	private String ec ;//默认成功
	
	protected  T d;

	public R3() {
		ec = ResponseStatus.SUCCESS;
	}

	public R3(T d) {
		ec = ResponseStatus.SUCCESS;
		this.d = d;
	}

	public R3(Throwable e) {
		super();
		this.es = e.toString();
		this.ec = ResponseStatus.FAIL;
	}
	public R3<T> success() {
		this.ec = ResponseStatus.SUCCESS;
		return this;
	}
	public R3<T> success(String msg) {
		this.ec = ResponseStatus.SUCCESS;
		this.es = msg;
		return this;
	}
	public R3<T> data(T data) {
		this.d = data;
		return this;
	}

	public static <T> R3<T> fail() {
		return fail("");
	}
	public static <T> R3<T> fail(String msg) {
		R3<T> r = new R3<T>();
		r.ec = ResponseStatus.FAIL;
		r.es = msg;
		return r;
	}

	public static <T> R3<T> fail(Throwable e) {
		return fail(e.toString());
	}
	
	public R3<T> msg(String msg) {
		this.es = msg;
		return this;
	}

	public static String getNoPermission() {
		return ResponseStatus.NO_PERMISSION;
	}
	
	public static <T> R3<T> refuse(){
		R3<T> r = new R3<T>();
		r.ec = ResponseStatus.NO_PERMISSION;
		return r;
	}
	public static <T> R3<T> sessionTimeOut(String msg){
		R3<T> r = new R3<T>();
		r.ec = ResponseStatus.SESSION_TIME_OUT;
		r.es=msg;
		return r;
	}
	public  static <T> R3<T> sessionTimeOut(){
		return sessionTimeOut("");
	}
	
	public static <T> R3<T> refuse(String msg){
		R3<T> r = new R3<T>();
		r.ec = ResponseStatus.NO_PERMISSION;
		r.es = msg;
		return r;
	}
	
	public static <T> R3<T> needToModifyPassword(){
		return needToModifyPassword("");
	}
	
	public static <T1> R3<T1> needToModifyPassword(String msg){
		R3<T1> r = new R3<T1>();
		r.ec = ResponseStatus.USE_DEFAULT_PASSWORD;
		r.es = msg;
		return r;
	}

	public String getEs() {
		return es;
	}

	public void setEs(String es) {
		this.es = es;
	}

	public String getEc() {
		return ec;
	}

	public void setEc(String ec) {
		this.ec = ec;
	}

	public T getD() {
		return d;
	}

	public void setD(T d) {
		this.d = d;
	}
	
	public static void main(String[] args) {
		R3<?> r3 = R3.//? 改成指定的类型不行
				needToModifyPassword("修改密码")
				.data("aaaaa");
		System.out.println(r3);
		R3.//? 改成指定的类型不行
				needToModifyPassword("修改密码")
				.data(new HashMap<String,String>());
	}
	
	public String toString(){
		return "ec="+ec+",es"+es+",data="+d;
	}
}
