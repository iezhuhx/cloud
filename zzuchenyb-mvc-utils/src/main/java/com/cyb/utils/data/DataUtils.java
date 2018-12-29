package com.cyb.utils.data;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DataUtils {
	private static int precision = 8;
	public static String e2String(double number, int precision) {
		DecimalFormat df = new DecimalFormat(formatStr(precision));
		return df.format(number);
	}
	public static String e2String(String number, int precision) {
		DecimalFormat df = new DecimalFormat(formatStr(precision));
		return df.format(number);
	}
	public static String e2String(String number) {
		DecimalFormat df = new DecimalFormat(formatStr(precision));
		return df.format(number);
	}
	public static String e2String(Double number) {
		DecimalFormat df = new DecimalFormat(formatStr(precision));
		return df.format(number);
	}
	public static BigDecimal e2Double(double number, int precision) {
		return new BigDecimal(e2String(number,precision));
	}
	public static String e2StringBD(double number, int precision){
		BigDecimal db = new BigDecimal(number);
		String ii = db.toPlainString();
		return e2String(new Double(ii).doubleValue(),precision);
	}
	public static String e2StringBD(double number){
		BigDecimal db = new BigDecimal(number);
		String ii = db.toPlainString();
		return e2String(new Double(ii).doubleValue(),precision);
	}
	public static String formatStr ( int precision){
		StringBuffer sb = null;
		if(precision<=0){
			sb = new StringBuffer("#0.0");
		}else{
			sb = new StringBuffer("#0.");
			for (int i = 1; i <= precision; i++) {
				sb.append("0");
			}
		}
		return sb.toString();
	}
	public static void main(String[] args) { 
		   
		   System.out.println(DataUtils.e2Double(0.1522784999999996, 8)); 
		   System.out.println(0.05+0.01);
	       System.out.println(1.0-0.42);
	       System.out.println(4.015*100);
	       System.out.println(123.3/100);
	       System.out.println(1/3.0);
		   System.out.println(DataUtils.e2Double(0.05+0.01, 11)); 
		   System.out.println(DataUtils.e2Double(4.015*100, 9)); 
		   System.out.println(DataUtils.e2Double(123.3/100, 9)); 
		   System.out.println(DataUtils.e2Double(1/3.0, 5)); 
		   System.out.println(DataUtils.e2Double(1/3.0, 6)); 
		   System.out.println(DataUtils.e2Double(1/3.0, 7)); 
		   System.out.println(DataUtils.e2Double(1/3.0, 15)); 
		   System.out.println(DataUtils.e2Double(1/3.0, 20)); 
		   System.out.println(0.0000001>0);
		   System.out.println(0.000000000000000000000001>0);
		   System.out.println(DataUtils.e2StringBD(1.0E-2, 1)); 
		   System.out.println(DataUtils.e2StringBD(1.0E-17, 18)); 
		   System.out.println(DataUtils.e2StringBD(1.0E-20, 21)); 
		} 
}
