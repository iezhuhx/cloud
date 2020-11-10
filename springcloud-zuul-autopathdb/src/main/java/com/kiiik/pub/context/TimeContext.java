package com.kiiik.pub.context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月19日
 */
public class TimeContext {
	Log log = LogFactory.getLog(TimeContext.class);
	private static ThreadLocal<Long> timeHolder = new ThreadLocal<Long>();
	public static void setTime(long time){
		timeHolder.set(time);
	}
	
	public static long getTime(){
		if(StringUtils.isEmpty(timeHolder.get())){
			return 0L;
		}
		return timeHolder.get();
	}
	
}
