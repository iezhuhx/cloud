package com.cyb.bean.response.body;

import com.cyb.bean.response.constant.ResponseStatus;

import java.io.Serializable;

/**
 * 作者 : iechenyb<br>
 * 类描述:返回实体信息<br>
 * 创建时间: 2018年1月10日
 */
public class R2<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String es;
	
	private String ec ;//默认成功
	
	protected  T d;

	public R2() {
		ec = ResponseStatus.SUCCESS;
	}

	public R2(T d) {
		ec = ResponseStatus.SUCCESS;
		this.d = d;
	}

	public R2(Throwable e) {
		super();
		this.es = e.toString();
		this.ec = ResponseStatus.FAIL;
	}
	public R2<T> success() {
		this.ec = ResponseStatus.SUCCESS;
		return this;
	}
	public R2<T> success(String msg) {
		this.ec = ResponseStatus.SUCCESS;
		this.es = msg;
		return this;
	}
	public R2<T> data(T data) {
		this.d = data;
		return this;
	}

	public R2<T> fail() {
		this.ec = ResponseStatus.FAIL;
		return this;
	}
	public R2<T> fail(String msg) {
		this.ec = ResponseStatus.FAIL;
		this.es = msg;
		return this;
	}

	public R2<T> fail(Throwable e) {
		this.ec = ResponseStatus.FAIL;
		this.es = e.toString();
		return this;
	}
	
	public  R2<T> msg(String msg) {
		this.es = msg;
		return this;
	}

	public static String getNoPermission() {
		return ResponseStatus.NO_PERMISSION;
	}
	
	public R2<T> refuse(){
		this.ec = ResponseStatus.NO_PERMISSION;
		return this;
	}
	public R2<T> sessionTimeOut(String msg){
		this.ec = ResponseStatus.SESSION_TIME_OUT;
		this.es=msg;
		return this;
	}
	public R2<T> sessionTimeOut(){
		this.ec = ResponseStatus.SESSION_TIME_OUT;
		return this;
	}
	
	public R2<T> refuse(String msg){
		this.ec = ResponseStatus.NO_PERMISSION;
		this.es = msg;
		return this;
	}
	
	public R2<T> needToModifyPassword(){
		this.ec = ResponseStatus.USE_DEFAULT_PASSWORD;
		return this;
	}
	
	public R2<T> needToModifyPassword(String msg){
		this.ec = ResponseStatus.USE_DEFAULT_PASSWORD;
		this.es = msg;
		return this;
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
		R2<String> r2= new R2<String>("data").success("测试！");
		System.out.println(r2);
	}
	
	public String toString(){
		return "ec="+ec+",es"+es+",data="+d;
	}
}
