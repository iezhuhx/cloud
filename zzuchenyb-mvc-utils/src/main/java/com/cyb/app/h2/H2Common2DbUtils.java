package com.cyb.app.h2;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.jdbcx.JdbcConnectionPool;

import com.cyb.app.commondb.ConnectionUtils;
import com.cyb.app.commondb.DDLUtils;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2019年1月23日
 */
public class H2Common2DbUtils {
	Log log = LogFactory.getLog(H2Common2DbUtils.class);
	static H2DBInfor dbInfo = new H2DBInfor();
	static JdbcConnectionPool pool = H2DBConnectionPool.getJDBCConnectionPool(dbInfo);
	
	public H2Common2DbUtils(JdbcConnectionPool pool){
		H2Common2DbUtils.pool = pool;
	}
	public static void main(String[] args) throws SQLException, IOException {
	
	}
	/**
	 * 获取表信息
	 * @return
	 */
	public static List<String> getTables(){
		return null;
	}
	
	/**
	 * 
	 * @param tableName
	 * @return 从默认库里查看是否存在
	 * @throws SQLException
	 */
	public static boolean exists(String tableName) throws SQLException{
		return exists(pool,tableName);
	}
	public static boolean exists(JdbcConnectionPool pool,String tableName) throws SQLException{
		boolean hasTable = DDLUtils.isExist(pool.getConnection(), tableName);
		return hasTable;
	}
	/**
	 * 默认的内存数据库创建表
	 * @param name
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public static boolean createTable(String name,String sql) throws SQLException {
		return createTable(pool,name,sql);
	}
	//指定的连接池创建表
	public static boolean createTable(JdbcConnectionPool pool,String name,String sql) throws SQLException {
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		if (!exists(pool,name)) {
			System.out.println("创建表...");
			dbUtil.update(sql);
		} else {
			System.out.println("表已经存在！");
		}
		return true;
	}
}
