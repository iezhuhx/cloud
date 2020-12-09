package com.cyb.app.holiday.util;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.cyb.app.holiday.Holiday;
import com.cyb.app.holiday.HolidayStatus;
import com.cyb.app.reptile.ProxyRandomUtils;
import com.cyb.utils.date.DateUnsafeUtil;
import com.cyb.utils.file.FileUtils;
import com.cyb.utils.http.MyHttpClient;
import com.cyb.utils.text.ELUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月22日
 */
/*
 * {"code":10000,"data":1}
1、接口地址：http://api.goseek.cn/Tools/holiday?date=数字日期，支持https协议。 
2、返回数据：工作日对应结果为 0, 休息日对应结果为 1, 节假日对应的结果为 2 
3、节假日数据说明：本接口包含2017年起的中国法定节假日数据，数据来源国务院发布的公告，每年更新1次，确保数据最新 
*/
public class GoseekHolidayUtils {
	static Log log = LogFactory.getLog(GoseekHolidayUtils.class);
	public static Map<String,String> types = new HashMap<>();
	public static String HOLIDAY_GOGZUORI="0";//工作日
	public static String HOLIDAY_XIUXIRI="1";//休息日
	public static String HOLIDAY_JIEJIARI="2";//节假日
	static{
		types = new HashMap<>();
		types.put(HOLIDAY_GOGZUORI, "工作日");
		types.put(HOLIDAY_XIUXIRI, "休息日");
		types.put(HOLIDAY_JIEJIARI, "节假日");
	}
	public static String rqs="D:\\data\\calendar\\${year}.txt";
	static String holidayUrl = "http://api.goseek.cn/Tools/holiday?date=${date}";
	//抓取当年的节假日2019
	/*public static void main(String[] args) throws IOException {
		//initHoliday();
		//initHoliday("20200101","20201231");
		//System.out.println(lastTradeDay());
	}*/
	//初始化指定年节假日
	public static void initHoliday(int year) throws IOException{
		 initHoliday(year+"0101",year+"1231");
	}
	
	//初始化当年节假日
	public static void initHoliday() throws IOException{
		 initHoliday(DateUnsafeUtil.curYearStartDay(),DateUnsafeUtil.curYearEndDay());
	}
	//初始化任意区间节假日
	public static void initHoliday(String start,String end) throws IOException{
		//计算两个日期之间的差值
		int days = DateUnsafeUtil.daysBetween(start, end);
		Date startDate = DateUnsafeUtil.calendar(start).getTime();
		rqs = ELUtils.el(rqs, "year",start.substring(0, 4));
		for(int i=0;i<=days;i++){
			Date date = DateUnsafeUtil.preDate(startDate,i);
			String req = ELUtils.el(holidayUrl, 
					"date"
					,DateUnsafeUtil.date2long8(date).toString());
			String holidayData=MyHttpClient.doGet(req);
			HolidayStatus status = JSON.parseObject(holidayData,
					HolidayStatus.class);
			String row = DateUnsafeUtil.date2long8(date)+","+status.getData()+"\n";
			FileUtils.append(rqs, row);
			//log.info(new Holiday(date, status.getData()));
			String isMarketDay = "0";//工作日
			if(Integer.valueOf(status.getData()).intValue()==0){
				isMarketDay="1";//节假日
			}
			
			String sql = "insert into tb_calendar(day,is_market_day) values("+DateUnsafeUtil.date2long8(date).toString()+","+isMarketDay+");";
			//String sql = "insert into tb_work_day (DAY, IS_WORK_DAY) values (to_date('"+DateUnsafeUtil.format(date, "yyyy-mm-dd")+"', 'yyyy-mm-dd'), "+isMarketDay+");";
			System.out.println(sql);
		}
	}
	public static Holiday holiday(Date date){
		String req = ELUtils.el(holidayUrl,"date"
				,DateUnsafeUtil.date2long8(date).toString());
		String holidayData=MyHttpClient.doGet(req,ProxyRandomUtils.findUseableProxyRandom());
		HolidayStatus status = JSON.parseObject(holidayData,
				HolidayStatus.class);
		return new Holiday(date, status.getData());
	}
	
	public static Date lastTradeDay(){
		return lastTradeDay(new Date());	
	}
	public static Date lastTradeDay(Date date){
		for(int i=1;i<=360;i++){
			Date d = DateUnsafeUtil.preDate(-1*i);
			if(holiday(d).getType().equals(HOLIDAY_GOGZUORI)){
				return d;
			}
		}
		return null;
	}
	public static Holiday holiday(){
		return holiday(new Date());
	}
	public static Holiday holiday(String yyyymmdd){
		return holiday(DateUnsafeUtil.calendar(yyyymmdd).getTime());
	}
}

