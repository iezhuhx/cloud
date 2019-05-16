package com.cyb.bean.response.body;

import com.cyb.bean.response.constant.ResponseStatus;

import java.io.Serializable;


/**
 * 作者 : iechenyb<br>
 * 类描述:返回实体信息<br>
 * 创建时间: 2018年1月10日
 */
public class RThis<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String es;
	
	private String ec ;//默认成功
	
	protected  T d;

	public RThis() {
		ec = ResponseStatus.SUCCESS;
	}

	public RThis(T d) {
		ec = ResponseStatus.SUCCESS;
		this.d = d;
	}

	public RThis(Throwable e) {
		super();
		this.es = e.toString();
		this.ec = ResponseStatus.FAIL;
	}
	public RThis<T> success() {
		this.ec = ResponseStatus.SUCCESS;
		return this;
	}
	public RThis<T> success(String msg) {
		this.ec = ResponseStatus.SUCCESS;
		this.es = msg;
		return this;
	}
	public RThis<T> data(T data) {
		this.d = data;
		return this;
	}

	public RThis<T> fail() {
		this.ec = ResponseStatus.FAIL;
		return this;
	}
	public RThis<T> fail(String msg) {
		this.ec = ResponseStatus.FAIL;
		this.es = msg;
		return this;
	}

	public RThis<T> fail(Throwable e) {
		this.ec = ResponseStatus.FAIL;
		this.es = e.toString();
		return this;
	}
	
	public  RThis<T> msg(String msg) {
		this.es = msg;
		return this;
	}

	public static String getNoPermission() {
		return ResponseStatus.NO_PERMISSION;
	}
	
	public RThis<T> refuse(){
		this.ec = ResponseStatus.NO_PERMISSION;
		return this;
	}
	public RThis<T> sessionTimeOut(String msg){
		this.ec = ResponseStatus.SESSION_TIME_OUT;
		this.es=msg;
		return this;
	}
	public RThis<T> sessionTimeOut(){
		this.ec = ResponseStatus.SESSION_TIME_OUT;
		return this;
	}
	
	public RThis<T> refuse(String msg){
		this.ec = ResponseStatus.NO_PERMISSION;
		this.es = msg;
		return this;
	}
	
	public RThis<T> needToModifyPassword(){
		this.ec = ResponseStatus.USE_DEFAULT_PASSWORD;
		return this;
	}
	
	public RThis<T> needToModifyPassword(String msg){
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
		RThis<String> r2= new RThis<String>("data")
				.success("测试！");
		System.out.println(r2);
	}
	
	public String toString(){
		return "ec="+ec+",es"+es+",data="+d;
	}
}
