package com.cyb.utils.charset;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月12日
 */
public abstract class AbstractJDKEncoder implements EntryCoder{
	Log log = LogFactory.getLog(AbstractJDKEncoder.class);

	public String encode(String name) {
		return null;
	}

	public String decode(String name) {
		return null;
	}
	public abstract String encode(String val,String from,String to);
	public abstract String decode(String val,String from,String to);
}
