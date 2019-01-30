package com.cyb.app.ribbon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月4日
 */
public class 加权随机 {
	Log log = LogFactory.getLog(加权随机.class);
	 public static String getServer()
	    {
	        // 重建一个Map，避免服务器的上下线导致的并发问题
	        Map<String, Integer> serverMap = 
	                new HashMap<String, Integer>();
	        serverMap.putAll(服务器.serverWeightMap);
	        
	        // 取得Ip地址List
	        Set<String> keySet = serverMap.keySet();
	        Iterator<String> iterator = keySet.iterator();
	        
	        List<String> serverList = new ArrayList<String>();
	        while (iterator.hasNext())//重新构造一个新数组 长度所有权重值之和
	        {
	            String server = iterator.next();
	            int weight = serverMap.get(server);
	            for (int i = 0; i < weight; i++)
	                serverList.add(server);
	        }
	        
	        java.util.Random random = new java.util.Random();
	        int randomPos = random.nextInt(serverList.size());
	        return serverList.get(randomPos);
	    }
}
