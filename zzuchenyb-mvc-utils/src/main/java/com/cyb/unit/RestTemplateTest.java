package com.cyb.unit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.cyb.utils.http.HttpClientUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月21日
 */
public class RestTemplateTest {
	Log log = LogFactory.getLog(RestTemplateTest.class);
	@Test
	public void get(){
		String url="http://192.168.108.224:8089/rsa/getPublicKey";
		String res = HttpClientUtils.doGet(url);
		System.out.println("result is "+res);//需要启动spring容器
	}
}
