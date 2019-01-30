package com.cyb.utils.collection;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.utils.random.RandomUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月29日
 */
public class MapStudy {
	Log log = LogFactory.getLog(MapStudy.class);
	
	public static void main(String[] args) {
		SortedMap<Integer, String> data = new TreeMap<Integer, String>();
		for(int i=1;i<=10;i++){
			Integer num = RandomUtils.getNum(1, 1000);
			data.put(num,String.valueOf(num));
		}
		System.out.println(data);
		System.out.println(data.subMap(100, 200));//查找指定区间大小的数据
		SortedMap<Integer, String> tailMap = data.tailMap(200);//查询大于200所有的数据
		System.out.println(tailMap);
		System.out.println(tailMap.firstKey());//第一个值
		System.out.println(tailMap.lastKey());//最后一个值
	}
}
