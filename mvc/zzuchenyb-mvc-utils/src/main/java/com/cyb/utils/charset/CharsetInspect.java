package com.cyb.utils.charset;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月19日
 */
public class CharsetInspect {
	Log log = LogFactory.getLog(CharsetInspect.class);
	static String[] charset =new String[]{"GB2312","GBK","UTF-8","UTF-16",CharsetUtils.ISO_8859_1};
    
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		uri(null);
		System.out.println("==================================");
		jdk(null);
	}
	
	 public static void jdk(String value) throws UnsupportedEncodingException {  
	    	String str = "鎬a荤粡鐞�"; 
	    	if(value!=null){
	    		str = value;
	    	}
	    	System.out.println("检查乱码源["+str+"]");
	    	//test(str);
	    	for(int i=0;i<charset.length;i++){
	    		for(int j=0;j<charset.length;j++){
	    			System.out.println(charset[i]+"--->"+charset[j]+"\t"+new String(str.getBytes(charset[i]),charset[j]));
	    		}
	    	}
	    }  
	 
	 public static void uri(String value) throws UnsupportedEncodingException {
			String chinease="总";//鎬荤粡鐞�
			if(value!=null){
				chinease = value;
			}
			for(int i=0;i<charset.length;i++){
				String enStr = URLEncoder.encode(chinease,charset[i]);
				System.out.println(chinease+" encode by "+charset[i]+" is "+enStr);
				for(int j=0;j<charset.length;j++){
					System.out.println("--"+" decode by "+
					charset[j]+" is :"+URLDecoder.decode(enStr,charset[j]));
				}
			}
		}
}
