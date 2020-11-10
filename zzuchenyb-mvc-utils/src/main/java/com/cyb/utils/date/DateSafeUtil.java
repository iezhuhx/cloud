package com.cyb.utils.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.cyb.utils.context.TimeContext;

/**
 * 
 * @author DHUser
 *
 */
public class DateSafeUtil {
	private static ThreadLocal<DateFormat> formattor = new ThreadLocal<DateFormat>();
	private static ThreadLocal<Calendar> calendarThreadLocal = new ThreadLocal<Calendar>();
	public static String format(Date date,String model) throws ParseException{
		formattor.set(new SimpleDateFormat(model));
		String res = formattor.get().format(date);
		formattor.remove();
		return res;
	}
	/**
	 * yyyy-MM-dd HH:mm:ss.SSS
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日
	 *@param date
	 *@return
	 */
	public static String timeToMilis(Date date) {
		formattor.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
		String dateString = formattor.get().format(date);
		formattor.remove();
		return dateString;
	}
	public static String timeToMilis19(Date date) {
		formattor.set(new SimpleDateFormat("yyyyMMddHHmmssSSS"));
		String dateString = formattor.get().format(date);
		formattor.remove();
		return dateString;
	}
	public static String timeToMilis() {
		return timeToMilis(new Date());
	}
	public static String timeToMilis19() {
		return timeToMilis19(new Date());
	}
	public static String timeToSec(Date date) {
		formattor.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		String dateString = formattor.get().format(date);
		formattor.remove();
		return dateString;
	}
	public static String timeToSec() {
		return timeToSec(new Date());
	}
	public static String descTimeToSec(Date date) {
		formattor.set(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒"));
		String dateString = formattor.get().format(date);
		formattor.remove();
		return dateString;
	}
	public static String descTimeToSec(){
		return descTimeToSec(new Date());
	}
	
	public static Long date2long8(Date date) {
		formattor.set(new SimpleDateFormat("yyyyMMdd"));
		String dateString = formattor.get().format(date);
		Long time =  Long.valueOf(dateString);
		formattor.remove();
		return time;
	}
	public static Long date2long8(){
		return date2long8(new Date());
	}
	public static Long date2long14(Date date) {
		formattor.set(new SimpleDateFormat("yyyyMMddHHmmss"));
		String dateString = formattor.get().format(date);
		Long time =  Long.valueOf(dateString);
		formattor.remove();
		return time;
	}
	public static Long date2long14(){
		return date2long14(new Date());
	}
    /**
     * 根据字符串日期获取日历对象
     * c.add(Calendar.MONTH, -1); 上一个月
     *作者 : iechenyb<br>
     *方法描述: 说点啥<br>
     *创建时间: 2017年7月15日
     *@param yyyymmddhhmmss
     *@return
     */
	public static Calendar calendar(String yyyymmddhhmmss) {
		calendarThreadLocal.set(Calendar.getInstance());
		Calendar cal = calendarThreadLocal.get();
		try{
			yyyymmddhhmmss = yyyymmddhhmmss
					.replaceAll("-", "")
					.replaceAll(":", "")
					.replaceAll("\\\\", "")
					.replaceAll(" ", "")
					.replace("/", "");
			if (yyyymmddhhmmss.length() != 8 
					&& yyyymmddhhmmss.length() != 14) {
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
			int year = Integer.valueOf(yyyymmddhhmmss.substring(0, 4));
			int month = Integer.valueOf(yyyymmddhhmmss.substring(4, 6));
			int day = Integer.valueOf(yyyymmddhhmmss.substring(6, 8));
			int hour = Integer.valueOf(yyyymmddhhmmss.substring(8, 10));
			int min = Integer.valueOf(yyyymmddhhmmss.substring(10, 12));
			int sec = Integer.valueOf(yyyymmddhhmmss.substring(12, 14));
			cal.set(year, month - 1, day, hour, min, sec);
		}finally{
			calendarThreadLocal.remove();
		}
		return cal;
	}
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: date转换成日历对象<br>
     *创建时间: 2017年7月15日
     *@param date
     *@return
     */
	public static Calendar calendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	/**
	 * 指定月份的最后一天
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日
	 *@param year
	 *@param month
	 *@return
	 */
	public static String getDateLastDay(String year, String month) {
		calendarThreadLocal.set(Calendar.getInstance());
		Calendar calendar = calendarThreadLocal.get();
		calendar.set(Calendar.YEAR, Integer.parseInt(year));
		calendar.set(Calendar.MONTH, Integer.parseInt(month)-1);
		calendar.set(Calendar.DAY_OF_MONTH, 1); 
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
	    DateFormat format = new SimpleDateFormat("yyyyMMdd");
	    return format.format(calendarThreadLocal.get().getTime());
	}
	/**
	 * 指定月份的第一天
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日
	 *@param year
	 *@param month
	 *@return
	 */
	public static String getDateFirstDay(String year, String month) {
		calendarThreadLocal.set(Calendar.getInstance());
		Calendar calendar = calendarThreadLocal.get();
		calendar.set(Calendar.YEAR, Integer.parseInt(year));
		calendar.set(Calendar.MONTH, Integer.parseInt(month)-1);
		calendar.set(Calendar.DAY_OF_MONTH, 1); 
		//calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
	    DateFormat format = new SimpleDateFormat("yyyyMMdd");
	    return format.format(calendarThreadLocal.get().getTime());
	}
	final static int MONTH=0;
	final static int SEASON=1;
	final static int HALFYEAR=2;
	final static int YEAR=3;
	/*
	 * 
	 * 计算固定周期的起始和结束月份
	 * type=0 月 1季度2半年 3年份
	 * year 年份
	 * value 值 
	 *  月份 0-11
	 *  季度 0-3
	 *  半年 0 1
	 *  年份 空
	 */
	public static void calDateSection(int type,int year,String value) throws Exception{
		String start;
		String end;
		switch(type){
		case MONTH:
			 start = getDateFirstDay(String.valueOf(year), value);
			 end = getDateLastDay(String.valueOf(year), value);
			break;
		case SEASON:
			   /*switch(SEASON){//1,2,3,4
			   case 0: 
				   start = getDateFirstDay(String.valueOf(year), "1");
				   end = getDateLastDay(String.valueOf(year), "3");
				   break;
			   case 1:
				   start = getDateFirstDay(String.valueOf(year), "4");
				   end = getDateLastDay(String.valueOf(year), "6");
				   break;
			   case 2:
				   start = getDateFirstDay(String.valueOf(year), "7");
				   end = getDateLastDay(String.valueOf(year), "9");
				   break;
			   case 3:
				   start = getDateFirstDay(String.valueOf(year), "10");
				   end = getDateLastDay(String.valueOf(year), "12");
				   break;
			   default :
				   throw new Exception("季度参数异常！");
			   }*/
			    int startMonth = 3*(Integer.valueOf(value)-1)+1;
			    int endMonth = 3*Integer.valueOf(value);
			    start = getDateFirstDay(String.valueOf(year), String.valueOf(startMonth));
				end = getDateLastDay(String.valueOf(year), String.valueOf(endMonth));
			 break;
		case HALFYEAR:
			 start = getDateFirstDay(String.valueOf(year), value.equals("1") ? "1":"7");
			 end = getDateLastDay(String.valueOf(year), value.equals("1") ? "6":"12");
			 break;
		case YEAR:
			 start = getDateFirstDay(String.valueOf(year), "1");
			 end = getDateLastDay(String.valueOf(year), "12");
			 break;
		default:
			start = "";
			end = "";
		}
		
		System.out.println(start+"->"+end);
		
	}
	public static void main(String[] args) throws Exception {
		System.out.println(format(new Date(),"yyyMMddHHmmss"));
		TimeContext.recordTimeStart();
		/*for(int i=0;i<=11;i++){
			System.out.println(getDateFirstDay("2018",String.valueOf(i)));
		}
		System.out.println("========");
		for(int i=0;i<=11;i++){
			System.out.println(getDateLastDay("2018",String.valueOf(i)));
		}*/
		//月份测试
		for(int i=1;i<=12;i++){
			calDateSection(MONTH,2018,String.valueOf(i));
		}
		System.out.println("----------");
		//季度测试
		for(int i=1;i<=4;i++){
			calDateSection(SEASON,2018,String.valueOf(i));
		}
		System.out.println("----------");
		//半年
		for(int i=1;i<=2;i++){
			calDateSection(HALFYEAR,2018,String.valueOf(i));
		}
		System.out.println("----------");
		//年
		for(int i=1;i<=1;i++){
			calDateSection(YEAR,2018,"");
		}
		System.out.println("----------");
		lastCycleDate("20181231");
		TimeContext.calExecuteTime();
	}
	
	//type 1 日 2周 3月 4季度 5半年 6年
	public static void lastCycleDate(String dayStr){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");// HH:mm:ss
        Calendar c = calendar(dayStr);
        //过去七天
        c.add(Calendar.DATE, - 7);
        Date d = c.getTime();
        String day = format.format(d);
        System.out.println("过去七天："+day);
        //过去一月
        c = calendar(dayStr);
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        System.out.println("过去一个月："+mon);
        //过去三个月
        c = calendar(dayStr);
        c.add(Calendar.MONTH, -3);
        Date m3 = c.getTime();
        String mon3 = format.format(m3);
        System.out.println("过去三个月："+mon3);
        //过去半年
        c = calendar(dayStr);
        c.add(Calendar.MONTH, -6);
        Date m4 = c.getTime();
        String mon4 = format.format(m4);
        System.out.println("过去半年："+mon4);
        //过去一年
        c = calendar(dayStr);
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
        String year = format.format(y);
        System.out.println("过去一年："+year);
	}
}
