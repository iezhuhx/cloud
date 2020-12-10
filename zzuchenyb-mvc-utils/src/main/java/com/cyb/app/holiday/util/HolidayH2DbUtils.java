package com.cyb.app.holiday.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.util.StringUtils;

import com.cyb.app.commondb.ConnectionUtils;
import com.cyb.app.h2.H2DBConnectionPool;
import com.cyb.app.holiday.Holiday;
import com.cyb.app.holiday.HolidaySQL;
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
	static JdbcConnectionPool pool = H2DBConnectionPool.getJDBCConnectionPool();
	/*public static void main(String[] args) throws Exception {
		H2Common2DbUtils.createTable(tableName,HolidaySQL.createSQL);
		insertHolidayFromFile();//初始化到内存数据库（日期已经分好）
		System.out.println("今日："+HolidayH2DbUtils.getToday().rq()+"["+HolidayH2DbUtils.getToday().getDesc()+"]\n");
		//DateUnsafeUtil.showEnCurMonthCal();//显示日历信息
		//显示当年的每个月的交易日历
		for(int mon=1;mon<=12;mon++){
			//DateUnsafeUtil.showMonthCal(mon);
		}
		DateUnsafeUtil.showMonthCal(DateUnsafeUtil.preDate(200));
		System.out.println("上个交易日："+preTradeDay(new Date()));
	}*/
	
	//获取指定的某一天
	public static Holiday getSomeDay(Date date) throws SQLException{
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		Map<String, Object> param = new HashMap<>();
		param.put("rq", DateSafeUtil.date2long8(date).toString());
		
		String sql = ELUtils.el(HolidaySQL.calSQL, param);
		//System.out.println("sql:"+sql);
		Holiday h =  dbUtil.queryForObject(sql, Holiday.class);
		h.setRq(date);
		dbUtil.close();
		return h;
	}
	//获取今天
	public static Holiday getToday() throws SQLException{
		return getSomeDay(new Date());
	}
	
	/**
	 * 当日是否为交易日
	 *@Author iechenyb<br>
	 *@Desc 说点啥<br>
	 *@CreateTime 2020年12月9日 下午3:28:51
	 *@return
	 */
	public static boolean isTradeDay(){
		try {
			Holiday h = getToday();
			return h.isTradeDay();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 判断指定日期是否为交易日
	 *@Author iechenyb<br>
	 *@Desc 说点啥<br>
	 *@CreateTime 2020年12月9日 下午3:30:32
	 *@param yyyymmdd
	 *@return
	 */
	public static boolean isTradeDay(String yyyymmdd){
		try {
			Holiday h = getSomeDay(DateUnsafeUtil.calendar(yyyymmdd).getTime());
			return h.isTradeDay();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
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
	public static String nextTradeDay(String yyyymmdd) throws SQLException {
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		Map<String, Object> param = new HashMap<>();
		param.put("rq", DateSafeUtil.date2long8(DateUnsafeUtil.calendar(yyyymmdd).getTime()).toString());
		String sql = ELUtils.el(HolidaySQL.nextTradeSQL, param);
		return dbUtil.queryForMap(sql).get("rq").toString();
	}

	//从文件中读取日期数据，然后进行插入
	public static void insertHolidayFromFile() throws SQLException {
		insertHolidayFromFile("");
	}
	public static void insertHolidayFromFile(String year) throws SQLException {
		List<String> rqs =  new ArrayList<String>();
		String fromFileDir = "";
		if(StringUtils.isEmpty(year)){
			fromFileDir = HolidayUtils.getCurYearFile();
		}else{
			fromFileDir = HolidayUtils.getYearFile(year);
		}
		System.out.println("读取节假日文件："+fromFileDir);
		rqs = FileUtils.readFileToList(fromFileDir);
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		for (String row : rqs) {
			Holiday holiday = new Holiday(row);
			Map<String, Object> param = new HashMap<>();
			param.put("rq", DateSafeUtil.date2long8(holiday.getRq()).toString());
			param.put("type", holiday.getType());
			String sql = ELUtils.el(HolidaySQL.insertSQL, param);
			//System.out.println(sql);
			dbUtil.update(sql);
		}
	}
}
