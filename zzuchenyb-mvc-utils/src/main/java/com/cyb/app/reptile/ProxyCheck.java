package com.cyb.app.reptile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.utils.http.MyHttpClient;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月28日
 */
public class ProxyCheck {
	Log log = LogFactory.getLog(ProxyCheck.class);
	static String url = "http://localhost:8088/mvc40/api/clientInfor";
	public static void main(String[] args) {
		for(int i=0;i<50;i++){
			//代理访问
			ProxyInfor proxy= ProxyRandomUtils.findUseableProxyRandom();
			String ip =MyHttpClient.doGet(url,proxy);
			System.out.println("1:"+ip);
			//真实ip正常访问
			//ProxySettingUtils.setServiceProxy(proxy);
			ip =MyHttpClient.doGet(url);
			System.out.println("2:"+ip);
		}
		//结论：走代理时，服务端获取不到客户端的ip信息
	}
}
