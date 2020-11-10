package com.cyb.utils.collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.utils.random.RandomUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月29日  https://www.cs.usfca.edu/~galles/visualization/AVLtree.html
 */
public class MapStudy {
	Log log = LogFactory.getLog(MapStudy.class);
	
	public static void main(String[] args) {
		Map<String,String> hsmap = new HashMap<>();
		
		hsmap.put("周瑜", "周瑜");
		hsmap.put("曹操", "曹操");
		hsmap.put("刘备", "刘备");
		hsmap.put("孙权", "孙权");
		hsmap.put("诸葛亮", "诸葛亮");
		System.out.println(Math.ceil(1.1));//上取整 比自己大的最小整数
		System.out.println(Math.floor(1.1));//下取整  比自己小的最大整数
		System.out.println(Math.ceil(3));//整数时等于自己
		System.out.println(Math.floor(3));//整数时等于自己
		System.out.println("=================");
		System.out.println(Math.ceil(-1.1));//上取整 比自己大的最小整数
		System.out.println(Math.floor(-1.1));//下取整  比自己小的最大整数
		System.out.println(Math.ceil(-3));//整数时等于自己
		System.out.println(Math.floor(-3));//整数时等于自己
		
		
		for(String key:hsmap.keySet()){
			int hashcode = key.hashCode();
			int index = hashcode%8;//曹操与周瑜的index发生了指针碰撞。
			System.out.println(String.format("%s 的hashcode %s,mod is %s", key,hashcode,index));//h&(len-1)
		}
	
	}
	
	
	
	public void sortedMap(){
		SortedMap<Integer, String> data = new TreeMap<Integer, String>();
		for(int i=1;i<=10;i++){
			Integer num = RandomUtils.getNum(1, 1000);
			data.put(num,String.valueOf(num));
		}
		System.out.println(data);
		System.out.println(data.subMap(100, 200));//查找指定区间大小的数据 fromkey-tokey
		
		
		
		SortedMap<Integer, String> tailMap = data.tailMap(200);//查询大于200所有的数据 key
		System.out.println(tailMap);
		System.out.println(tailMap.firstKey());//第一个值
		System.out.println(tailMap.lastKey());//最后一个值
	}
}
