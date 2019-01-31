package com.cyb.utils.hash;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.utils.encrypt.MD5Util;
/**
 *作者 : iechenyb<br>
 *类描述: app/ribbon 查看负载均衡策略<br>
 *创建时间: 2019年1月29日
 */
public class HashUtils {
	Log log = LogFactory.getLog(HashUtils.class);
	
	public static long md5Hash(String key) throws Exception {
		byte[] bKey = MD5Util.md5Encode(key).getBytes();
        //具体的哈希函数实现细节--每个字节 & 0xFF 再移位
        long result = 
        		  ((long) (bKey[3] & 0xFF) << 24)
                | ((long) (bKey[2] & 0xFF) << 16
                | ((long) (bKey[1] & 0xFF) << 8) 
                | (long) (bKey[0] & 0xFF));
        return result & 0xffffffffL;
    }
	
	public static int fnv1_32_hash(String str)
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
	public static void main(String[] args) throws Exception {
		String server="192.168.16.221:8080";
		System.out.println(md5Hash(server));
		System.out.println(fnv1_32_hash(server));
	}
}
