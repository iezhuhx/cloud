package com.cyb.app.h2;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.jdbcx.JdbcConnectionPool;

import com.cyb.app.commondb.ConnectionUtils;
import com.cyb.app.commondb.DDLUtils;
import com.cyb.utils.sql.utils.ProduceSqlUtils;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2019年1月23日
 */
public class H2Common2DbUtils {
	Log log = LogFactory.getLog(H2Common2DbUtils.class);
	static JdbcConnectionPool pool = H2DBConnectionPool.getJDBCConnectionPool();
	
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
			System.out.println("创建表"+name+"...");
			dbUtil.update(sql);
		} else {
			System.out.println("表"+name+"已经存在！");
		}
		return true;
	}
	
	public static boolean createTable(JdbcConnectionPool pool,Class<?> clzz) throws SQLException {
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		String tabName = ProduceSqlUtils.getTableName(clzz);
		if (!exists(pool,tabName)) {
			System.out.println("创建表"+tabName+"...");
			dbUtil.update(ProduceSqlUtils.genCreateTableSql(clzz));
		} else {
			System.out.println("表"+tabName+"已经存在！");
		}
		return true;
	}
	
	/**
	 * 删除表结构
	 *@Author iechenyb<br>
	 *@Desc 说点啥<br>
	 *@CreateTime 2020年12月10日 上午9:53:13
	 *@param pool
	 *@param name
	 *@return
	 *@throws SQLException
	 */
	public static boolean dropTable(JdbcConnectionPool pool,String name) throws SQLException {
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		if (exists(pool,name)) {
			System.out.println("drop表"+name+"...");
			dbUtil.update("drop table "+name+";");
		} else {
			System.out.println("表"+name+"不存在！");
		}
		return true;
	}
	/**
	 * 重新创建表结构
	 *@Author iechenyb<br>
	 *@Desc 说点啥<br>
	 *@CreateTime 2020年12月10日 上午9:54:22
	 *@return
	 * @throws SQLException 
	 */
	public static boolean reCreateTable(JdbcConnectionPool pool,String name,String sql) throws SQLException{
		dropTable(pool, name);
		System.out.println("建表语句："+sql);
		return createTable(name, sql);
	}
	
	public static boolean reCreateTable(JdbcConnectionPool pool,Class<?> clzz) throws SQLException{
		String name = ProduceSqlUtils.getTableName(clzz);
		dropTable(pool, name);
		String sql = ProduceSqlUtils.genCreateTableSql(clzz);
		System.out.println("建表语句："+sql);
		return createTable(name, sql);
	}
	
	/**
	 * 清除表数据
	 *@Author iechenyb<br>
	 *@Desc 说点啥<br>
	 *@CreateTime 2020年12月10日 上午9:55:35
	 *@param pool
	 *@param name
	 *@return
	 *@throws SQLException
	 */
	public static boolean clearTableData(JdbcConnectionPool pool,String name) throws SQLException {
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		if (exists(pool,name)) {
			System.out.println("delete表"+name+"数据...");
			dbUtil.update("delete from "+name+";");
		} else {
			System.out.println("表"+name+"不存在！");
		}
		return true;
	}
	
	public static boolean clearTableData(JdbcConnectionPool pool,Class<?> clzz) throws SQLException {
		return clearTableData(pool,ProduceSqlUtils.getTableName(clzz));
	}
}
