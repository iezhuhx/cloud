package com.kiiik.utils;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年8月7日上午8:50:38
 */
public class ConnectionUtils {
	Log log = LogFactory.getLog(ConnectionUtils.class);
	/*public static void main(String[] args) {
		new ConnectionUtils();
	}*/
	public ConnectionUtils(){
	    //String url = "jdbc:h2:tcp://localhost/~/te;AUTO_SERVER=true";  
		
		String url = "jdbc:h2:tcp://192.168.108.221:6678/~/vasData/apache-tomcat-7.0.56/webapps/VASDataCenter/data/cashDb;mv_store=false;MULTI_THREADED=1";  
	    String jdbcDriver = "org.h2.Driver";  
	    String user = "vascenter";  
	    String password = "cesfkiiik";  
	   /* String url = "jdbc:oracle:thin:@192.168.108.231:1521:KIIIK";  
	    String jdbcDriver = "oracle.jdbc.driver.OracleDriver";  
	    String user = "kiiik";  
	    String password = "cesfkiiik_99";*/  
        try {
			Class.forName(jdbcDriver);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
	    try {  
	        Connection conn = DriverManager.getConnection(url, user, password);  
	        System.out.println(conn);
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } finally {  
	    }  
	}
	
	
}
