package com.cyb.app.h2.usecase;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.h2.jdbcx.JdbcConnectionPool;

import com.cyb.app.commondb.ConnectionUtils;
import com.cyb.app.h2.H2Common2DbUtils;
import com.cyb.app.h2.H2DBConnectionPool;
import com.cyb.app.h2.H2DBInfor;
import com.cyb.app.h2.H2ServerManager;

/**
 * 描述：sql
 *
 * @author iechenyb
 * @create 2019年6月3日15:04:52
 */
public class H2CRUDSql {
	static String tName = "aaaaa";
    public static String CREATESQL="create table "+tName+" (rq varchar,sj varchar)";//日期和时间
    public static String QUERYSQL="select count(1) as total from "+tName+"  where rq='20120202'";
    public static String DELETESQL="DELETE FROM "+tName+" ";
    public static String INSERTSQL ="insert into "+tName+"  values('20120202','23212')";
    static H2DBInfor dbInfo = new H2DBInfor();
    static JdbcConnectionPool pool = H2DBConnectionPool.getJDBCConnectionPool(dbInfo);
    static ConnectionUtils dbUtil =null;
    public static void main(String[] args) throws SQLException {
    	dbUtil = new ConnectionUtils(pool.getConnection());
    	createTable();
    	H2ServerManager.h2Test();//各种初始化连接测试
    }
    
    public static void createTable() throws SQLException{
    	H2Common2DbUtils.createTable(tName,CREATESQL);
    	dbUtil.update(INSERTSQL);
    	query();
    }
    
    public static void query() throws SQLException{
    	 List<Map<String,Object>> rss = dbUtil.queryForListMap(QUERYSQL);
         System.out.println("res:"+rss);
    }
}
