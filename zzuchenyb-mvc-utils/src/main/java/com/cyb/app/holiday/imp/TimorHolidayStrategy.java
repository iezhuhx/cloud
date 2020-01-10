package com.cyb.app.holiday.imp;

import com.alibaba.fastjson.JSONObject;
import com.cyb.utils.http.MyHttpClient;
import com.cyb.utils.text.ELUtils;

public class TimorHolidayStrategy implements HolidayStrategy
{
	public static String holidayUrl = "http://timor.tech/api/holiday/info/${date}";
	/**
	 * yyyy-mm-dd
	 * {"code":0,"type":{"type":0,"name":"周五","week":5},"holiday":null}
	 * {"code":0,"type":{"type":1,"name":"周六","week":6},"holiday":null}
	 * {"code":0,"type":{"type":2,"name":"元旦","week":3},"holiday":{"holiday":true,"name":"元旦","wage":3,"date":"2020-01-01"}}
	 */
	@Override
	public String holidayType(String yyyymmdd) {
		String req = ELUtils.el(holidayUrl,"date",yyyymmdd);
		String holidayData=MyHttpClient.doGet(req);
		System.out.println(yyyymmdd+"#"+holidayData);
		JSONObject obj = JSONObject.parseObject(holidayData);
		String type = obj.getJSONObject("type").getString("type");
		return type;
	}

	
	public static void main(String[] args) {
		TimorHolidayStrategy a = new TimorHolidayStrategy();
		String rs = a.holidayType("2020-01-01");
		System.out.println(rs);
		rs = a.holidayType("2020-01-03");
		System.out.println(rs);
		rs = a.holidayType("2020-01-04");
		System.out.println(rs);
	}
}
