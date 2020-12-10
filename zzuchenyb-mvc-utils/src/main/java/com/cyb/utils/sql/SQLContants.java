package com.cyb.utils.sql;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
/**
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年12月10日 上午9:24:53
 */
public class SQLContants {
	public static Set<String> filterProperty = new HashSet<>();//忽略属性
    //https://blog.csdn.net/qq_35448976/article/details/78799081
	public static Map<String,String> COLUMNSTYPEMAP = new LinkedHashMap<>();
	
	static {
		filterProperty.add("serialVersionUID");
		COLUMNSTYPEMAP.put("CHAR", " varchar(4) default '' ");
		COLUMNSTYPEMAP.put("SHORT", " int default 0 ");
		COLUMNSTYPEMAP.put("INT", " int default 0 ");
		COLUMNSTYPEMAP.put("INTEGER", " int default 0 ");
		COLUMNSTYPEMAP.put("DOUBLE", " double(4) default 0.0 ");
		COLUMNSTYPEMAP.put("FLOAT", " double(4) default 0.0 ");
		COLUMNSTYPEMAP.put("STRING", " varchar(500) default '' ");
		COLUMNSTYPEMAP.put("LONG", " bigint default 0 ");
	}
	
}
