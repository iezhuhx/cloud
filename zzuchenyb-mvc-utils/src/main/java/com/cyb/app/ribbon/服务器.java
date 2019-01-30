package com.cyb.app.ribbon;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月4日
 */
public class 服务器 {
	Log log = LogFactory.getLog(服务器.class);
	// 待路由的Ip列表，Key代表Ip，Value代表该Ip的权重
    public static HashMap<String, Integer> serverWeightMap = 
            new HashMap<String, Integer>();
    
    static
    {
        serverWeightMap.put("192.168.1.100", 1);
        serverWeightMap.put("192.168.1.101", 1);
        // 权重为4
        serverWeightMap.put("192.168.1.102", 4);
        serverWeightMap.put("192.168.1.103", 1);
        serverWeightMap.put("192.168.1.104", 1);
        // 权重为3
        serverWeightMap.put("192.168.1.105", 3);
        serverWeightMap.put("192.168.1.106", 1);
        // 权重为2
        serverWeightMap.put("192.168.1.107", 2);
        serverWeightMap.put("192.168.1.108", 1);
        serverWeightMap.put("192.168.1.109", 1);
        serverWeightMap.put("192.168.1.110", 1);
    }
}
