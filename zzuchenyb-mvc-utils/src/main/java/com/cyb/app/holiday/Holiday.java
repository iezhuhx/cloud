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
	Holiday(Date rq,String type){
		this.rq = rq;
		this.type = type;
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
	
	public String toString(){
		return DateUnsafeUtil.date2long8(rq)+"  "
				+HolidayUtils.types.get(type)+"\t"
				+DateUnsafeUtil.getChineaseNameDayOfWeek(DateUnsafeUtil.calendar(rq));
	}
}
