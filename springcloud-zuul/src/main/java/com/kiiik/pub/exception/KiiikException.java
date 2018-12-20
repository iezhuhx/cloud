package com.kiiik.pub.exception;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月11日
 */
public class KiiikException extends RuntimeException {
	public Integer status;//异常状态
	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getLog(KiiikException.class);
	public KiiikException() {
		super();
	}
	public KiiikException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
	public KiiikException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}
	public KiiikException(String arg0) {
		super(arg0);
	}
	public KiiikException(String arg0,Integer status) {
		super(arg0);
		this.status = status;
	}
	public KiiikException(Throwable arg0) {
		super(arg0);
	}
	
}
