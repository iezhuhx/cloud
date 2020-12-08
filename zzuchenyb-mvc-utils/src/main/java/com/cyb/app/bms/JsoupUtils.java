package com.cyb.app.bms;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
/**
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年11月24日 下午2:30:12
 */
public class JsoupUtils {
	Log log = LogFactory.getLog(JsoupUtils.class);
	
	public static Connection getConnection(String url){
		Connection con = Jsoup.connect(url);
		con.ignoreContentType(true);
		con.timeout(10000000)
       // .proxy(ip, port, null)
       // .data(map)
        .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");//WebAgent.getWebAgent()
		return con;
	}
}
