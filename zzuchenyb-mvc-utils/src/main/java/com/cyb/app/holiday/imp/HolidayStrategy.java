package com.cyb.app.holiday.imp;

/**
 * 将不同的网站获取的节日信息统一输出。
 * @author zzuch
 *
 */
public interface HolidayStrategy {
  String holidayType(String yyyymmdd);//返回节假日类型
}
