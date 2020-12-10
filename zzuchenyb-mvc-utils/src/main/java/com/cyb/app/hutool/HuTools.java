package com.cyb.app.hutool;
import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Month;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import cn.hutool.setting.dialect.Props;
/**
 * https://www.hutool.cn/docs/#/
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年12月10日 下午1:34:26
 *isBlank是否为空白，空白的定义是null,"",不可见字符（如空格），
 *isEmpty是否为空，空的定义是null,""
 */
public class HuTools {
	Log log = LogFactory.getLog(HuTools.class);
	public static void main(String[] args) {
		TimeInterval timer = DateUtil.timer();

		String fileName = StrUtil.removeSuffix("pretty_girl.jpg", ".jpg");//去掉后缀
		System.out.println(fileName);
		System.out.println(ReUtil.findAllGroup0("[a]", "a00000a"));
		String template = "{}爱{}，就像老鼠爱大米";
	    String str = StrUtil.format(template, "我", "你");
	    System.out.println(str);
	    FileUtil.del(new File("/aaa"));
	    FileUtil.mkdir("d:/data/hutool");
	    /**
	     *  yyyy-MM-dd HH:mm:ss
			yyyy-MM-dd
			HH:mm:ss
			yyyy-MM-dd HH:mm
			yyyy-MM-dd HH:mm:ss.SSS
	     */
	    System.out.println(DateUtil.parse("2020-12-1"));
	    System.out.println(DateUtil.parse("2020-12-100"));//日期格式异常，不能正常处理
	    Date d = DateUtil.parse("2020-12-10 23:23:56");//重写了Date类
	    System.out.println(d);
	    try {
			Thread.sleep(1000+100+10);
		} catch (InterruptedException e) {
			e.printStackTrace();
			
		}
	  
	    // "摩羯座"
	    String zodiac = DateUtil.getZodiac(Month.JULY.getValue(), 1);
	    // "狗"
	    String chineseZodiac = DateUtil.getChineseZodiac(1989);
	    System.out.println(zodiac+","+chineseZodiac);
	    String[] col= new String[]{"a","b","c","d","e"};
	    List<String> colList = CollUtil.newArrayList(col);
	    str = CollUtil.join(colList, "#"); //str -> a#b#c#d#e
	    System.out.println(colList+"-"+str);
	    System.out.println(timer.interval()+","+","+timer.intervalPretty());
	    MapUtil.newConcurrentHashMap().put("1", "2");
	    
	    final CsvReader reader = CsvUtil.getReader();
	   //假设csv文件在classpath目录下
	    final List<CsvBean> result = reader.read(
	                  ResourceUtil.getUtf8Reader("d:/data/hutool/input.csv"), CsvBean.class);
	    for(CsvBean c:result)
	    System.out.println(c);
	  //指定路径和编码
	    CsvWriter writer = CsvUtil.getWriter("d:/data/hutool/testWrite.csv", CharsetUtil.CHARSET_UTF_8);
	    //按行写出
	    writer.write(
	        new String[] {"a1", "b1", "c1"}, 
	        new String[] {"a2", "b2", "c2"}, 
	        new String[] {"a3", "b3", "c3"}
	    );
	    writer.write(colList);
	    writer.close();
	    
	    
	    Props props = new Props("d:/data/hutool/test.properties");
	    String user = props.getProperty("user");
	    String driver = props.getStr("driver");
	    System.out.println(user+","+driver+","+props.getProperty("aa", "defaultValue"));
	    
	    //https://www.hutool.cn/docs/#/setting/设置文件-Setting
	    
	   Setting setting = new Setting(FileUtil.touch("d:/data/hutool/example.setting"), CharsetUtil.CHARSET_UTF_8, true);
	 /* //重新读取配置文件
	   setting.load();
	   //在配置文件变更时自动加载
	   setting.autoLoad(true);*/
	   System.out.println(setting.getStr("name"));//无组别获取
	   System.out.println(setting.getByGroup("user","demo"));
	   
	   System.out.println(setting.getByGroup("user","demo2"));
	    
	    
	    
	}
}
