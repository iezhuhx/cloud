package com.cyb.app.stock.util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.jdbcx.JdbcConnectionPool;

import com.cyb.app.commondb.ConnectionUtils;
import com.cyb.app.h2.H2Common2DbUtils;
import com.cyb.app.h2.H2DBConnectionPool;
import com.cyb.app.stock.pub.RealQuotation;
import com.cyb.app.stock.pub.Stock;
import com.cyb.utils.sql.utils.ProduceSqlUtils;

/**
 * @Author iechenyb<br>
 * @Desc 类描述<br>
 * @CreateTime 2020年7月6日 下午6:54:27
 */
public class StrateStockDbUtils {
	static Log log = LogFactory.getLog(StrateStockDbUtils.class);

	static JdbcConnectionPool pool = H2DBConnectionPool.getJDBCConnectionPool();

	public static void main(String[] args) throws IOException, SQLException {
		initStockDB(false);// 初始化表结构
		// initStockDbInfo();
		// readListBatch("D:\\data\\stock\\sh.txt","sh");	
		// readListBatch("D:\\data\\stock\\sz.txt","sz");
		// analyse();
	}

	/**
	 * 初始化股票相关的数据库信息
	 * 
	 * @Author iechenyb<br>
	 * @Desc 说点啥<br>
	 * @CreateTime 2020年12月9日 下午5:04:01
	 * @param clearData
	 */
	public static void initStockDB(boolean clearData) {
		try {
			//H2Common2DbUtils.reCreateTable(pool, RealQuotation.class);
			H2Common2DbUtils.reCreateTable(pool, Stock.class);
			if (clearData) {// 是否清楚股票数据
				H2Common2DbUtils.clearTableData(pool, Stock.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * 保存行情信息
	 * 
	 * @Author iechenyb<br>
	 * @Desc 说点啥<br>
	 * @CreateTime 2020年12月9日 下午4:35:45
	 * @param data
	 * @throws SQLException
	 */
	public static void saveStockCode(List<Stock> data) throws SQLException {
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		for (Stock rq : data) {
			try {
				String sql = ProduceSqlUtils.getInsertSql(rq);
				log.info(sql);
				dbUtil.update(sql);
			} catch (Exception e) {
			}
		}
	}

	// 保存行情逻辑
	public static void saveRealQutoes(List<RealQuotation> qutoes) throws SQLException {
		// 保存行情逻辑
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		for (RealQuotation rq : qutoes) {
			try {
				/*Map<String, Object> param = new HashMap<>();
				param.put("code", rq.getCode());
				param.put("name", rq.getName());
				param.put("cjl", rq.getCjl());
				param.put("cje", rq.getCje());
				String sql = ELUtils.el(StockSQL.insertRealQutoesSQL, param);*/
				String sql = ProduceSqlUtils.getInsertSql(rq);
				System.out.println(sql);
				dbUtil.update(sql);
			} catch (Exception e) {
			}
		}
	}
}
