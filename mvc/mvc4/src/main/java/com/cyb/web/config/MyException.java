package com.cyb.web.config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月21日
 */
public class MyException extends Exception {
	public MyException(String string) {
		super(string);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getLog(MyException.class);
}
