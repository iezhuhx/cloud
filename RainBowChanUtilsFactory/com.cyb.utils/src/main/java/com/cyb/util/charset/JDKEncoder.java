package com.cyb.util.charset;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月12日
 */
@Slf4j
public class JDKEncoder extends AbstractJDKEncoder{

	@Override
	public String encode(String val) {
		 try {
			 return encode(val,defaultGbk,defaultUTF8 );
		} catch (Exception e) {
			e.printStackTrace();
			return val;
		}
	}

	@Override
	public String decode(String val)  {
		 try {
			return decode(val, defaultGbk,defaultUTF8);
		} catch (Exception e) {
			e.printStackTrace();
			return val;
		}
	}
	
	@Override
	public String encode(String val,String from,String to) {
		 try {
			return new String(val.toString().getBytes(from), to);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return val;
		}
	}
	@Override
	public String decode(String val,String from,String to)  {
		 try {
			return new String(val.toString().getBytes(from), to);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return val;
		}
	}
	
}
