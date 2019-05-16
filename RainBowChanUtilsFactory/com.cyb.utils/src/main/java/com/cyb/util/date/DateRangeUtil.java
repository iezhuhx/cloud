package com.cyb.util.date;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年3月19日
 */
@Slf4j
public class DateRangeUtil {

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 匹配任意格式<br>
	 * 创建时间: 2017年7月15日
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isInDayWithTime(String start, String end) {
		return isInDayWithTime(start, end, DateUnsafeUtil.date2long8().toString());
	}

	public static boolean isInDayNoTime(String start, String end) {
		return isInDayNoTime(start, end, DateUnsafeUtil.date2long8().toString());
	}

	public static boolean isInDayNoTime(String start_date, String end_date, String cur_date) {
		long start = Long.valueOf(start_date);
		long end = Long.valueOf(end_date);
		long target = Long.valueOf(cur_date);
		return start <= target && target <= end;
	}

	public static boolean isInDayWithTime(String start_date, String end_date, String cur_date) {
		long start = DateUnsafeUtil.calendar(start_date).getTimeInMillis();
		long end = DateUnsafeUtil.calendar(end_date).getTimeInMillis();
		long cur = DateUnsafeUtil.calendar(cur_date).getTimeInMillis();
		if (cur >= start && cur <= end) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isInTime(String sourceTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String cur = sdf.format(new Date());
		return isInTime(sourceTime, cur);
	}

	/**
	 * 判断某一时间是否在一个区间内
	 * 
	 * @param sourceTime
	 *            时间区间,半闭合,如[10:00-20:00)
	 * @param curTime
	 *            需要判断的时间 如10:00
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static boolean isInTime(String sourceTime, String curTime) {
		if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
			throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
		}
		if (curTime == null || !curTime.contains(":")) {
			throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
		}
		String[] args = sourceTime.split("-");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		try {
			long now = sdf.parse(curTime).getTime();
			long start = sdf.parse(args[0]).getTime();
			long end = sdf.parse(args[1]).getTime();
			if (args[1].equals("00:00")) {
				args[1] = "24:00";
			}
			if (end < start) {
				if (now >= end && now < start) {
					return false;
				} else {
					return true;
				}
			} else {
				if (now >= start && now < end) {
					return true;
				} else {
					return false;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
		}

	}
}
