package com.cyb.app.bms.zuul;
import java.util.Date;
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
import org.springframework.util.StringUtils;

import com.cyb.utils.date.DateUnsafeUtil;
import com.cyb.utils.text.ELUtils;
/**
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年5月27日 上午8:49:59
 */
public class JobMain {
	Log log = LogFactory.getLog(JobMain.class);
	static String logUrl="http://zuul.cesfutures.com:8000/user/login?username=iechenyb&password=NFymbfiHvGyG4QhFNnPJGToTw2Bk5NjHv4gKNTrN746PkhYSxh8L%2Fx1QGmSnEDBeZyXcmYAT%2FEXNmkE2gJnb1JbVLRVIX%2FxnOF6W3OCXbJM0D8MlzlqFLNqOCLH6cswKHNTwSYpeiimG5YBg4AWVo0ejBPPyAgOHArZss2EGU9I%3D&code=7986";
	static String rjqyUrl="http://zuul.cesfutures.com:8000/report/job/batchExecuteJob?start=${rq}&end=${rq}&jobKey=DailyInterestJobKey";
	//客户明细计算
	static String khmxUrl="http://zuul.cesfutures.com:8000/report/job/batchExecuteJob?start=${rq}&end=${rq}&jobKey=DailyInterestJobKey";
	
   static String analyseData = "http://zuul.cesfutures.com:8000/report/dailyInterest/qurytDailyInterestList?startDate=20200601&endDate=20200630&curPage=${page}&pageSize=10";
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 登录<br>
	 *创建时间: 2017年7月15日
	 * @throws Exception 
	 */
	public static void 登录(String cookiep) throws Exception{
		//Map<String,Object> param = new HashMap<>();
		Connection con = Jsoup.connect(logUrl);
		con.ignoreContentType(true);
		if(con.get().html().contains("error")||con.get().html().contains("过期")){
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
        if(!StringUtils.isEmpty(cookiep))
        cookie.put("SESSION", cookiep);
        System.out.println("cookie:"+sb.toString());
	}
	static Map<String, String> cookie;
	static String cookieString;
	public static void setCookie( Connection curConnection){
		Iterator<Entry<String, String>> iterCookie = cookie.entrySet().iterator();  
        while(iterCookie.hasNext()){  
            Entry<String, String> entry = iterCookie.next();  
            curConnection.cookie(entry.getKey(), entry.getValue());  
        } 
	}
	
	
	public  static boolean checkSuccess(String rq){
		try {
			登录("dfa6d7ab-bc36-4648-8ac5-78d4f634d46c");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String x = "http://zuul.cesfutures.com:8000/report/job/getAllLogs?ywrq=${rq}&status=&jobKey=DailyInterestJobKey&curPage=1&pageSize=1";
		Map<String,Object> param = new HashMap<>();
		param.put("rq", rq);
		String url = ELUtils.el(x, param);
		boolean goOn = true;
		int count=1;
		while(goOn){
			try {
				Connection con = Jsoup.connect(url);
				setCookie(con);
				con.ignoreContentType(true);
				Document res = con.get();
				System.out.println(param+"->"+res.text());
				if(res.text().contains("成功")){
					goOn=false;
					System.out.println(rq+"成功返回!"+count++);
				}else{
					System.out.println(rq+"继续判断!"+count++);
					Thread.sleep(1000*5);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return goOn;
	}
	static long during = 300;//秒
	static String start1="20200207";
	static String end="20200831";
	static Date newlyDate = DateUnsafeUtil.nextSomeDay(new Date(), -1);
	public static void main(String[] args) {
		try {
			登录("dfa6d7ab-bc36-4648-8ac5-78d4f634d46c");
			
			/*for(int page=1;page<=1;page++){
				Map<String,Object> param = new HashMap<>();
				param.put("page", page);
				String url = ELUtils.el(analyseData, param);
				Connection con = Jsoup.connect(url);
				setCookie(con);
				con.ignoreContentType(true);
				String data = con.get().toString();
				System.out.println(data);
				if(data.contains("8703235")){
					System.out.println("aaaaa"+data.toString());
				}7-31
			}*/
			/*
			 * 日均权益补充计算
			 * int nums = DateUnsafeUtil.daysBetween(start1, end);
			int i=0;
			Date start = DateUnsafeUtil.calendar(start1).getTime();
			for(i=0;i<=nums;i++){
				Date cur = DateUnsafeUtil.nextSomeDay(start, i);
				Map<String,Object> param = new HashMap<>();
				param.put("rq", DateUnsafeUtil.date2long8(cur));
				FileUtils.appendEnter("d:/data/rjqy.log", param.get("rq").toString());
				String url = ELUtils.el(rjqyUrl, param);
				Connection con = Jsoup.connect(url);
				setCookie(con);
				con.ignoreContentType(true);
				System.out.println(param+"->"+con.get());
				//Thread.sleep(1000*during);//独占式等待，效率低
				if(checkSuccess(param.get("rq").toString())){//轮询判断
					continue;
				}
			}*/
			SettlementService.downloadRjqy("20200101", "20201031");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
