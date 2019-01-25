package com.cyb.app.stock;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;

import com.alibaba.fastjson.JSON;
import com.cyb.app.stock.pub.RealQuotation;
import com.cyb.utils.http.MyHttpClient;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月17日
 */
public class QutoesWindow {
	static String defaultCode="sh600868";
	Log log = LogFactory.getLog(QutoesWindow.class);
	public static void main(String[] args) throws IOException {
		/*DrawQutoesUtils
   			 .String2Qutoes(
   					 DrawQutoesUtils
   					 .getOriginQutoes(defaultCode)
   					 ,defaultCode);*/
		System.out.println(MyHttpClient
				.doGet(DrawQutoesUtils.url+defaultCode));
	}
	@SuppressWarnings("unchecked")
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 创建job的时候传递参数！<br>
	 *创建时间: 2017年7月15日
	 *@param data
	 *@throws IOException
	 */
	public static void execTask(JobDataMap data) throws IOException {
		List<String> shCodes=(List<String>) data.get("sh");
		List<String> szCodes=(List<String>) data.get("sz");
	    for(String shcode:shCodes){
	    	 RealQuotation qutoe = DrawQutoesUtils
	    			 .String2Qutoes(
	    					 DrawQutoesUtils
	    					 .getOriginQutoes(shcode)
	    					 ,shcode);
	    	 if(Double.valueOf(qutoe.getOpen())>0.0){
	    		 System.out.println("上证行情->"+qutoe.getName()+":"+JSON.toJSONString(qutoe));
	    	 }
			 
	    }
	    for(String szcode:szCodes){
	    	 RealQuotation qutoe = DrawQutoesUtils
	    			 .String2Qutoes(
	    					 DrawQutoesUtils
	    					 .getOriginQutoes(szcode)
	    					 ,szcode);
	    	 if(Double.valueOf(qutoe.getOpen())>0.0){
	    		 System.out.println("深证行情->"+qutoe.getName()+":"+JSON.toJSONString(qutoe));
	    	 }
	    }
	}
}
