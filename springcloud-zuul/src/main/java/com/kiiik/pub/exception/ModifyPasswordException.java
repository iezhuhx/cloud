package com.kiiik.pub.exception;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 当密码为默认密码时，提示前端重新更新密码<br>
 *创建时间: 2018年5月11日
 */
public class ModifyPasswordException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getLog(ModifyPasswordException.class);
	public ModifyPasswordException() {
		super();
	}
	public ModifyPasswordException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
	public ModifyPasswordException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}
	public ModifyPasswordException(String arg0) {
		super(arg0);
		
	}
	public ModifyPasswordException(Throwable arg0) {
		super(arg0);
	}
	
}
