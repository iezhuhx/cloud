package com.cyb.app.holiday.util;

import java.io.IOException;

import com.cyb.app.holiday.imp.TimorHolidayStrategy;

/**
 * 自定义节假日，然后生成对应的节假日信息。
 * @author Administrator
 * http://timor.tech/api/holiday cod=0 代表成功  
 * 2013-04-27#{"code":0,"type":{"type":3,"name":"劳动节前调休","week":6},"holiday":{"holiday":false,"after":false,"name":"劳动节前调休","wage":1,"target":"劳动节","date":"2013-04-27"}}
2013-04-28#{"code":0,"type":{"type":3,"name":"劳动节前调休","week":7},"holiday":{"holiday":false,"after":false,"name":"劳动节前调休","wage":1,"target":"劳动节","date":"2013-04-28"}}
2013-04-29#{"code":0,"type":{"type":2,"name":"劳动节","week":1},"holiday":{"holiday":true,"name":"劳动节","wage":3,"date":"2013-04-29"}}
2013-04-30#{"code":0,"type":{"type":2,"name":"劳动节","week":2},"holiday":{"holiday":true,"name":"劳动节","wage":2,"date":"2013-04-30"}}
2013-05-01#{"code":0,"type":{"type":2,"name":"劳动节","week":3},"holiday":{"holiday":true,"name":"劳动节","wage":2,"date":"2013-05-01"}}
2013-05-02#{"code":0,"type":{"type":0,"name":"周四","week":4},"holiday":null}
2013-05-03#{"code":0,"type":{"type":0,"name":"周五","week":5},"holiday":null}
 */
public class TimorHolidayUtils {
	//date yyyy-mm-dd
	public static void main(String[] args) {
		try {//20201231
			// HolidayUtils.initHoliday("20200101","20201231", new TimorHolidayStrategy());
			for(int i=2013;i>=2000;i--){
				HolidayUtils.initHoliday(i+"",new TimorHolidayStrategy());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
