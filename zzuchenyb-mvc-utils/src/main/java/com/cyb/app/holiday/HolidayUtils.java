package com.cyb.app.holiday;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cyb.app.holiday.imp.GenerSqlStrategy;
import com.cyb.app.holiday.imp.HolidayStrategy;
import com.cyb.app.holiday.imp.KiiikWorkDayStrategy;
import com.cyb.utils.date.DateUnsafeUtil;
import com.cyb.utils.file.FileUtils;
/**
 * 根据不同的策略，生产指定区间的日历
 * @author Administrator
 *
 */
public class HolidayUtils {
	static String calTxt="D:\\data\\calendar\\rqs.txt";
	
	//获取以当前年份为文件名的日历文件
	public static String getCurYearFile(){
		return calTxt.replace("rqs", DateUnsafeUtil.curYear());
	}
	public static String getYearFile(String year){
		return calTxt.replace("rqs", year);
	}
	public static File file() throws IOException{
		File file = new File(getCurYearFile());
		if(file.exists()){
			file.delete();
		}
		file.createNewFile();
		return file;
	}
	public static File file(String year) throws IOException{
		File file = new File(getYearFile(year));
		if(file.exists()){
			file.delete();
		}
		file.createNewFile();
		return file;
	}
	//根据年份 生成指定年份全年的节假日
	public static List<Holiday> initHoliday(
			String yyyy,
			HolidayStrategy strategy) throws IOException {
		return initHoliday(yyyy+"0101",
				yyyy+"1231", 
				strategy,
				file(yyyy));
	}
	
	public static List<Holiday> initHoliday(
			String yyyyMMdd_start,
			String yyyyMMdd_end,
			HolidayStrategy strategy) throws IOException {
		return initHoliday(yyyyMMdd_start,
				yyyyMMdd_end, 
				strategy,
				file(yyyyMMdd_start.substring(0, 4)));
	}
	/**
	 * 指定区间、指定策略的节假日获取
	 * @param yyyyMMdd_start
	 * @param yyyyMMdd_end
	 * @param strategy
	 * @return
	 * @throws IOException
	 */
	public static List<Holiday> initHoliday(
			String yyyyMMdd_start,
			String yyyyMMdd_end,
			HolidayStrategy strategy,File file) 
					throws IOException{
		//File curYearCalFile = file();
		//计算两个日期之间的差值
		int days = DateUnsafeUtil.daysBetween(yyyyMMdd_start, yyyyMMdd_end);
		Date startDate = DateUnsafeUtil.calendar(yyyyMMdd_start).getTime();
		List<Holiday> list = new ArrayList<Holiday>();
		for(int i=0;i<=days;i++){
			Date date = DateUnsafeUtil.preDate(startDate,i);
			String a = DateUnsafeUtil.date2long8(date).toString();
			String type =strategy
					.holidayType(DateUnsafeUtil.str8to10(a, "-"));
			String row = a+","+type+"\n";
			Holiday holiday1 = new Holiday(date,type);
			list.add(holiday1);
			FileUtils.append(file.getAbsolutePath(), row);
		}
		System.out.println(list);
		return list;
	}
	
	public static  List<Holiday> getHolidays(){
		List<String> rqs = FileUtils.readFileToList(HolidayUtils.getCurYearFile());
		List<Holiday> data = new ArrayList<Holiday>();
		for(String rq:rqs){
			Holiday d =new Holiday(rq);
			data.add(d);
		}
		return data;
	}
	
	
	//生成当年的节假日维护sql
	public static void generSql(){
		List<Holiday> data  = getHolidays();
		GenerSqlStrategy sty = new KiiikWorkDayStrategy();
		for(Holiday d:data){
			System.out.println(sty.genSql(d));
		}
	}
	public static void main(String[] args) {
		//System.out.println(getHolidays());
		generSql();
	}
}
