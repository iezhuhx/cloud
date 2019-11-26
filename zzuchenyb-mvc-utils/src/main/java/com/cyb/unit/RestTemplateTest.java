package com.cyb.unit;
import com.cyb.utils.bean.RMap;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.utils.http.HttpClientUtils;
import org.apache.poi.util.SystemOutLogger;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月21日
 */
public class RestTemplateTest {
	Log log = LogFactory.getLog(RestTemplateTest.class);
	//https://www.easy-mock.com
	@Test
	public void get(){
		String url="https://www.easy-mock.com/mock/5d0b1c4cbcff841ec1ef37b8/example/getAuthorName";
		String res = HttpClientUtils.doGet(url);
		System.out.println("result is "+res);//需要启动spring容器
	}

	@Test
	public void post(){
		String url="http://fyssov2.ceair.com/SignOn.aspx?ActionUrl=http%3a%2f%2ffyb2e.ceair.com%2fDefault.aspx";
		Map data = RMap.build()
				.put("UID","aaa")
				.put("Password","*****").put("Button1","")
				.put("_action","~/login/Form.aspx").put("txtcode","")
				.put("__VIEWSTATE","/wEPDwULLTE3MTIwMDY4NDUPZBYCAgEPZBYEAgEPZBYCAgIPDxYCHgdWaXNpYmxlaGRkAgIPZBYCAgEPFgIfAGhkZP6CY6kXLAmYaP6W38MGk3hIwjeG")
				.put("__EVENTVALIDATION","/wEWBQLWkfi7CQKrr964CQKB847vBgLSxeCRDwKM54rGBuZCXJoFU6w/54FmVOvtrNy6WR/f");
	    String str = HttpClientUtils.doPost(url,data);
		System.out.println("post:"+str);
	}
}
