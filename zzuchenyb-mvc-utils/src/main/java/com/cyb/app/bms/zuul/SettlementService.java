package com.cyb.app.bms.zuul;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyb.utils.file.FileUtils;
import com.cyb.utils.text.ELUtils;
/**
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年11月20日 下午1:06:13
 */
public class SettlementService extends JobMain {
	Log log = LogFactory.getLog(SettlementService.class);
	static String rjqyUrl="http://zuul.cesfutures.com:8000/report/dailyInterest/qurytDailyInterestList?startDate=20200101&endDate=20201030&curPage=${page}&pageSize=2000";
	
	public static void downloadRjqy(String yyyymmdd1,String yyyymmdd2){
		 int nums = 31;//608
			int i=1;
			for(i=1;i<=nums;i++){
				Map<String,Object> param = new HashMap<>();
				param.put("page", i);
				try {
					//FileUtils.appendEnter("d:/data/rjqy20(01-10).csv", param.get("rq").toString());
					String url = ELUtils.el(rjqyUrl, param);
					Connection con = Jsoup.connect(url);
					setCookie(con);
					con.ignoreContentType(true);
					con.timeout(10000000);
					String res  = con.get().text();
					JSONObject dta = JSON.parseObject(res);
					JSONArray arr = dta.getJSONObject("d").getJSONArray("list");
					System.out.println(param+"->"+dta.getJSONObject("d").getJSONArray("list"));
					for(int j=0;j<arr.size();j++){
						JSONObject tmp = arr.getJSONObject(j);
						FileUtils.appendEnter("d:/data/rjqy20(01-10)-2000.csv", tmp.getString("customer_account")
								+","+tmp.getString("is_work_day")
								+","+tmp.getString("expendablefund")
								+","+tmp.getString("transactionamount")//交易日日均
								+","+tmp.getString("closingbalance")
								
					);
					}
				} catch (IOException e) {
					e.printStackTrace();
					
				}
				
			}
	}
				
}
