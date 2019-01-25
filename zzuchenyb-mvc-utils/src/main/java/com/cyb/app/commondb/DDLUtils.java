package com.cyb.app.commondb;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2019年1月23日
 */
public class DDLUtils {
	Log log = LogFactory.getLog(DDLUtils.class);
	public static boolean isExist(Connection conn,String tableName){
		try {
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rs = meta.getTables(
					null, 
					null, 
					tableName.toUpperCase(),
					null);
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean createTable(Connection con,String tableName,String[] items){
		try {
			DatabaseMetaData meta = con.getMetaData();
			ResultSet rsTables = meta.getTables(null, null, tableName,
	                new String[] { "TABLE" });
			Statement stmt = null;
			StringBuilder sql = new StringBuilder();
			if (!rsTables.next()) {
	            stmt = con.createStatement();
	            
	            sql.append(" CREATE TABLE ");
	            if (!StringUtils.isEmpty(tableName)) {
	                sql.append(tableName);
	            }
	            if (items != null && items.length > 0) {
	                sql.append(" ( ");
	                sql.append(" hid VARCHAR(5), ");
	                for (int i = 0;i < items.length;i++) {
	                    sql.append(items[i]);
	                    sql.append(" VARCHAR(100), ");
	                }
	                sql.append("PRIMARY KEY(hid)) ");
	            }
			}
            stmt.execute(sql.toString());
			rsTables.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
}
