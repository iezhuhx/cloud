package com.cyb.web.bean;

import java.io.Serializable;

/**
 * 作者 : iechenyb<br>
 * 类描述: http://blog.didispace.com/cxy-wsm-zml-2/<br>
 * 创建时间: 2018年1月10日
 */
public class ResultBean<T> extends BaseResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private String msg = "";
	public boolean cacheable = false;
	
	private String code = SUCCESS;
	
	protected  T data;

	public ResultBean() {
		super();
		/* Type type = ((ParameterizedType)data.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	     System.out.println(type);
	     
	    ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();//获取当前new对象的泛型的父类类型
	 	Class clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
	 	System.out.println("clazz ==>> "+clazz);*/
		 //this.data=(T)"";
	}

	public ResultBean(T data) {
		super();
		this.data = data;
	}

	public ResultBean(Throwable e) {
		super();
		this.msg = e.toString();
		this.code = FAIL;
	}
	@SuppressWarnings("rawtypes")
	public ResultBean success() {
		this.code = SUCCESS;
		return this;
	}
	@SuppressWarnings("rawtypes")
	public ResultBean success(String msg) {
		this.code = SUCCESS;
		this.msg = msg;
		return this;
	}
	@SuppressWarnings("rawtypes")
	public ResultBean data(T data) {
		if(data==null){
			
			//this.data  = "";如果data为基本类型 
		}
		this.data = data;
		return this;
	}

	@SuppressWarnings("rawtypes")
	public ResultBean fail() {
		this.code = FAIL;
		return this;
	}
	@SuppressWarnings("rawtypes")
	public ResultBean fail(String msg) {
		this.code = FAIL;
		this.msg = msg;
		return this;
	}

	@SuppressWarnings("rawtypes")
	public ResultBean fail(Throwable e) {
		this.code = FAIL;
		this.msg = e.toString();
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	public  ResultBean<T> msg(String msg) {
		this.msg = msg;
		return this;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}


	public static String getSuccess() {
		return SUCCESS;
	}

	public static String getFail() {
		return FAIL;
	}

	public static String getNoPermission() {
		return NO_PERMISSION;
	}
	
	public ResultBean<T> refuse(){
		this.code = NO_PERMISSION;
		return this;
	}
	public ResultBean<T> refuse(String msg){
		this.code = NO_PERMISSION;
		this.msg = msg;
		return this;
	}

	public boolean isCacheable() {
		return cacheable;
	}

	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}
	
	public ResultBean<T> cacheable(boolean cacheable) {
		this.cacheable = cacheable;
		return this;
	}

	
	public ResultBean<T> cacheable() {
		this.cacheable = true;
		return this;
	}
}
