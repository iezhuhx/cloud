package com.cyb.utils.cache.gauva;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月31日
 */
public class GauvaCacheUtils {
	Log log = LogFactory.getLog(GauvaCacheUtils.class);
	public static void main(String[] args) {
		RemovalListener<String, String> removalListener = new RemovalListener<String, String>() {
		    public void onRemoval(RemovalNotification<String, String> removal) {
		    	String conn = removal.getValue();
		    	System.out.println("移除值"+conn);
		    }
		};
		Cache<String,String> myCache=CacheBuilder.newBuilder()
				.concurrencyLevel(4)//设置segment数量，并发正比
				.expireAfterAccess(10, TimeUnit.SECONDS)
				.maximumSize(10000)
				.removalListener(removalListener)
				.build();//lru 回收
		myCache.put("name", "iechenyb");
		
		/*
		 * myCache.asMap();
		   myCache.cleanUp();
		 */
		String value = myCache.getIfPresent("name");
		System.out.println("name="+value);
		myCache.invalidate("name");
		value = myCache.getIfPresent("name");
		System.out.println("name="+value);
	}
}
