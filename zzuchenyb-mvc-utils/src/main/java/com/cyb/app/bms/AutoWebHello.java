package com.cyb.app.bms;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
/**
 *@Author iechenyb<br>
 *@Desc 配置chromedriver.exe的path，然后引入
 *<dependency>
			<groupId>org.seleniumhq.selenium</groupId>
			<artifactId>selenium-java</artifactId>
			<version>3.141.59</version>
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
		driver.get("http://zuul.cesfutures.com:8000/index.html#/");//打开一个浏览器
		System.out.println(driver.getWindowHandles());
		String title = driver.getTitle();
		
		System.out.println(title);
		while(true){
			
			//driver.get("http://zuul.cesfutures.com:8000/index.html#/");
			System.out.println(driver.manage().getCookies());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		//driver.close();
		//x.checkState(expression, errorMessageTemplate, errorMessageArgs);
	}
}
