package com.cyb.app.reptile;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.cyb.utils.computer.CmdUtils;
import com.cyb.utils.http.HttpsClient;
import com.cyb.utils.text.ELUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月4日
 */
public class XicidailiUtils {
	Log log = LogFactory.getLog(XicidailiUtils.class);
	static Map<String, String> cookie;
	static String page = "https://www.xicidaili.com/nn/1";
	static String pageN = "https://www.xicidaili.com/nn/${page}";
	String  agent="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)"
			+ "  Chrome/56.0.2924.87 Safari/537.36" ;
	public static void main(String[] args) throws IOException {
		HttpsClient.get(page,ProxyRandomUtils.findUseableProxyRandom());
		
		ProxySettingUtils.setHttpsServiceProxy(ProxyRandomUtils.findUseableProxyRandom());
		Connection con = Jsoup.connect(page)
				//.userAgent(agent)
        		.ignoreContentType(true)
        		.ignoreHttpErrors(true)
        		.timeout(10000);
		Response response = con.execute();  
        cookie = response.cookies(); 
        StringBuffer sb = new StringBuffer();
        for(String key:cookie.keySet()){
        	sb.append(key+"="+cookie.get(key)+";");
        }
        System.out.println(sb.toString());
        /*System.setProperty("https.proxySet", "true");
        System.getProperties().put("https.proxyHost", ip);
        System.getProperties().put("https.proxyPort", port);*/
        
        for(int i=1;i<=3545;i++){//3545
	        Map<String, Object> data = new HashMap<String, Object>();  
	        data.put("page", String.valueOf(i));
	        String url = ELUtils.el(pageN, data);
	        System.out.println(data+","+url);
	        Connection conSearch = Jsoup.
	        		connect(url)
	        		//.userAgent(agent)
	        		.ignoreContentType(true)
	        		.ignoreHttpErrors(true)
	        		.timeout(3000);  
	        setCookie(conSearch);
	        Document doc = conSearch.get(); 
	        Elements trs = doc.getElementById("ip_list").parent().getElementsByTag("tr");
	        String ip = trs.get(1).getElementsByTag("td").get(1).html();
	        String port = trs.get(1).getElementsByTag("td").get(2).html();
	        ProxyInfor proxy = new ProxyInfor(ip, Integer.valueOf(port));
	        try {
				if(CmdUtils.telnet(proxy)){
					System.out.println(proxy);
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
        }
	}
	
	public static void setCookie( Connection curConnection){
		Iterator<Entry<String, String>> iterCookie = cookie.entrySet().iterator();  
        while(iterCookie.hasNext()){  
            Entry<String, String> entry = iterCookie.next();  
            curConnection.cookie(entry.getKey(), entry.getValue());  
        } 
	}
}
