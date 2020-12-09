package com.cyb.app.holiday.main;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.app.h2.H2Common2DbUtils;
import com.cyb.app.h2.usecase.H2Starter;
import com.cyb.app.holiday.Holiday;
import com.cyb.app.holiday.HolidaySQL;
import com.cyb.app.holiday.imp.TimorHolidayStrategy;
import com.cyb.app.holiday.util.HolidayH2DbUtils;
import com.cyb.app.holiday.util.HolidayUtils;
import com.cyb.utils.date.DateUnsafeUtil;
/**
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年12月9日 上午10:20:59
 */
public class HolidayOperator {
	Log log = LogFactory.getLog(HolidayOperator.class);
	public static void main(String[] args) throws Exception {
		H2Starter.startTcpAndWebServer();//开启h2相关服务
		//1 抓取节假日+写入文件
		//GoseekHolidayUtils.initHoliday(2021);//不可用
		boolean getCal = false;
		if(getCal){
			HolidayUtils.initHoliday(2021+"",new TimorHolidayStrategy());
		}
		//2落库
		H2Common2DbUtils.createTable(HolidaySQL.tableName, HolidaySQL.createSQL);
		boolean initHolidayDB = false;
		if(initHolidayDB){
			HolidayH2DbUtils.insertHolidayFromFile("2020");
		}
		//3查询校验
		Holiday day = HolidayH2DbUtils.getSomeDay(new Date());
		System.out.println("当日："+day);
		String preTradeDay= HolidayH2DbUtils.preTradeDay();
		System.out.println("上1个交易日："+preTradeDay);
		String nextTradeDay= HolidayH2DbUtils.nextTradeDay(preTradeDay);
		System.out.println("下1个交易日："+nextTradeDay);
		
		DateUnsafeUtil.showMonthCal();
		System.out.println("------------------------------------------");
		DateUnsafeUtil.showMonthCal(11);
		System.out.println("------------------------------------------");
		DateUnsafeUtil.showYearMonthCal(2021,2);
	}
}
