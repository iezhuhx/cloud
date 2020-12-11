package com.cyb.app.bms.zuul;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.cyb.app.holiday.util.HolidayH2DbUtils;
/**
 *@Author iechenyb<br>
 *@Desc autoweb 人工登录获取session进行读取<br>
 *@CreateTime 2020年12月11日 下午12:05:50
 */
public class ReportChecker implements Runnable{
	public static final String service ="http://zuul.cesfutures.com:8000/";
	Log log = LogFactory.getLog(ReportChecker.class);
	Map<String, String> cookie;
	
	public ReportChecker(Map<String, String> cookie){
		this.cookie = cookie;
	}
	
	@Override
	public void run() {
		//1 获取上个交易日
		try {
			String preTradeDate = HolidayH2DbUtils.preTradeDay();
			System.out.println(preTradeDate);
			checkDrgl("report/company/overview?ywrq=");
			checkJYTS("report/company/hisTrend?start=&end=") ;
			checkKhmx("report/client/khdtl?date=&paixustr=khqy&shunxu=desc&chaxunstr=&chaxunzhi=&curPage=1&pageSize=10") ;
			checkTradeClient("report/appData/tradeClientPieData?appId=&start=&end=");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//检查当日概览
	public void checkDrgl(String uri) throws IOException, SQLException{
		String preTradeDate = HolidayH2DbUtils.preTradeDay();
		//System.out.println(preTradeDate);
		Connection conn = Jsoup.connect(service+uri).ignoreContentType(true);
		setCookie(conn);
		String res = conn.get().text();
		System.out.println("当日概览检查结果：(true->通过)"+res.contains(preTradeDate));
	}
	//检查经营态势
	public void checkJYTS(String uri) throws IOException, SQLException{
		String preTradeDate = HolidayH2DbUtils.preTradeDay();
		//System.out.println(preTradeDate);
		Connection conn = Jsoup.connect(service+uri).ignoreContentType(true);
		setCookie(conn);
		String res = conn.get().text();
		System.out.println("经营态势检查结果：(true->通过)"+res.contains(preTradeDate));
	}
	//检查客户明细
	public void checkKhmx(String uri) throws IOException, SQLException{
		String preTradeDate = HolidayH2DbUtils.preTradeDay();
		//System.out.println(preTradeDate);
		Connection conn = Jsoup.connect(service+uri).ignoreContentType(true);
		setCookie(conn);
		String res = conn.get().text();
		System.out.println("客户明细检查结果：(true->通过)"+res.contains(preTradeDate));
	}
	
	//检查交易客户端数据
	public void checkTradeClient(String uri) throws IOException, SQLException{
		String preTradeDate = HolidayH2DbUtils.preTradeDay();
		//System.out.println(preTradeDate);
		Connection conn = Jsoup.connect(service+uri).ignoreContentType(true);
		setCookie(conn);
		String res = conn.get().text();
		System.out.println("交易客户端检查结果：(true->通过)"+res.contains(preTradeDate));
	}
	
	public  void setCookie( Connection curConnection){
		Iterator<Entry<String, String>> iterCookie = cookie.entrySet().iterator();  
        while(iterCookie.hasNext()){  
            Entry<String, String> entry = iterCookie.next();  
            curConnection.cookie(entry.getKey(), entry.getValue());  
        } 
	}
}
