package com.kiiik.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.cyb.date.DateUtil;
import com.kiiik.pub.Contants;
import com.kiiik.pub.exception.VasException;

public class TimeSectionAnalysis {
	//判断今天的交易是否结束
	public static boolean isOverToday(){
		boolean flag = true;
		
		return flag;
	}
	/**
	 * 
	 * @作者:iechenyb</br>
	 * @功能描述：计算指定时间stime和etime，间隔为step的时间区间</br>
	 * @创建时间：2016年5月6日下午4:38:49</br>
	 */
    public static Map<String,String> section(String stime,String etime,int step){
    	String spilt = "";
    	String curDay = ConcurrentDateUtil.date2long8(new Date()).toString(); //20150102
    	if(stime.length()==4){
    		stime = stime+"00";//参数为分钟时，精确到秒
    	}
    	if(etime.length()==4){
    		etime = etime+"00";//参数为分钟时，精确到秒
    	}
    	Calendar start = calendar(curDay+stime);
    	//如果结束时间是过凌晨0点，则结束时间使用day+1
    	Calendar end = calendar(curDay+etime);
    	long startMillis = start.getTimeInMillis();
    	long endMillis = end.getTimeInMillis();
    	if(endMillis<startMillis){//结束点在后半夜
    		String nextDay = ConcurrentDateUtil.date2long8(DateUtil.nextSomeDay(new Date(), 1)).toString();
    		end = calendar(nextDay+etime);
    		endMillis = end.getTimeInMillis();
    	}
    	List<String> xlst = new ArrayList<String>();
		List<String> ylst = new ArrayList<String>();
		Map<String,String> xmap = new LinkedHashMap<String,String>();
		for(long i = startMillis;i<=endMillis;i=i+1000*60*step){
	    	 Calendar x = Calendar.getInstance();
	    	 x.setTimeInMillis(i);
	    	 String str= ConcurrentDateUtil.date2long14(x.getTime()).toString();
	    	 xlst.add(str.substring(8,10)+spilt+str.substring(10,12));
	    	 xmap.put(str.substring(8,10)+spilt+str.substring(10,12), "-");
	    	 ylst.add("-");
	    }
    	return xmap;
	}
    /**
     * 根据合约代码获取大写的品种代码
     * @作者:iechenyb</br>
     * @功能描述：</br>
     * @创建时间：2016年5月6日下午4:39:48</br>
     */
	public static String getProductCode(String code){
		 String s = "[a-zA-Z]+";
	     Pattern  pattern=Pattern.compile(s);  
	     Matcher  ma=pattern.matcher(code);  
	     if(ma.find()){
	    	 return  ma.group().toUpperCase();
	     }else{
	    	 return "";
	     }
	}
	
	public static int getLastNotEmptyIndex(List<String> values){
		int index =0;
		for(int i=0;i<values.size();i++){
			if(!"-".equals(values.get(i))){
				index = i;
			}
		}
		return index;
	}
	public static Calendar calendar(String yyyymmddhhmmss) {
		yyyymmddhhmmss = yyyymmddhhmmss.replaceAll("-", "").replaceAll(":", "").replaceAll("\\\\", "").replaceAll(" ", "")
				.replace("/", "");
		if (yyyymmddhhmmss.length() != 8 && yyyymmddhhmmss.length() != 14) {
			try {
				throw new Exception("参数必须为8位或者14位数字");
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
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述:绘制品种的交易时间坐标<br>
	 *创建时间: 2018年6月1日下午4:35:35
	 *@param productCode
	 *@param step
	 *@return
	 */
	public static Map<String,String> timeX(String productCode,int step){
		Map<String,String> x = new LinkedHashMap<String,String>();
		//夜盘品种坐标
		if(Contants.NINGHT_PRODUCT_MAP.containsKey(productCode.toUpperCase())){
			x.putAll(section("2100",Contants.NINGHT_PRODUCT_MAP.get(productCode.toUpperCase()),step));
		}
		//日盘坐标（所有品种都有）
		if("IF".equals(productCode)||"IH".equals(productCode)){
			x.putAll(section("0900","1130",step));
			x.putAll(section("1300","1500",step));
		}else if("TF".equals(productCode)||"T".equals(productCode)){
			x.putAll(section("0915","1130",step));
			x.putAll(section("1300","1515",step));
		}else{
			x.putAll(section("0900","1015",step));
			x.putAll(section("1030","1130",step));
			x.putAll(section("1330","1500",step));
		}
		return x;
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 是否是夜盘时间<br>
	 *创建时间: 2018年6月4日下午4:48:15
	 *@param hhmm
	 *@return
	 */
	public static boolean isNinghtTime(String hhmm){
		if(StringUtils.isBlank(hhmm)){ throw new VasException("时间格式HHmm");}
		String curDay = ConcurrentDateUtil.date2long8(new Date()).toString(); //20150102
		long ninght_start = calendar(curDay+"180000").getTimeInMillis();
		long ninght_end = calendar(curDay+"235959").getTimeInMillis();
		long cur_time = calendar(curDay+hhmm+"00").getTimeInMillis();
		if(cur_time>=ninght_start&&cur_time<=ninght_end){
			return true;
		}else{
			return false;
		}
	}
	/*public static void main(String[] args) {
		System.out.println(section("0900","1130",5));
	}*/
}
