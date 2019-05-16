package com.cyb.util.charset;
import lombok.extern.slf4j.Slf4j;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月12日
 */
@Slf4j
public abstract class AbstractJDKEncoder implements EntryCoder{

	public String encode(String name) {
		return null;
	}

	public String decode(String name) {
		return null;
	}
	public abstract String encode(String val,String from,String to);
	public abstract String decode(String val,String from,String to);
}
