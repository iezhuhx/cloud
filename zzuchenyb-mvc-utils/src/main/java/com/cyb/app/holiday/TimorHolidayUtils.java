package com.cyb.app.holiday;

import java.io.IOException;

import com.cyb.app.holiday.imp.TimorHolidayStrategy;

/**
 * 自定义节假日，然后生成对应的节假日信息。
 * @author Administrator
 * http://timor.tech/api/holiday
 */
public class TimorHolidayUtils {
	//date yyyy-mm-dd
	public static void main(String[] args) {
		try {//20201231
			HolidayUtils.initHoliday("20200101",
					"20201231", new TimorHolidayStrategy());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
