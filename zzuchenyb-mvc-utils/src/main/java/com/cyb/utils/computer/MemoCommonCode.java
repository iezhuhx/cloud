package com.cyb.utils.computer;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.cyb.utils.date.DateSafeUtil;
/**
 *@Author iechenyb<br>
 *@Desc 单机版 统一分发 本机内存模式yyyymmddHHmmssSSS+2位业务类型+当日流水号5位  共26位<br>
 *@CreateTime 2020年5月22日 上午10:17:01
 *缺点 应用重启则流水号需要从新分发
 */
public class MemoCommonCode implements BuinessCodeGen{
	Log log = LogFactory.getLog(MemoCommonCode.class);
	static Map<String,AtomicLong> ywCounterMap = new ConcurrentHashMap<String,AtomicLong>();
	static {//每天晚上需要将业务号重新初始化
		ywCounterMap.put("01", new AtomicLong(0));
		ywCounterMap.put("02", new AtomicLong(0));
		ywCounterMap.put("03", new AtomicLong(0));
	}
	
	String ywSeqFormat="00000";
	
	@Override
	public synchronized String genCode(String ywlx) {
		if(StringUtils.isEmpty(ywlx)||!ywCounterMap.containsKey(ywlx)){
			return null;
		}
		return DateSafeUtil.timeToMilis19()+ywlx+new DecimalFormat(ywSeqFormat).format(ywCounterMap.get(ywlx).incrementAndGet());
	}
	public static void main(String[] args) {
		BuinessCodeGen genor = new MemoCommonCode();
		for(int i=0;i<99999;i++)
			System.out.println(genor.genCode("01"));
		for(int i=0;i<99999;i++)
			System.out.println(genor.genCode("02"));
	}
}
