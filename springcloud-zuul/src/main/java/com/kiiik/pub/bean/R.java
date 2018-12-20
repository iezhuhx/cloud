package com.kiiik.pub.bean;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 作者 : iechenyb<br>
 * 类描述:返回实体信息<br>
 * 创建时间: 2018年1月10日
 */
@ApiModel(value="统一返回对象",description="统一的返回值定义方式")
public class R<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="执行结果",name="msg",example="执行成功！")
	private String es = "";
	
	@ApiModelProperty(value="执行状态",name="code",example="0 失败 1成功 ")
	private String ec = ResponseStatus.SUCCESS;//默认成功
	
	@ApiModelProperty(value="数据体",name="data",example="任意类型数据集合")
	protected  T d;

	public R() {
		super();
	}

	public R(T d) {
		super();
		this.d = d;
	}
	
   /*public ResultBean(PageData<T> d) {
		super();
		this.d = d;
	}*/

	public R(Throwable e) {
		super();
		this.es = e.toString();
		this.ec = ResponseStatus.FAIL;
	}
	public R<T> success() {
		this.ec = ResponseStatus.SUCCESS;
		return this;
	}
	public R<T> success(String msg) {
		this.ec = ResponseStatus.SUCCESS;
		this.es = msg;
		return this;
	}
	public R<T> data(T data) {
		this.d = data;
		return this;
	}

	public R<T> fail() {
		this.ec = ResponseStatus.FAIL;
		return this;
	}
	public R<T> fail(String msg) {
		this.ec = ResponseStatus.FAIL;
		this.es = msg;
		return this;
	}

	public R<T> fail(Throwable e) {
		this.ec = ResponseStatus.FAIL;
		this.es = e.toString();
		
		return this;
	}
	
	public  R<T> msg(String msg) {
		this.es = msg;
		return this;
	}

	public static String getNoPermission() {
		return ResponseStatus.NO_PERMISSION;
	}
	
	public R<T> refuse(){
		this.ec = ResponseStatus.NO_PERMISSION;
		return this;
	}
	public R<T> sessionTimeOut(String msg){
		this.ec = ResponseStatus.SESSION_TIME_OUT;
		this.es=msg;
		return this;
	}
	public R<T> sessionTimeOut(){
		this.ec = ResponseStatus.SESSION_TIME_OUT;
		return this;
	}
	
	public R<T> refuse(String msg){
		this.ec = ResponseStatus.NO_PERMISSION;
		this.es = msg;
		return this;
	}
	
	public R<T> needToModifyPassword(){
		this.ec = ResponseStatus.USE_DEFAULT_PASSWORD;
		return this;
	}
	
	public R<T> needToModifyPassword(String msg){
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
	
}
