package com.cyb.app.commondb;

import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.beans.BeanUtils;

import com.cyb.app.h2.H2DBConnectionPool;
import com.cyb.app.holiday.HolidaySQL;
import com.cyb.utils.date.DateSafeUtil;
import com.cyb.utils.returnBean.ParamMap;
import com.cyb.utils.text.ELUtils;

public class ConnectionUtils extends QueryRunner {
	public Connection conn = null;
	public QueryRunner query = null;

	public void close() {
		DbUtils.closeQuietly(conn);
	}

	public ConnectionUtils(Connection conn) {
		if (this.conn == null) {
			this.conn = conn;
		}
		if (query == null) {
			query = new QueryRunner();
		}
	}

	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException {
		JdbcConnectionPool pool = H2DBConnectionPool.getJDBCConnectionPool();
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		String sql = ELUtils.el(HolidaySQL.preTradeSQL, 
				ParamMap
				.build()
				.put("rq",DateSafeUtil.date2long8(new Date()).toString() ));
		System.out.println(dbUtil.queryForObject(sql,TestBean.class));
		System.out.println("-----------------------");
		System.out.println(dbUtil.queryForList(sql, TestBean.class));
		System.out.println("-----------------------");
		System.out.println(dbUtil.queryForList1(sql, TestBean.class));
	}
	
	public Map<String, Object> queryForMap(String sql) throws SQLException {
		return queryForListMap(sql).get(0);
	}

	public List<Map<String, Object>> queryForListMap(String sql) throws SQLException {
		List<Map<String, Object>> results = (List<Map<String, Object>>) this.query.query(conn, sql,
				new MapListHandler());
		return results;
	}
	public <T> T queryForObject(String sql,Class<T> t)throws SQLException{ 
		//调用方法,传递结果集实现类BeanHandler 
		T s = super.query(conn, sql, new BeanHandler<T>(t)); 
		return s;
	} 
	public <T> List<T> queryForList1(String sql, Class<T> classes)
			throws SQLException, InstantiationException, IllegalAccessException {
		List<Map<String, Object>> results = (List<Map<String, Object>>) this.query.query(conn, sql,
				new MapListHandler());
		List<T> data = new ArrayList<>();
		for (Map<String, Object> m : results) {
			T t = (T) classes.newInstance();
			BeanUtils.copyProperties(m, t);
			data.add(t);
		}
		return data;
	}

