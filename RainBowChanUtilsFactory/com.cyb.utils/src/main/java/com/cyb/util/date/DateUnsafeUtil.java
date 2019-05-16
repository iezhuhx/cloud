package com.cyb.util.date;

import com.cyb.util.string.StringUtil;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author DHUser
 *
 */
public class DateUnsafeUtil {
	private static SimpleDateFormat formatter = null;

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 说点啥<br>
	 * 创建时间: 2017年7月15日hj12
	 * 
	 * @param date
	 * @param model
	 *            yyyy-MM-dd HH:mm:ss.SSS
	 * @return
	 */
	public static String format(Date date, String model) {
		formatter = new SimpleDateFormat(model);
		String dateString = formatter.format(date);
		return dateString;
	}

	public static String format(String date, String model) {
		formatter = new SimpleDateFormat(model);
		String dateString = formatter.format(calendar(date).getTime());
		return dateString;
	}
	static String weekNum[]={"7","1","2","3","4","5","6"};//字符串数组
	static String weekName[]={"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
	public static String dayOfWeek(){
		return dayOfWeek(Calendar.getInstance());
	}
	public static String getChineaseNameDayOfWeek(){
		return getChineaseNameDayOfWeek(Calendar.getInstance());
	}
	public static String dayOfWeek(Calendar cal){
		int day=cal.get(Calendar.DAY_OF_WEEK);//获取时间
		return weekNum[day-1];
	}
	public static String getChineaseNameDayOfWeek(Calendar cal){
		int day=cal.get(Calendar.DAY_OF_WEEK);//获取时间
		return weekName[day-1];
	}
	public static String dayOfWeek(String ywrq){
		return dayOfWeek(ywrq);
	}
	public static String getChineaseNameDayOfWeek(String ywrq){
		return getChineaseNameDayOfWeek(ywrq);
	}
	/**
	 * Calendar.MONDAY,Calendar.TUESDAY,Calendar.THURSDAY..
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日
	 *@param day_of_week
	 *@return
	 */
	public static boolean isSomeWeekDay(int day_of_week){
		return isSomeWeekDay(Calendar.getInstance(),day_of_week);
	}
	
	public static boolean isSomeWeekDay(Calendar cal,int day_of_week){
		boolean flag = false;
		int day=cal.get(Calendar.DAY_OF_WEEK);//获取时间
		if(day == day_of_week){
			flag = true;
		}
		return flag;
	}
	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 说点啥<br>
	 * 创建时间: 2017年7月15日hj12
	 * 
	 * @param date
	 *            yyyy-MM-dd HH:mm:ss.SSS
	 * @return
	 */
	public static String timeToMilis(Date date) {
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateString = formatter.format(date);
		return dateString;
	}

	public static String timeToMilis() {
		return timeToMilis(new Date());
	}

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 说点啥<br>
	 * 创建时间: 2017年7月15日hj12
	 * 
	 * @param date
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String timeToSec(Date date) {
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}

	public static String timeToSec() {
		return timeToSec(new Date());
	}

	public static String descTimeToSec(Date date) {
		formatter = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		String dateString = formatter.format(date);
		return dateString;
	}

	public static String descTimeToSec() {
		return descTimeToSec(new Date());
	}

	public static Long date2long10(Date date) {
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		return Long.valueOf(dateString);
	}

	public static Long date2long10() {
		return Long.valueOf(date2long10(new Date()));
	}

	public static Long date2long8(Date date) {
		formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(date);
		return Long.valueOf(dateString);
	}

	public static Long date2long8() {
		return Long.valueOf(date2long8(new Date()));
	}

	public static Long date2long14(Date date) {
		formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(date);
		return Long.valueOf(dateString);
	}

	public static String date2long14() {
		return date2long14(new Date()).toString();
	}

	// date 20150202 -> 2015-02-02
	public static String date2long10(String date) {
		if (date.length() != 8 && date.length() != 14) {
			try {
				throw new Exception("参数必须为8位或者14位数字");
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}
		if (date.length() == 8) {
			date = date + "000000";
		}
		Date d = calendar(date).getTime();
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(d);
		return dateString;
	}
	public static Calendar calendar(){
		return calendar(new Date());
	}
	public static Calendar calendar(String yyyymmddhhmmss) {
		yyyymmddhhmmss = yyyymmddhhmmss
				.replaceAll("-", "")
				.replaceAll(":", "")
				.replaceAll("\\\\", "")
				.replaceAll(" ", "")
				.replace("/", "");
		if (yyyymmddhhmmss.length() != 8 && yyyymmddhhmmss.length() != 14) {
			try {
				throw new Exception("参数必须为8位或者12位数字");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		if (yyyymmddhhmmss.length() == 8) {
			yyyymmddhhmmss = yyyymmddhhmmss + "000000";
		}
		Calendar cal = Calendar.getInstance();
		int year = Integer.valueOf(yyyymmddhhmmss.substring(0, 4));
		int month = Integer.valueOf(yyyymmddhhmmss.substring(4, 6));
		int day = Integer.valueOf(yyyymmddhhmmss.substring(6, 8));
		int hour = Integer.valueOf(yyyymmddhhmmss.substring(8, 10));
		int min = Integer.valueOf(yyyymmddhhmmss.substring(10, 12));
		int sec = Integer.valueOf(yyyymmddhhmmss.substring(12, 14));
		cal.set(year, month - 1, day, hour, min, sec);
		return cal;
	}

	public static Calendar calendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static boolean between(String shhmmss, String ehhmmss) {
		String day = DateUnsafeUtil.date2long8(new Date()).toString(); // 20150102
		// 09:30-11:30 13:00-15:00
		Calendar mornings = DateUnsafeUtil.calendar(day + shhmmss);
		Calendar morninge = DateUnsafeUtil.calendar(day + ehhmmss);
		Calendar curDate = DateUnsafeUtil.calendar(new Date());
		if ((curDate.after(mornings) && curDate.before(morninge))) {
			return true;
		} else {
			return false;
		}
	}

	public static Date preDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}
	public static Date preDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}
	
	public static Date preDate(Date date,int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 
	 *负数为，不包括今天，往前推算n天<br>
	 *正数时，不包括今天，往后推算n天<br>
	 *创建时间: 2017年7月15日
	 *@param days
	 *@return
	 */
	public static Date preDate(int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}
	
	public static int getWeekOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	public static int getMaxWeekNumOfYear(int year) {
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
		return getWeekOfYear(c.getTime());
	}

	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	public static Date getLasttDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Monday
		return c.getTime();
	}

	public static int getWeekNumber(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(7);
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	public static Date nextSomeDay(Date date, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, i);
		return c.getTime();
	}

	public static String str8to10(String yyyymmdd, String split) {
		return yyyymmdd.substring(0, 4) + split + yyyymmdd.substring(4, 6) + split + yyyymmdd.substring(6, 8);
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
	
	public static String curYearStartDay(){
		return date2long8().toString().substring(0, 4)+"0101";
	}
	public static String curYearEndDay(){
		return date2long8().toString().substring(0, 4)+"1231";
	}

	/**
	 * 字符串的日期格式的计算
	 */
	public static int daysBetween(String start, String end) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		long between_days = 0;
		try {
			cal.setTime(sdf.parse(start));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(end));
			long time2 = cal.getTimeInMillis();
			between_days = (time2 - time1) / (1000 * 3600 * 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Integer.parseInt(String.valueOf(between_days));
	}

	public static String showTime(long s, long e) {
		long ms = e - s;// 毫秒数
		// 初始化Formatter的转换格式。
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
		String hms = formatter.format(ms);
		String hmstmp = hms.substring(0, hms.length() - 4);
		StringTokenizer st = new StringTokenizer(hmstmp, ":");
		while (st.hasMoreElements()) {
			if (!st.nextToken().equals("00")) {
				break;
			} else {
				hms = hms.substring(3, hms.length());
			}
		}
		return hms;
	}



	/**
	 * 时间戳转换成日期格式字符串
	 * 
	 * @param seconds
	 *            精确到秒的字符串
	 * @return
	 */
	public static String timeStamp2Date(String seconds) {
		return timeStamp2Date(seconds, "HH:mm:ss");
	}

	public static String timeStamp2Date(String seconds, String format) {
		if (seconds == null || StringUtil.isEmpty(seconds) || seconds.equals("null")) {
			return "";
		}
		if (format == null || StringUtil.isEmpty(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds + "000")));
	}

	/**
	 * 日期格式字符串转换成时间戳
	 * 
	 * @param date_str
	 *            字符串日期
	 * @param format
	 *            如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String date2TimeStamp(String date_str, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return String.valueOf(sdf.parse(date_str).getTime() / 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 取得当前时间戳（精确到秒）
	 * 
	 * @return
	 */
	public static String timeStamp() {
		long time = System.currentTimeMillis();
		String t = String.valueOf(time / 1000);
		return t;
	}
}
