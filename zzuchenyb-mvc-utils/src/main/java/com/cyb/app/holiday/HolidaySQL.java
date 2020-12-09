package com.cyb.app.holiday;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月23日
 */
public class HolidaySQL {
	Log log = LogFactory.getLog(HolidaySQL.class);
	public static String tableName="holiday";
	public static String createSQL="create table holiday(rq varchar,type varchar)";
	public static String insertSQL="insert into holiday values('${rq}','${type}')";
	public static String preTradeSQL="select max(rq) rq from holiday where rq<${rq} and type=0";
	public static String nextTradeSQL="select min(rq) rq from holiday where rq>${rq} and type=0";
	public static String tradeSQL="select type from holiday where rq=${rq}";
}
