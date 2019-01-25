package com.cyb.app.h2;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.jdbcx.JdbcConnectionPool;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月22日
 */
public class H2DBConnectionPool {
	Log log = LogFactory.getLog(H2DBConnectionPool.class);
	private  JdbcConnectionPool pool=null;
	
	public H2DBConnectionPool(){
		pool=JdbcConnectionPool.create("jdbc:h2:~/test1","sa","");
		pool.setLoginTimeout(10000);//建立连接超时时间  
        pool.setMaxConnections(100);//建立连接最大个数  
	}
	
	public H2DBConnectionPool(String url,String name,String password){
		pool=JdbcConnectionPool.create(url, name, password);
		pool.setLoginTimeout(10000);//建立连接超时时间  
        pool.setMaxConnections(100);//建立连接最大个数  
	}
	
	//获取连接池
	public  JdbcConnectionPool getJDBCConnectionPool(String url,String name,String password){  
		JdbcConnectionPool pool=JdbcConnectionPool.create(url, name, password);
		pool.setLoginTimeout(10000);//建立连接超时时间  
        pool.setMaxConnections(100);//建立连接最大个数
        return pool;
    }
	//获取连接池
	public  static JdbcConnectionPool getJDBCConnectionPool(H2DBInfor db){  
		JdbcConnectionPool pool=JdbcConnectionPool.create(H2UrlStratroy.getDbUrl(db), db.getUsername(), db.getPassword());
		pool.setLoginTimeout(10000);//建立连接超时时间  
        pool.setMaxConnections(100);//建立连接最大个数
        return pool;
    }
    public  JdbcConnectionPool getJDBCConnectionPool(){  
        return pool;  
    }  
      
    public  Connection getConnection(){  
        try {  
            return pool.getConnection();  
        } catch (SQLException e) {  
            System.out.println("DBConnectionPool create connection is failed:");  
            e.printStackTrace();  
        }finally{  
            pool.dispose();        //释放空闲连接  
        }  
        return null;  
    }  
      
    public static void main(String[] args) throws SQLException { 
    	System.out.println(new H2DBConnectionPool().getJDBCConnectionPool().getConnection());
    }  
}
