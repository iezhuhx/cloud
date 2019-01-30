package com.cyb.app.ribbon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月4日
 */
public class 轮询 {
	Log log = LogFactory.getLog(轮询.class);
	 private static Integer pos = 0;
	    
	    public static String getServer()
	    {
	        // 重建一个Map，避免服务器的上下线导致的并发问题
	        Map<String, Integer> serverMap = 
	                new HashMap<String, Integer>();
	        serverMap.putAll(服务器.serverWeightMap);
	        
	        // 取得Ip地址List
	        Set<String> keySet = serverMap.keySet();
	        ArrayList<String> keyList = new ArrayList<String>();
	        keyList.addAll(keySet);
	        
	        String server = null;
	        synchronized (pos)
	        {
	            if (pos > keySet.size())
	                pos = 0;
	            server = keyList.get(pos);
	            pos ++;
	        }
	        
	        return server;
	    }
}
