package com.cyb.utils.charset;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlCodeUtils {
public static void main(String[] args) throws UnsupportedEncodingException {
	String[] charset =new String[]{"GB2312","GBK","UTF-8","ISO-8859-1"};
	String chinease="总";//鎬荤粡鐞�
	for(int i=0;i<charset.length;i++){
		String enStr = URLEncoder.encode(chinease,charset[i]);
		System.out.println(chinease+" encode by "+charset[i]+" is "+enStr);
		for(int j=0;j<charset.length;j++){
			System.out.println("--"+charset[i]+" encode "+
			charset[j]+" decode :"+URLDecoder.decode(enStr,charset[j]));
		}
	}
	//correctEncode();
}

public static void correctEncode() throws UnsupportedEncodingException {  
    String gbk = "鎬荤粡鐞�";  
    String iso = new String(gbk.getBytes("UTF-8"),"ISO-8859-1");  
    for (byte b : iso.getBytes("ISO-8859-1")) {  
        System.out.print(b + " ");  
    }  
    System.out.println();  
  
    //模拟UTF-8编码的网站显示  
    System.out.println(new String(iso.getBytes("ISO-8859-1"),"UTF-8"));  
}  
}
