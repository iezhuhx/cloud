package com.ww.activiti.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 作者 : iechenyb<br>
 * 创建时间: 2018年5月10日
 */
@ApiModel(value = "统一返回对象", description = "统一的返回值定义方式")
public class R<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "服务器响应描述", name = "msg", example = "执行成功！")
	private String es = "ok";

	@ApiModelProperty(value = "执行状态", name = "ec", example = "0 成功 1失败 ")
	private String ec = "0";

	@ApiModelProperty(value = "数据体", name = "d", example = "任意类型数据集合")
	private T d;

	public R() {
		super();
	}

	public R(T data) {
		super();
		this.d = data;
	}

	public R(Throwable e) {
		super();
		this.ec = "1";
		this.es = e.toString();
	}

	public R<T> success() {
		this.ec = "0";
		this.es = "执行成功!";
		return this;
	}

	public R<T> success(String msg) {
		this.ec = "0";
		this.es = msg;
		return this;
	}

	public R<T> data(T data) {
		this.d = data;
		return this;
	}

	public R<T> fail() {
		this.ec = "1";
		this.es = "执行失败!";
		return this;
	}

	public R<T> fail(String msg) {
		this.ec = "1";
		this.es = msg;
		return this;
	}

	public R<T> fail(String ec, String msg) {
		this.ec = ec;
		this.es = msg;
		return this;
	}

	public R<T> fail(Throwable e) {
		this.ec = "1";
		this.es = e.toString();
		return this;
	}


	public String getEs() {
		return es;
	}

	public void setEs(String es) {
		this.es = es;
	}

	public R<T> msg(String msg) {
		this.es = msg;
		return this;
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

	@ApiModelProperty(hidden = true)
	public boolean wrong() {
		return "1".equals(this.ec);
	}

	@ApiModelProperty(hidden = true)
	public boolean ok() {
		return "0".equals(this.ec);
	}

	public boolean isWrong() {
		return wrong();
	}

	public boolean isOk() {
		return ok();
	}
}
