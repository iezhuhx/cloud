package com.cyb.app.bms;

import java.sql.SQLException;

import org.h2.jdbcx.JdbcConnectionPool;

import com.cyb.app.h2.H2Common2DbUtils;
import com.cyb.app.h2.H2DBConnectionPool;
import com.cyb.app.h2.H2DBInfor;

/**
 * 描述：sql
 *
 * @author iechenyb
 * @create 2019年6月3日15:04:52
 */
public class BMSSql {
	static String tName = "sendlog";
    public static String CREATESQL="create table "+tName+" (rq varchar,sj varchar)";//日期和时间
    public static String QUERYSQL="select count(1) as total from "+tName+"  where rq='20120202'";
    public static String DELETESQL="DELETE FROM "+tName+" ";
    public static String INSERTSQL ="insert into "+tName+"  values('20120202','23212')";
    static JdbcConnectionPool pool = H2DBConnectionPool.getJDBCConnectionPool();
    public static void main(String[] args) throws SQLException {
        H2Common2DbUtils.createTable("sendlog", CREATESQL);
    }
}
