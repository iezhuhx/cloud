package com.cyb.app.webAgent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.cyb.app.bms.zuul.ReportChecker;
/**
 *@Author iechenyb<br>
 *@Desc 配置chromedriver.exe的path，然后引入
 *<dependency>
			<groupId>org.seleniumhq.selenium</groupId>
			<artifactId>selenium-java</artifactId>
			<version>3.141.59</version><!--4.0.0-alpha-7 -->
			<exclusions>
				<exclusion>
					<groupId>com.google.guava</groupId>
					<artifactId>guava</artifactId>
				</exclusion>
			</exclusions>
		</dependency>

		<!-- https://mvnrepository.com/artifact/com.google.guava/guava -->
		<dependency>
			<groupId>com.google.guava</groupId>
			<artifactId>guava</artifactId>
			<version>29.0-jre</version>
		</dependency>
 *
 *<br>
 *@CreateTime 2020年11月20日 下午6:03:23
 *https://blog.csdn.net/qq_36433725/article/details/83058090
 */
public class AutoWebHello {
	Log log = LogFactory.getLog(AutoWebHello.class);
	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();       //将浏览器最大化
		driver.get("http://zuul.cesfutures.com:8000/index.html#/");//打开一个浏览器
		System.out.println(driver.getWindowHandles());
		String title = driver.getTitle();
		System.out.println(title);
		boolean isLogin = false;
		Map<String, String> cookieMap = new HashMap<String, String>();
		while(true){
			if(!isLogin){//检查是否登录
				Set<Cookie> cookies = driver.manage().getCookies();
				
				//System.out.println(cookies+","+cookies.size());
				for(Cookie c:cookies){
					cookieMap.put("SESSION", c.getValue());
					cookieMap.put("path", c.getPath());
					cookieMap.put("domain", c.getDomain());
					break;
				}
				
				Connection conn = Jsoup.connect(ReportChecker.service+"report/company/overview?ywrq=").ignoreContentType(true);
				Iterator<Entry<String, String>> iterCookie = cookieMap.entrySet().iterator();  
		        while(iterCookie.hasNext()){  
		            Entry<String, String> entry = iterCookie.next();  
		            conn.cookie(entry.getKey(), entry.getValue());  
		        } 
				String res;
				try {
					res = conn.get().text();
					System.out.println("检查是否登录："+!res.contains("过期"));
					if(res.contains("过期")) {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
							
						}
						continue;
					}else{
						isLogin=true;
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}else{
				new Thread(new ReportChecker(cookieMap)).start();//开启检查程序
			}
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//driver.close();
		//x.checkState(expression, errorMessageTemplate, errorMessageArgs);
	}
}
