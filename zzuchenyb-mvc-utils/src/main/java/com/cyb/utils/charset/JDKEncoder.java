package com.cyb.utils.charset;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月12日
 */
public class JDKEncoder extends AbstractJDKEncoder{
	Log log = LogFactory.getLog(JDKEncoder.class);

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
