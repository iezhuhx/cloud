package com.cyb.app.bms;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.client.DefaultHttpClient;
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
import org.quartz.SchedulerException;

import com.cyb.app.holiday.HolidayH2DbUtils;
import com.cyb.app.task.QuartzManager;
import com.cyb.utils.bean.RThis;
import com.cyb.utils.date.DateUnsafeUtil;
import com.cyb.utils.http.MyHttpClient;
import com.cyb.utils.text.ELUtils;

@SuppressWarnings("deprecation")
public class BmsDataCheckWorker {
	Log log = LogFactory.getLog(BmsDataCheckWorker.class);
	static String 登录地址="http://bms.kiiik.com/kiiikoa/login.do?username=${name}&password=${password}";
	static String 交易客户端="http://bms.kiiik.com/kiiikoa/settleAccount/balanceReport.do?method=loginStatisticslist&navigation=%B5%C7%C2%BC%D7%B4%CC%AC%CD%B3%BC%C6";
	static String 成交排名="http://bms.kiiik.com/kiiikoa/settleAccount/balanceReport.do?method=postionRankQufenlist";
	static String 交易概览="http://bms.kiiik.com/kiiikoa/settleAccount/balanceReport.do?method=queryCompanyTrades";
	static String 交易排名="http://bms.kiiik.com/kiiikoa/settleAccount/balanceReport.do?method=positionRankingList";
	static String 持仓排名="http://bms.kiiik.com/kiiikoa/settleAccount/balanceReport.do?method=queryPostionRank";
	static Map<String, String> cookie;
	static String cookieString;
	static String lastDay;
	
	public static void main(String[] args) throws Exception {
		//start();
		execTask();
	}
	
	public static void start() throws ClassNotFoundException, SchedulerException{
        String job_name = "动态任务调度";  
        String cron = "*/10 * * * * ?"; 
        QuartzManager.addJob(job_name, BMSJob.class, cron);  
    }
	public static void execTask() throws Exception{
		DateUnsafeUtil.showMonthCal();
		登录();
		String lastTrade = HolidayH2DbUtils.preTradeDay(new Date());
		String lastDay = DateUnsafeUtil.format(DateUnsafeUtil.calendar(lastTrade).getTime(), "yyyyMMdd");
		System.out.println("ywrq="+lastDay);
		交易客户端数据(lastDay);
		lastDay = DateUnsafeUtil.format(lastTrade, "yyyy/MM/dd");
		成交排名(lastDay);
		交易概览(lastDay);
		交易排名(lastDay);//增值服务中心抓取
		//持仓排名(lastDay);
	}
	
	//慢 超时失败 不做校验
	/**
	 * 同一个连接 既是会话保持
	 */
	public static void 持仓排名(String lastDay) throws IOException {
		Map<String,String> param = new HashMap<>();
		param.put("name", "gongweilin");
		param.put("password", "my.123456");
		RThis<DefaultHttpClient> htl = MyHttpClient.login(登录地址,param);
		System.out.println(htl);
		Map<String, String> data = new HashMap<String, String>();  
	    data.put("transactionDate", lastDay);
	    data.put("sortflag", "0");
	    data.put("order", "");
	    data.put("chaxunPro", "");
	    data.put("findtext", "");
		String res = MyHttpClient.doPost(持仓排名,data, htl.getD());
		System.out.println("hh:"+res);
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
        //System.out.println("cookie:"+sb.toString());
	}
	
	{Connection conSearch = Jsoup.connect(持仓排名).timeout(30*1000); 
	setCookie(conSearch);
	Map<String, String> data = new HashMap<String, String>();  
    data.put("transactionDate", lastDay);
    data.put("sortflag", "0");
    data.put("order", "");
    data.put("chaxunPro", "");
    data.put("findtext", "");
    Map<String, String> header = new HashMap<String, String>();
    header.put("Host", "http://bms.kiiik.com");
    header.put("User-Agent", "  Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0");
    header.put("Accept", "  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    header.put("Accept-Language", "zh-cn,zh;q=0.5");
    header.put("Accept-Charset", "  UTF-8,utf-8;q=0.7,*;q=0.7");
    header.put("Connection", "keep-alive");
    header.put("Content-Type", "application/x-www-form-urlencoded");
    conSearch.data(data);
    Document doc;
	try {
		doc = conSearch.headers(header).timeout(30*1000).get();
	
    System.out.println(doc.html());
    Elements eles = doc
    		.getElementById("resultTable")
    		.getElementsByTag("tr");
    if(eles.size()>1){
		System.err.println(lastDay+"->持仓排名ok！");
	}else{
		System.err.println(lastDay+"->持仓排名异常！");
	}	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}
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
		//Assert.isTrue();
		if(year.equals(lastDay)){
			System.err.println(year+"->交易客户端ok！");
		}else{
			System.err.println(year+"->交易客户端异常！");
		}
	}
	
	public static void 交易概览(String lastDay) throws IOException{
		Connection conSearch = Jsoup.connect(交易概览); 
		setCookie(conSearch);
		Map<String, String> data = new HashMap<String, String>();  
        data.put("transactionDate", lastDay);
        conSearch.data(data);
        Document doc = conSearch.get();
        String year = doc.getElementById("resultTable1").parent().getElementsByTag("tr").get(1).getElementsByTag("td").get(0).html();
        if(year.equals(lastDay.replaceAll("/", ""))){
			System.err.println(year+"->交易概览ok！");
		}else{
			System.err.println(year+"->交易概览异常！");
		}
	}
	
	public static void 成交排名(String lastDay) throws IOException{
		Connection conSearch = Jsoup.connect(成交排名); 
		setCookie(conSearch);
		Map<String, String> data = new HashMap<String, String>();  
        data.put("transactionDate", lastDay);
        conSearch.data(data);
        Document doc = conSearch.get();
        Elements eles = doc.getElementById("resultTable").getElementsByTag("tr");
        if(eles.size()>1){
        	System.err.println(lastDay+"->成交排名ok！");
        }else{
        	System.err.println(lastDay+"->成交排名异常！");
        }
	} 
	public static void 交易排名(String lastDay) throws IOException{
		Connection conSearch = Jsoup.connect(交易排名); 
		setCookie(conSearch);
		Map<String, String> data = new HashMap<String, String>();  
        data.put("transactionDate", lastDay);
        conSearch.data(data);
        Document doc = conSearch.get();
        Elements eles = doc.getElementById("resultTable").getElementsByTag("tr");
        if(eles.size()>1){
        	System.err.println(lastDay+"->交易排名ok！");
        }else{
        	System.err.println(lastDay+"->交易排名异常！");
        }
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
