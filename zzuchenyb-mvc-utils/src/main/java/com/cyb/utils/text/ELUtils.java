package com.cyb.utils.text;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月27日
 */
public class ELUtils {
	Log log = LogFactory.getLog(ELUtils.class);
	public static void main(String[] args) {
		Map<String,Object> map = new HashMap<String,Object>(2);  
	    map.put("name", "Jame Gosling");  
	    map.put("alias", "Rod Johnson"); 
	    //${{name}},${name}}}},
	    String words = "${name} did a great job, so ${alias} did,${aaa}."; 
		el(words,map);
		el1(words,map);
		String re = "\\[([^\\]]+)\\]";//[  (])+ ]
	    String str = "[您好]，abcdefg，[abc]] , [[abc]]";
	    // 您好  abc [abc
	    Pattern p = Pattern.compile(re);
	    Matcher m = p.matcher(str);
	    while(m.find()){
	    	System.out.println(m.group(1));
	    }

	}
	/**
	 * \\$ 转义 \\{转义   \\转义} ${
	 * 当{多个时 无法解析  当}多个时可以解析，第一个}被替换，其他保留原始值
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日
	 *@param words
	 *@param param
	 *@return
	 */
	public static String el(String words,Map<String,Object> param){
		String regex = "\\$\\{[^\\}]+\\}"; //^\\} 匹配第一个}
	    Pattern p = Pattern.compile(regex);  
	    Matcher m = p.matcher(words);  
	    String g;  
	    while (m.find()) {  
	        g = m.group();  
//	        System.out.println("被替换的内容："+g);//${name}
	        g = g.substring(2, g.length() - 1);  
	        words = m.replaceFirst(param.get(g) + "");  
	        m = p.matcher(words);  
	    }  
//	    System.out.println(words); 
	    return words;
	}
	
	
	public static String el1(String words,Map<String,Object> param) {   
		String regex = "\\$\\{([^\\}]+)\\}"; 
	    Pattern p = Pattern.compile(regex);     
	    Matcher m = p.matcher(words);     
	    String g;     
	    while (m.find()) {     
	        g = m.group(1);     
//	        System.out.println("被替换的内容："+g);//name
	        words = m.replaceFirst(param.get(g) + "");     
	        m = p.matcher(words);     
	    }     
//	    System.out.println(words); 
	    return words;
	}  
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 根据正则表达式查找数据<br>
	 *创建时间: 2017年7月15日
	 *@param words
	 *@param ex
	 *@return
	 */
	// ip:port 
	public static String ipPort="((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?):[0-9]{1,6}";
	public static List<String> find(String words,String regEx) {   
		Pattern p = Pattern.compile(regEx);     
	    Matcher m = p.matcher(words);  
	    List<String> data =new ArrayList<>();
	    String g;     
	    while (m.find()) {     
	        g = m.group();     
	        //System.out.println("匹配值:"+g);
	        data.add(g);
	    }     
	    return data;
	}  
	
	
}
