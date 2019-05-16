package com.cyb.util.data;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DecimalUtils {
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
}
