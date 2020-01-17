package com.cyb.utils.context;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.util.StringUtils;

import com.cyb.utils.date.DateUnsafeUtil;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月19日
 */
public class TimeContext {
	
	private static ThreadLocal<Long> timeHolder = new ThreadLocal<Long>();
	
	public static void setTime(long time){
		timeHolder.set(time);
	}
	
	public static long getTime(){
		if(StringUtils.isEmpty(timeHolder.get())){
			return 0L;
		}
		long time = timeHolder.get();
		timeHolder.remove();
		return time;
	}
	
	static ThreadLocal<Long> start = new ThreadLocal<Long>();
	static SimpleDateFormat formatter = new SimpleDateFormat("mm分ss秒SSS毫秒",Locale.ENGLISH);//初始化Formatter的转换格式。
	
	public static  String formatTime(long millis){
		formatter.setTimeZone(TimeZone.getTimeZone("GMT_ID"));
		String hms = formatter.format(millis);
		return hms;
	}
	public static void recordTimeStart(){
		start.set(System.currentTimeMillis());
	}
	
	public static void start(){
		recordTimeStart();
	}
	
	public static void begin(){
		recordTimeStart();
	}
	
	public static long calExecuteTime(){
		long millis = System.currentTimeMillis() -  start.get();
		System.err.println("用时("+millis+"毫秒)："+formatTime(millis));
		start.remove();
		return millis;
	}
	public static long calExecuteTime(String msg){
		long millis = System.currentTimeMillis() -  start.get();
		String rest = DateUnsafeUtil.timeToMilis()+" "+msg+"用时("+millis+"毫秒)："+formatTime(millis)+"\n";
		System.err.println(rest);
		return millis;
	}
	
}
