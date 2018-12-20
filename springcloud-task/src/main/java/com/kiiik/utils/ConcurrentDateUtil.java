package com.kiiik.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
