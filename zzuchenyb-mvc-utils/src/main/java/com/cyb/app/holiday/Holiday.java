package com.cyb.app.holiday;
import java.util.Date;

import com.cyb.utils.date.DateSafeUtil;
import com.cyb.utils.date.DateUnsafeUtil;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月22日
 */
public class Holiday {
	Date rq;//yyyymmdd
	String type;//工作日对应结果为 0, 休息日对应结果为 1, 节假日对应的结果为 2 
	String weekday;//周一 周二 等
	final String TRADEDAY="0";
	final String WEEKDAY="1";
	final String JIJIARI="2";
	
	String[] desc = new String[]{"工作日","休息日","节假日"};
	Holiday(Date rq,String type){
		this.rq = rq;
		this.type = type;
	}
	public boolean isWeekDay(){//周六周日
		return type.equals(WEEKDAY);
	}
	public boolean isTradeDay(){
		return type.equals(TRADEDAY);//工作日
	}
	public boolean isJIJIARI(){
		return type.equals(JIJIARI);//节假日
	}
	
	public String getDesc(){
		return desc[Integer.valueOf(type)];
	}
	public String getCalEnDesc(){
		String tip="";
		if(isJIJIARI()){
			tip="HD";//节假日
		}else if(isTradeDay()){
			tip="TD";//交易日
		}else if(isWeekDay()){
			tip="WD";//周六周日
		}
		if (DateUnsafeUtil.isToday(rq)){
			tip="CD";
		}
		return tip;
	}
	
	public String isKiiikTradeDay(){
		if(isWeekDay()||isJIJIARI())
		{ 
			return "1";
		}else{ 
			return "0";
		}
	}
	public Holiday(){};
	Holiday(String row){
		this.rq = DateSafeUtil.calendar(row.split(",")[0]).getTime();
		this.type = row.split(",")[1];
	}
	public Date getRq() {
		return rq;
	}
	public void setRq(Date rq) {
		this.rq = rq;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String rq(){
		return DateUnsafeUtil.descTimeToSec(rq);
	}
	
	public String toString(){
		return DateUnsafeUtil.date2long8(rq)+"  "
				+GoseekHolidayUtils.types.get(type)+"\t"
				+DateUnsafeUtil.getChineaseNameDayOfWeek(DateUnsafeUtil.calendar(rq));
	}
}
