package com.cyb.app.holiday;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.jdbcx.JdbcConnectionPool;

import com.cyb.app.commondb.ConnectionUtils;
import com.cyb.app.h2.H2Common2DbUtils;
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

	public static void main(String[] args) throws SQLException, IOException {
		H2Common2DbUtils.createTable(tableName,HolidaySQL.createSQL);
		insertHolidayFromFile();//初始化到内存数据库（日期已经分好）
	}
	public static boolean isTradeDay() throws SQLException {
		return isTradeDay(new Date());
	}
	public static Holiday getSomeDay() throws SQLException {
		return getSomeDay(new Date());
	}
	public static Holiday getSomeDay(Date date) throws SQLException{
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		Map<String, Object> param = new HashMap<>();
		param.put("rq", DateSafeUtil.date2long8(date).toString());
		String sql = ELUtils.el(HolidaySQL.tradeSQL, param);
		Holiday h =  dbUtil.queryForObject(sql, Holiday.class);
		dbUtil.close();
		return h;
	}
	public static boolean isTradeDay(Date date) throws SQLException {
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		Map<String, Object> param = new HashMap<>();
		param.put("rq", DateSafeUtil.date2long8(date).toString());
		String sql = ELUtils.el(HolidaySQL.tradeSQL, param);
		return GoseekHolidayUtils.HOLIDAY_GOGZUORI.equals(dbUtil.queryForMap(sql).get("type").toString());
	}
	private static void showHoliday() throws SQLException {
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		Map<String, Object> param = new HashMap<>();
		param.put("rq", DateSafeUtil.date2long8(new Date()).toString());
		String sql = ELUtils.el(HolidaySQL.preTradeSQL, param);
		System.out.println(dbUtil.queryForMap(sql).get("rq"));
	}
	public static String preTradeDay(String date) throws SQLException {
		return preTradeDay(DateUnsafeUtil.calendar(date).getTime());
	}
	public static String preTradeDay(Date date) throws SQLException {
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		Map<String, Object> param = new HashMap<>();
		param.put("rq", DateSafeUtil.date2long8(date).toString());
		String sql = ELUtils.el(HolidaySQL.preTradeSQL, param);
		return dbUtil.queryForMap(sql).get("rq").toString();
	}

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

	/*public static boolean createTable(String string) throws SQLException {
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		boolean hasTable = DDLUtils.isExist(pool.getConnection(), tableName);
		if (!hasTable) {
			System.out.println("创建表...");
			dbUtil.update(HolidaySQL.createSQL);
		} else {
			System.out.println("表已经存在！");
		}
		return true;
	}*/
	
	/*public static boolean createTable(String name,String sql) throws SQLException {
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		boolean hasTable = DDLUtils.isExist(pool.getConnection(), name);
		if (!hasTable) {
			System.out.println("创建表...");
			dbUtil.update(sql);
		} else {
			System.out.println("表已经存在！");
		}
		return true;
	}*/
}
