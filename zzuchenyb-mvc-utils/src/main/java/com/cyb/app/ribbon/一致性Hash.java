package com.cyb.app.ribbon;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.cyb.utils.random.RandomUtils;
/**
 *作者 : iechenyb<br>
 *类描述: [0,232-1]中有4294967296个数字<br>
 *不带虚拟节点的一致性Hash算法<br>
 *创建时间: 2019年1月29日
 *string本身的hashcode沒有意義，需要自定义hashcode生成策略
 *
 */
public class 一致性Hash {
	Log log = LogFactory.getLog(一致性Hash.class);
	static int nodes =2<<3;
	 /**
     * 待添加入Hash环的服务器列表
     */
    private static List<String> servers = new ArrayList<>(nodes);
    private static Map<String,Integer> statis = new LinkedHashMap<>();//统计分布情况
    
    /**
     * key表示服务器的hash值，value表示服务器的名称
     */
    private static SortedMap<Integer, String> sortedMap = 
            new TreeMap<Integer, String>();
    
    /**
     * 程序初始化，将所有的服务器放入sortedMap中
     */
    static
    {
        /*for (int i = 0; i < servers.length; i++)
        {
            int hash = getHash(servers[i]);
            System.out.println("[" + servers[i] + "]加入集合中, 其Hash值为" + hash);
            sortedMap.put(hash, servers[i]);
            statis.put(servers[i], 0);
        }*/
    	for (int i = 0; i < nodes; i++){
    		String server = "192.168.0."+i+":111";
    		servers.add(server);
            int hash = getHash(server);
            System.out.println((i+1)+"-->[" + server + "]加入集合中, 其Hash值为" + hash);
            sortedMap.put(hash, server);
            statis.put(server, 0);
        }
        System.out.println();
    }
    
    /**
     * CRC32_HASH、FNV1_32_HASH、KETAMA_HASH（MemCache）
     * 使用FNV1_32_HASH算法计算服务器的Hash值,这里不使用重写hashCode的方法，最终效果没区别 
     * 
     * 单纯比较下散列函数的计算效率：
	   CRC32_HASH:3266
	   KETAMA_HASH:7500
	   FNV1_32_HASH:375
	   NATIVE_HASH:187
	   MYSQL_HASH:500
	   NATIVE_HASH > FNV1_32_HASH > MYSQL_HASH > CRC32_HASH > KETAMA_HASH
     */
    private static int getHash(String str)
    {
        final int p = 16777619;
        int hash = (int)2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }
    
    /**
     * 得到应当路由到的结点
     */
    private static String getServer(String node)
    {
        try {
			// 得到带路由的结点的Hash值
			int hash = getHash(node);
			// 得到大于该Hash值的所有Map key=hash value=server
			SortedMap<Integer, String> subMap = 
			        sortedMap.tailMap(hash);
			// 第一个Key就是顺时针过去离node最近的那个结点
			Integer i = subMap.firstKey();
			// 返回对应的服务器名称
			String res =  subMap.get(i);
			return res;
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
        
    }
    public static void hashTest(){
    	//随机生成应用ip列表和端口号
    	List<String> ips =new ArrayList<>();
    	boolean show = false;
    	for(int i=1;i<=500;i++){
    		String ip = RandomUtils.getIp()+":"+RandomUtils.getNum(4, 6);
    		ips.add(ip);
    		String server =getServer(ip);
    		if(show){
    			System.out.println("[" + ip+ "]的hash值为" + 
                     getHash(ip) + ", 被路由到结点[" + server +"]");
    		}
    		if(!StringUtils.isEmpty(server)){
    			statis.put(server, statis.get(server)+1);
    		}
    	}
    	//System.out.println("分布统计："+statis);
    	for(String ip:statis.keySet()){
    		System.out.println(ip+"->"+statis.get(ip));
    	}
    }
    public static void main(String[] args)
    {
    	hashTest();
        
    }
}
