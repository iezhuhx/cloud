package com.cyb.utils.http;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年12月21日
 */
public class HttpClientUtils {
	static RestTemplate restTemplate = null;

	protected static void init() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setReadTimeout(5000);
		requestFactory.setConnectTimeout(5000);

		// 添加转换器
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
		messageConverters.add(new MappingJackson2HttpMessageConverter());

		restTemplate = new RestTemplate(messageConverters);
		restTemplate.setRequestFactory(requestFactory);
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
	}

	Log log = LogFactory.getLog(HttpClientUtils.class);

	/**
	 * post请求
	 * 
	 * @param url
	 * @param formParams
	 * @return
	 */
	public static String doPost(String url, Map<String, String> formParams) {
		init();		
		if (CollectionUtils.isEmpty(formParams)) { return doPost(url); }
		try {
			MultiValueMap<String, String> requestEntity 
			= new LinkedMultiValueMap<>();
			for(String key:formParams.keySet()){
				requestEntity.add(key, formParams.get(key));
			}
			return restTemplate.postForObject(url, requestEntity, String.class);
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @return
	 */
	public static String doPost(String url) {
		
		try {
			return restTemplate.postForObject(url, HttpEntity.EMPTY, String.class);
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @return
	 */
	public static String doGet(String url) {
		init();
		try {
			return restTemplate.getForObject(url, String.class);
		} catch (Exception e) {
		}
		return "";
	}
}