	@Override
	public int[] batch(Connection arg0, String arg1, Object[][] arg2) {
		try {
			return super.batch(arg0, arg1, arg2);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public int[] batch(String sql, Object[][] params) {
		try {
			return super.batch(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	@Override
	protected void close(Connection conn) {
		try {
			super.close(conn);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	@Override
	protected void close(ResultSet rs) {
		try {
			super.close(rs);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	protected void close(Statement stmt) {
		try {
			super.close(stmt);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void fillStatement(PreparedStatement arg0, Object... arg1) {
		try {
			super.fillStatement(arg0, arg1);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	@Override
	public void fillStatementWithBean(PreparedStatement arg0, Object arg1, PropertyDescriptor[] arg2) {
		try {
			super.fillStatementWithBean(arg0, arg1, arg2);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	@Override
	public void fillStatementWithBean(PreparedStatement arg0, Object arg1, String... arg2) {
		try {
			super.fillStatementWithBean(arg0, arg1, arg2);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	@Override
	public DataSource getDataSource() {
		return super.getDataSource();
	}

	@Override
	protected Connection prepareConnection() {
		try {
			return super.prepareConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	@Override
	protected PreparedStatement prepareStatement(Connection conn, String sql) {
		try {
			return super.prepareStatement(conn, sql);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	@Override
	protected void rethrow(SQLException cause, String sql, Object... params) {
		try {
			super.rethrow(cause, sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	@Override
	public int update(Connection arg0, String arg1, Object... arg2) {
		try {
			return super.update(arg0, arg1, arg2);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	@Override
	public int update(Connection conn, String sql, Object param) {
		try {
			return super.update(conn, sql, param);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	@Override
	public int update(Connection conn, String sql) {
		try {
			return super.update(conn, sql);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	@Override
	public int update(String sql, Object... params) {
		try {
			return super.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	@Override
	public int update(String sql, Object param) {
		try {
			return super.update(conn, sql, param);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	public int update(String sql) {
		try {
			return super.update(conn, sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	protected ResultSet wrap(ResultSet rs) {
		return super.wrap(rs);
	}

	/*
	 * 结果集第八种处理方法,MapListHandler 将结果集每一行存储到Map集合,键:列名,值:数据 Map集合过多,存储到List集合
	 */
	public  List<Map<String, Object>> mapListHandler(String sql) throws SQLException {
		// 调用方法query,传递结果集实现类MapListHandler
		// 返回值List集合, 存储的是Map集合
		List<Map<String, Object>> list = super.query(conn, sql, new MapListHandler());
		return list;
	}

	/*
	 * 结果集第七种处理方法,MapHandler 将结果集第一行数据,封装到Map集合中 Map<键,值> 键:列名 值:这列的数据
	 */
	public  Map<String, Object> mapHandler(String sql) throws SQLException {
		Map<String, Object> map = super.query(conn, sql, new MapHandler());
		return map;
	}

	/*
	 * 结果集第六种处理方法,ScalarHandler 对于查询后,只有1个结果
	 * 聚合函数
	 */
	public  long scalarHandler(String sql) throws SQLException {
		//String sql = "SELECT COUNT(*) FROM sort";
		// 调用方法query,传递结果集处理实现类ScalarHandler
		return super.query(conn, sql, new ScalarHandler<Long>());
	}

	/*
	 * 结果集第五种处理方法,ColumnListHandler 结果集,指定列的数据,存储到List集合 List<Object> 每个列数据类型不同
	 */
	public List<String> columnListHandler(String sql,String colName) throws SQLException {
		QueryRunner qr = new QueryRunner();
		// 调用方法 query,传递结果集实现类ColumnListHandler
		// 实现类构造方法中,使用字符串的列名
		List<String> list = qr.query(conn, sql, new ColumnListHandler<String>(colName));
		return list;
	}

	/*
	 * 结果集第四种处理方法, BeanListHandler 结果集每一行数据,封装JavaBean对象 多个JavaBean对象,存储到List集合
	 */
	public <T> List<T>  queryForList(String sql,Class<T> t) throws SQLException {
		// 调用方法query,传递结果集处理实现类BeanListHandler
		List<T> list = super.query(conn, sql, new BeanListHandler<T>(t));
		return list;
	}

	/*
	 * 结果集第三种处理方法,BeanHandler 将结果集的第一行数据,封装成JavaBean对象 注意: 被封装成数据到JavaBean对象,
	 * Sort类必须有空参数构造
	 */
	public <T>  T beanHandler(String sql,Class<T> t) throws SQLException {
		// 调用方法,传递结果集实现类BeanHandler
		T s = super.query(conn, sql, new BeanHandler<T>(t));
		return s;
	}

	/*
	 * 结果集第二种处理方法,ArrayListHandler 将结果集的每一行,封装到对象数组中, 出现很多对象数组 对象数组存储到List集合
	 */
	public  List<Object[]> arrayListHandler(String sql) throws SQLException {
		// 调用query方法,结果集处理的参数上,传递实现类ArrayListHandler
		// 方法返回值 每行是一个对象数组,存储到List
		List<Object[]> result = super.query(conn, sql, new ArrayListHandler());
		return result;
	}

	/*
	 * 结果集第一种处理方法, ArrayHandler 将结果集的第一行存储到对象数组中 Object[]
	 */
	public Object[] arrayHandler(String sql) throws SQLException {
		// 调用方法query执行查询,传递连接对象,SQL语句,结果集处理方式的实现类
		// 返回对象数组
		Object[] result = super.query(conn, sql, new ArrayHandler());
		return result;
	}
}
