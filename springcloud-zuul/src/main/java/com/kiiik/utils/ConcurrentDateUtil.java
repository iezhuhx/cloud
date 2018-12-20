package com.kiiik.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年6月27日上午10:59:34
 */
public class ConcurrentDateUtil {
	private static ThreadLocal<DateFormat> formattor = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMddHHmmss");
		}
	};

	public static Date parse(String dateStr) {
		try {
			formattor.set(new SimpleDateFormat("yyyyMMddHHmmss"));
			return formattor.get().parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}finally{
			formattor.remove();
		}
	}

	public static String format(Date date) {
		formattor.set(new SimpleDateFormat("yyyyMMddHHmmss"));
		String dateString = formattor.get().format(date);
		formattor.remove();
		return dateString;
	}
	
	public static Long date2long14(Date date) {
		formattor.set(new SimpleDateFormat("yyyyMMddHHmmss"));
		String dateString = formattor.get().format(date);
		Long time =  Long.valueOf(dateString);
		formattor.remove();
		return time;
	}
	public static Long date2long14(){
		return date2long14(new Date());
	}
	
	public static Long date2long8(Date date) {
		formattor.set(new SimpleDateFormat("yyyyMMdd"));
		String dateString = formattor.get().format(date);
		Long time =  Long.valueOf(dateString);
		formattor.remove();
		return time;
	}
	public static Long date2long8(){
		return date2long14(new Date());
	}
	/** 
     * 时间戳转换成日期格式字符串 
     * @param seconds 精确到秒的字符串 
     * @param formatStr 
     * @return 
     */  
    public static Long timeStamp2Date(String seconds,String format) {  
        if(StringUtils.isEmpty(seconds)){  
            return 0l;  
        }  
        if(format == null || format.isEmpty()){
            format = "yyyyMMddHHmmss";
        }   
        Date d = new Date(Long.valueOf(seconds+"000"));
        String res = formattor.get().format(d);
        formattor.remove();
        return Long.valueOf(res);
    } 
    /** 
     * 时间戳转换成日期格式字符串 
     * @param seconds 精确到秒的字符串 
     * @param formatStr 
     * @return 
     */  
    public static Long timeStamp2Date(String seconds) {  
        return timeStamp2Date(seconds,null);
    } 
    public static Long timeStamp2Date(Long seconds) {
    	if(!StringUtils.isEmpty(seconds)){
    		return timeStamp2Date(seconds.toString(),null);
    	}else{
    		return 0l;
    	}
    } 
}
