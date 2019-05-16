package com.cyb.util.date;

import java.util.Date;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月16日
 */
public interface DateUtil {
	public  String timeToMilis(Date date);
	public  String timeToMilis(); 
	
	public  String timeToSec(Date date);
	public  String timeToSec();
	
	public  Long date2long14(Date date); 
	public  Long date2long14(); //yyyymmddHHmmss
	
	public  Long date2long8(Date date); 
	public  Long date2long8(); //yyyymmdd
	
	public  Long date2long10(Date date); 
	public  Long date2long10(); //yyyy-mm-dd
}
