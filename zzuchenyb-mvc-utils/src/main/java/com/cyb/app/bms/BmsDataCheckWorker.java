package com.cyb.app.bms;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月28日
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.Assert;

import com.cyb.utils.date.DateUtil;
import com.cyb.utils.text.ELUtils;

@SuppressWarnings("deprecation")
public class BmsDataCheckWorker {
	Log log = LogFactory.getLog(BmsDataCheckWorker.class);
	static String 登录地址="http://bms.kiiik.com/kiiikoa/login.do?username=${name}&password=${password}";
	static String 交易客户端="http://bms.kiiik.com/kiiikoa/settleAccount/balanceReport.do?method=loginStatisticslist&navigation=%B5%C7%C2%BC%D7%B4%CC%AC%CD%B3%BC%C6";
	static String 成交排名="http://bms.kiiik.com/kiiikoa/settleAccount/balanceReport.do?method=postionRankQufenlist";
	static Map<String, String> cookie;
	static String cookieString;
	static String lastDay;

	public static void main(String[] args) throws Exception {
		登录();
		String lastDay = DateUtil.date2long8(DateUtil.preDate()).toString();
		交易客户端数据(lastDay);
		lastDay = DateUtil.format(DateUtil.preDate(), "yyyy/MM/dd");
		成交排名(lastDay);
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 登录<br>
	 *创建时间: 2017年7月15日
	 * @throws Exception 
	 */
	public static void 登录() throws Exception{
		Map<String,Object> param = new HashMap<>();
		param.put("name", "gongweilin");
		param.put("password", "my.123456");
		登录地址 = ELUtils.el(登录地址, param);
		Connection con = Jsoup.connect(登录地址);
		if(con.get().html().contains("error")){
			System.out.println("登录失败！");
			throw new Exception("登录失败");
		}else{
			System.out.println("登录成功！");
		}
        Response response = con.execute();  
        cookie = response.cookies(); 
        StringBuffer sb = new StringBuffer();
        for(String key:cookie.keySet()){
        	sb.append(key+"="+cookie.get(key)+";");
        }
        System.out.println("cookie:"+sb.toString());
	}
	
	public static void setCookie( Connection curConnection){
		Iterator<Entry<String, String>> iterCookie = cookie.entrySet().iterator();  
        while(iterCookie.hasNext()){  
            Entry<String, String> entry = iterCookie.next();  
            curConnection.cookie(entry.getKey(), entry.getValue());  
        } 
	}
	
	
	public static void 交易客户端数据(String lastDay) throws IOException{
		//会话保持
        Connection conSearch = Jsoup.connect(交易客户端);  
        setCookie(conSearch);
        Document doc = conSearch.get();  
		Elements eles = doc.getElementById("resultTable").getElementsByTag("tr");
		String year = eles.get(1).children().get(2).html();
		Assert.isTrue(year.equals(lastDay));
		System.err.println("交易客户端ok！");
	}
	
	public static void 成交排名(String lastDay) throws IOException{
		Connection conSearch = Jsoup.connect(成交排名); 
		setCookie(conSearch);
		Map<String, String> data = new HashMap<String, String>();  
        data.put("transactionDate", lastDay);
        conSearch.data(data);
        Document doc = conSearch.get();
        Elements eles = doc.getElementById("resultTable").getElementsByTag("tr");
        Assert.isTrue(eles.size()>1);
        System.err.println("成交排名ok！");
	} 
	
	public static String httpGet(String url,String cookie) throws IOException{
        //获取请求连接
        Connection con = Jsoup.connect(url);
        //请求头设置，特别是cookie设置
        con.header("Accept", "text/html, application/xhtml+xml, */*"); 
        con.header("Content-Type", "application/x-www-form-urlencoded");
        con.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))"); 
        con.header("Cookie", cookieString);
        //解析请求结果
        Document doc=con.get(); 
        //获取标题
        System.out.println(doc.title());
        return doc.toString();
        
    }
	
	public static String httpPost(String url,Map<String,String> map) throws IOException{
        //获取请求连接
        Connection con = Jsoup.connect(url);
        con.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"); 
        con.header("Content-Type", "application/x-www-form-urlencoded");
        con.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))"); 
        //遍历生成参数
        if(map!=null){
            for (Entry<String, String> entry : map.entrySet()) {     
           //添加参数
            con.data(entry.getKey(), entry.getValue());
           } 
        }
        //插入cookie（头文件形式）
        con.header("Cookie", cookieString);
        setCookie(con);
        Document doc = con.post();  
        System.out.println(doc.html());
        return doc.toString();
    }
}
