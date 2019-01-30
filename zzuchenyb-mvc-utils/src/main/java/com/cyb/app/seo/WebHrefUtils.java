package com.cyb.app.seo;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import com.cyb.utils.file.FileUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月24日
 */
public class WebHrefUtils {
	Log log = LogFactory.getLog(WebHrefUtils.class);
	public static String newsFile = "d:/data/news/data.txt";
	static String index = "http://www.baidu.com";
	
	static int curDeep = 0;
	static int maxDeep = 1000*100;
	static transient volatile Map<String, String> urls 
	= new ConcurrentHashMap<>();// new HashMap<>();
	public static void main(String[] args) throws MalformedURLException, IOException {
		FileUtils.overideString2File("", newsFile);
		index="https://www.hao123.com/";
		getHref(index);
	}
	public static void getHref(String index) throws MalformedURLException, IOException{
		try{
		if(curDeep>=maxDeep) return ;
		curDeep++;
		Elements hrefs = Jsoup.parse(new URL(index), 10*1000).getElementsByTag("a");
		for(int i=0;i<hrefs.size();i++){
			String a = hrefs.get(i).attr("href");
			String title = hrefs.get(i).attr("title");
			if(StringUtils.isEmpty(title)){
				title = hrefs.get(i).text();
			}
			if(StringUtils.isEmpty(title)){
				continue;
			}
			if(urls.containsKey(a)){
				continue;
			}
			urls.put(a, "");//去重
			if(a.startsWith("http")||a.startsWith("https")){
				System.out.println("deep="+curDeep+"\t"+title+"\t"+a);
				FileUtils.appendString2File(title+"#"+a+"\n", newsFile);
				getHref(a);
			}
		}
		}catch(Exception e){}
	}
}
