package com.cyb.util.charset;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月12日
 */
@Slf4j
public class UriEncoder implements EntryCoder{

	public String encode(String val) {
		 try {
			return URLEncoder.encode(val, defaultUTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return val;
		}
	}

	public String decode(String val)  {
		 try {
			return URLDecoder.decode(val, defaultUTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return val;
		}
	}
	
	public String encode(String val,String charset) {
		 try {
			return URLEncoder.encode(val, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return val;
		}
	}

	public String decode(String val,String charset)  {
		 try {
			return URLDecoder.decode(val, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return val;
		}
	}
}
