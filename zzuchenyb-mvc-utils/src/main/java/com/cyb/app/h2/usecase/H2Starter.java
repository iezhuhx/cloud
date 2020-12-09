package com.cyb.app.h2.usecase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.app.h2.H2ServerManager;
/**
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年12月9日 上午9:54:52
 */
public class H2Starter {
	Log log = LogFactory.getLog(H2Starter.class);
	public static void startTcpAndWebServer() {
		boolean needStart = H2ServerManager.start();
		if(needStart){
			while(true){
				//启动服务之后 ，其他使用到h2的模块无需再启动h2服务。
			}
		}else{
			//已经启动，无需重新启动
			System.out.println("H2的TCP和web服务已经启动!");
		}
	}
}
