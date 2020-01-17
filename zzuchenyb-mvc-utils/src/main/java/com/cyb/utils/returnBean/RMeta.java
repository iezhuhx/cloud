package com.cyb.utils.returnBean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年12月21日
 */
public class RMeta {

	private static final String OK = "ok";
	private static final String ERROR = "error";
	// 成功状态
	public static final Integer SUCCESS_CODE = 200;
	// ERROR
	public static final Integer ERROR_CODE = 202;

	private Meta meta;// 元数据
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Object data;// 相应内容

	public RMeta success() {
		this.meta = new Meta(true, OK);
		this.meta.code = SUCCESS_CODE;
		return this;
	}

	public RMeta success(Object data) {
		this.meta = new Meta(true, OK);
		this.meta.code = SUCCESS_CODE;
		this.data = data;
		return this;
	}

	public RMeta failure() {
		this.meta = new Meta(false, ERROR);
		this.meta.code = ERROR_CODE;
		return this;
	}

	public RMeta failure(String message) {
		this.meta = new Meta(false, message);
		this.meta.code = ERROR_CODE;
		return this;
	}

	public Meta getMeta() {
		return meta;
	}

	public Object getData() {
		return data;
	}

	public class Meta {

		private boolean success;
		private String message;
		private Integer code;

		public Meta(boolean success) {
			this.success = success;
		}

		public Meta(boolean success, String message) {
			this.success = success;
			this.message = message;
		}

		public boolean isSuccess() {
			return success;
		}

		public String getMessage()

		{
			return message;
		}

		public Integer getCode() {
			return code;
		}

	}
}
