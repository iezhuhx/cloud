package com.cyb.app.h2;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.tools.Server;

import com.cyb.app.h2.usecase.H2URL;
import com.cyb.utils.computer.CmdUtils;

/**
 *作者 : iechenyb<br>
 *类描述: 程序启动与关闭h2<br>
 *创建时间: 2019年1月22日
 */
public class H2ServerManager {
	private static Server server;
	private static Server webServer;
	Log log = LogFactory.getLog(H2ServerManager.class);
	
	public static boolean start(String port) {
		boolean needStart = false;
        try {
        	if(CmdUtils.telnet(H2URL.server, H2URL.port)){
        		System.out.println("TCP端口服务"+H2URL.port+"已经启动！");
        	}else{
        		System.out.println("正在启动h2 TCP服务...");
        		server = Server.createTcpServer(
                    new String[] { 
                    		"-tcp", 
                    		"-tcpAllowOthers",
                    		"-tcpPort",
                             port }).start();
        		System.out.println("启动h2server成功：" + server.getStatus());
        		needStart = true;
            }
            if(CmdUtils.telnet(H2URL.server, H2URL.webPort)){
        		System.out.println("WEB端口服务"+H2URL.webPort+"已经启动！");
        	}else{
        		System.out.println("正在启动h2 web服务...");
        		webServer = Server.createWebServer(new String[] { 
                		"-web", 
                		"-webPort",
                		"8095",
                        "-browser",
                        "-webAllowOthers"}).start();
        		 System.out.println("启动webserver成功：" + webServer.getStatus());
        		 needStart = true;
            }
        } catch (SQLException e) {
            System.out.println("启动h2出错：" + e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return needStart;
    }
	public static boolean start() {
		return start(H2URL.port);
	}
    public static void stop() {
        if (server != null) {
            System.out.println("正在关闭h2...");
            server.stop();
            System.out.println("关闭成功.");
        }
        if (webServer != null) {
            System.out.println("正在关闭h2web...");
            webServer.stop();
            System.out.println("关闭h2web成功.");
        }
    }

    public static void crudTcpTest() {
        try {
            Class.forName("org.h2.Driver");
            Connection conn = null;/*DriverManager
            		.getConnection("jdbc:h2:./h2db/sxaz42b4", "sa", "sa");*/
            conn = H2DBConnectionPool.getJDBCConnectionPool().getConnection();
            Statement stat = conn.createStatement();
            stat.execute("CREATE TABLE TEST(NAME VARCHAR)");
            stat.execute("INSERT INTO TEST VALUES('菩提树下的杨过')");
            stat.execute("INSERT INTO TEST VALUES('http://yjmyzz.cnblogs.com/')");
            ResultSet result = stat.executeQuery("select name from test ");
            int i = 1;
            System.out.println("===================================");
            System.out.println("读取记录条数如下：");
            while (result.next()) {
                System.out.println(i++ + ":" + result.getString("name"));
            }
            System.out.println("===================================");
            stat.execute("DROP TABLE TEST");
            result.close();
            stat.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void h2Test() {
       // start();
    	crudTcpTest();
        
       /* crudTest(db);
        
        crudTest(db);*/
	        
        stop();
    }
    public static void main(String[] args) {
		h2Test();
	}
}
