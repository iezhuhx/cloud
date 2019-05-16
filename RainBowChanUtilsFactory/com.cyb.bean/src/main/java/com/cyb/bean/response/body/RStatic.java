package com.cyb.bean.response.body;

import com.cyb.bean.response.constant.ResponseStatus;

import java.io.Serializable;
import java.util.HashMap;



/**
 * 作者 : iechenyb<br>
 * 类描述:返回实体信息,与R2的区别，不创建对象<br>
 * 创建时间: 2018年1月10日
 */
public class RStatic<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String es;
	
	private String ec ;//默认成功
	
	protected  T d;

	public RStatic() {
		ec = ResponseStatus.SUCCESS;
	}

	public RStatic(T d) {
		ec = ResponseStatus.SUCCESS;
		this.d = d;
	}

	public RStatic(Throwable e) {
		super();
		this.es = e.toString();
		this.ec = ResponseStatus.FAIL;
	}
	public RStatic<T> success() {
		this.ec = ResponseStatus.SUCCESS;
		return this;
	}
	public RStatic<T> success(String msg) {
		this.ec = ResponseStatus.SUCCESS;
		this.es = msg;
		return this;
	}
	public RStatic<T> data(T data) {
		this.d = data;
		return this;
	}

	public static <T> RStatic<T> fail() {
		return fail("");
	}
	public static <T> RStatic<T> fail(String msg) {
		RStatic<T> r = new RStatic<T>();
		r.ec = ResponseStatus.FAIL;
		r.es = msg;
		return r;
	}

	public static <T> RStatic<T> fail(Throwable e) {
		return fail(e.toString());
	}
	
	public RStatic<T> msg(String msg) {
		this.es = msg;
		return this;
	}

	public static String getNoPermission() {
		return ResponseStatus.NO_PERMISSION;
	}
	
	public static <T> RStatic<T> refuse(){
		RStatic<T> r = new RStatic<T>();
		r.ec = ResponseStatus.NO_PERMISSION;
		return r;
	}
	public static <T> RStatic<T> sessionTimeOut(String msg){
		RStatic<T> r = new RStatic<T>();
		r.ec = ResponseStatus.SESSION_TIME_OUT;
		r.es=msg;
		return r;
	}
	public  static <T> RStatic<T> sessionTimeOut(){
		return sessionTimeOut("");
	}
	
	public static <T> RStatic<T> refuse(String msg){
		RStatic<T> r = new RStatic<T>();
		r.ec = ResponseStatus.NO_PERMISSION;
		r.es = msg;
		return r;
	}
	
	public static <T> RStatic<T> needToModifyPassword(){
		return needToModifyPassword("");
	}
	
	public static <T1> RStatic<T1> needToModifyPassword(String msg){
		RStatic<T1> r = new RStatic<T1>();
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
		RStatic<?> r3 = RStatic.//? 改成指定的类型不行
				needToModifyPassword("修改密码")
				.data("aaaaa");
		System.out.println(r3);
		RStatic.//? 改成指定的类型不行
				needToModifyPassword("修改密码")
				.data(new HashMap<String,String>());
	}
	
	public String toString(){
		return "ec="+ec+",es"+es+",data="+d;
	}
}
