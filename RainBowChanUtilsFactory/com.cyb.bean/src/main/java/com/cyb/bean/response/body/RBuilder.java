package com.cyb.bean.response.body;


import com.cyb.bean.response.constant.ResponseStatus;

/**
 * 作者 : iechenyb<br>
 * 类描述: 建造者模式<br>
 * 创建时间: 2019年1月15日
 */
public class RBuilder<T> {
	private String es;

	private String ec;// 默认成功

	protected T d;

	public static void main(String[] args) {
		RBuilder<String> r = new Builder<String>()
				.success("提示信息！")
				.data("data content")
				.build();
		System.out.println(r);
	}

	public static class Builder<T> {
		private String es;
		private String ec;
		protected T d;
		public Builder<T> success(String msg) {
			es = msg;
			ec = ResponseStatus.SUCCESS;
			return this;
		}

		public Builder<T> fail(String msg) {
			es = msg;
			ec = ResponseStatus.FAIL;
			return this;
		}

		public Builder<T> data(T t) {
			d = t;
			return this;
		}

		public RBuilder<T> build() {
			return new RBuilder<T>(this);
		}
	}

	private RBuilder(Builder<T> builder) {
		this.ec = builder.ec;
		this.es = builder.es;
		this.d = builder.d;
	}

	public String toString() {
		return "ec=" + ec + ",es=" + es + ",d=" + d;
	}
}
