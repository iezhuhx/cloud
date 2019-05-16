package com.cyb.app.h2;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月23日
 */
public class H2UrlStratroy implements H2DBAware{
	Log log = LogFactory.getLog(H2UrlStratroy.class);
	static String tcpServerPrix="jdbc:h2:tcp://localhost/";//服务式+绝对路径
	static String embeddedPrix="jdbc:h2:~/";//嵌入式+绝对路径 不加盘符
	static String memoryPrix="jdbc:h2:tcp://localhost/mem:";//内存式+数据库名 不加盘符
	
	public  String getDbUrl(H2DBInfor db){
		if(db.isServer(db)){
			return getUrl(tcpServerPrix,db);
		}else if(db.isEmbed(db)){
			return getUrl(embeddedPrix,db);
		}else{
			return getUrl(memoryPrix,db);
		}
	}
	
	public  String getUrl(String prix,H2DBInfor db){
		return embeddedPrix+db.getPath()+"/"+db.getFileName();
	}
	
}
