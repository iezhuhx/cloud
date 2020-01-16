package com.cyb.app.holiday;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.jdbcx.JdbcConnectionPool;

import com.cyb.app.commondb.ConnectionUtils;
import com.cyb.app.h2.H2DBConnectionPool;
import com.cyb.app.h2.H2DBInfor;
import com.cyb.utils.date.DateSafeUtil;
import com.cyb.utils.date.DateUnsafeUtil;
import com.cyb.utils.file.FileUtils;
import com.cyb.utils.text.ELUtils;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2019年1月23日
 */
public class HolidayH2DbUtils {
	Log log = LogFactory.getLog(HolidayH2DbUtils.class);
	static String tableName = "holiday";
	static H2DBInfor dbInfo = new H2DBInfor();
	static JdbcConnectionPool pool = H2DBConnectionPool.getJDBCConnectionPool(dbInfo);
	public static void main(String[] args) throws Exception {
		//H2Common2DbUtils.createTable(tableName,HolidaySQL.createSQL);
		//insertHolidayFromFile();//初始化到内存数据库（日期已经分好）
		System.out.println("今日："+HolidayH2DbUtils.getToday().rq()+"["+HolidayH2DbUtils.getToday().getDesc()+"]\n");
		//DateUnsafeUtil.showEnCurMonthCal();//显示日历信息
		//显示当年的每个月的交易日历
		for(int mon=1;mon<=12;mon++){
			//DateUnsafeUtil.showMonthCal(mon);
		}
		DateUnsafeUtil.showMonthCal(DateUnsafeUtil.preDate(200));
		System.out.println("上个交易日："+preTradeDay(new Date()));
	}
	
	//获取指定的某一天
	public static Holiday getSomeDay(Date date) throws SQLException{
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		Map<String, Object> param = new HashMap<>();
		param.put("rq", DateSafeUtil.date2long8(date).toString());
		String sql = ELUtils.el(HolidaySQL.tradeSQL, param);
		Holiday h =  dbUtil.queryForObject(sql, Holiday.class);
		h.setRq(date);
		dbUtil.close();
		return h;
	}
	//获取今天
	public static Holiday getToday() throws SQLException{
		return getSomeDay(new Date());
	}
	
	//上一个交易日
	public static String preTradeDay(String yyyymmddDate) throws SQLException {
		return preTradeDay(DateUnsafeUtil.calendar(yyyymmddDate).getTime());
	}
	//获取当日的上个交易日
	public static String preTradeDay() throws SQLException{
		return preTradeDay(new Date());
	}
	 //上一个交易日
	public static String preTradeDay(Date date) throws SQLException {
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		Map<String, Object> param = new HashMap<>();
		param.put("rq", DateSafeUtil.date2long8(date).toString());
		String sql = ELUtils.el(HolidaySQL.preTradeSQL, param);
		return dbUtil.queryForMap(sql).get("rq").toString();
	}
    //下一个交易日
	public static String nextTradeDay(String date) throws SQLException {
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		Map<String, Object> param = new HashMap<>();
		param.put("rq", DateSafeUtil.date2long8(DateUnsafeUtil.calendar(date).getTime()).toString());
		String sql = ELUtils.el(HolidaySQL.nextTradeSQL, param);
		return dbUtil.queryForMap(sql).get("rq").toString();
	}

	//从文件中读取日期数据，然后进行插入
	public static void insertHolidayFromFile() throws SQLException {
		List<String> rqs = FileUtils.readFileToList(HolidayUtils.getCurYearFile());
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		for (String row : rqs) {
			Holiday holiday = new Holiday(row);
			Map<String, Object> param = new HashMap<>();
			param.put("rq", DateSafeUtil.date2long8(holiday.getRq()).toString());
			param.put("type", holiday.getType());
			String sql = ELUtils.el(HolidaySQL.insertSQL, param);
			System.out.println(sql);
			dbUtil.update(sql);
		}
	}
}
