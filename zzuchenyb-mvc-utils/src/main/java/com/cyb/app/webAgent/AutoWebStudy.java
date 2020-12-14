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
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;


/**
 * @Author iechenyb<br>
 * @Desc 配置chromedriver.exe的path，然后引入
 *       <dependency> <groupId>org.seleniumhq.selenium</groupId>
 *       <artifactId>selenium-java</artifactId> <version>3.141.59</version>
 *       <!--4.0.0-alpha-7 -->
 *       <exclusions> <exclusion> <groupId>com.google.guava</groupId>
 *       <artifactId>guava</artifactId> </exclusion> </exclusions> </dependency>
 * 
 *       <!-- https://mvnrepository.com/artifact/com.google.guava/guava -->
 *       <dependency> <groupId>com.google.guava</groupId>
 *       <artifactId>guava</artifactId>
 *       <version>29.0-jre</version> </dependency>
 *
 *       <br>
 * @CreateTime 2020年11月20日 下午6:03:23
 *             https://blog.csdn.net/qq_36433725/article/details/83058090
 */
public class AutoWebStudy {
	Log log = LogFactory.getLog(AutoWebStudy.class);
	static WebDriver driver = new ChromeDriver();

	public static void main(String[] args) throws InterruptedException {

		driver.manage().window().maximize(); // 将浏览器最大化
		driver.get("http://www.baidu.com");// 打开一个浏览器
		System.out.println(driver.getWindowHandles());// 获取父类窗口的id
		String title = driver.getTitle();
		System.out.println(title);
		Thread.sleep(5000);
		Actions action = new Actions(driver);
		Thread.sleep(5000);
		action.keyDown(Keys.CONTROL).sendKeys("t").keyUp(Keys.CONTROL).perform(); // ctr+t
																					// 覆盖了第一个标签
		driver.get("https://www.ifeng.com");// 覆盖掉百度
		Thread.sleep(5000);
		driver.get("https://www.ifeng.com");// 覆盖掉上一个窗口
		Thread.sleep(5000);
		WebElement search = null;
		// 找到“亲自试一试”按钮
		search = driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/ul/li[3]/a"));// copy
																							// xpath
																							// full
		// 点击“亲自试一试”按钮
		search.click(); // 点击某个操作按钮
		driver.switchTo().window(driver.getWindowHandle());
		// driver.close();
		// x.checkState(expression, errorMessageTemplate, errorMessageArgs);
	}

	// 刷新浏览器
	public static void refresh() {
		driver.navigate().refresh();
	}

	// 浏览器前进
	public static void forward() {
		driver.navigate().forward();
	}

	// 浏览器后退
	public static void back() {
		driver.navigate().back();
	}

	// 关闭浏览器 关闭窗口不关闭后台进程
	public static void close() {
		driver.close();
	}

	// 关闭浏览器 关闭窗口 关闭后台进程
	public static void quit() {
		driver.quit();
	}

	// 浏览器最大化
	public static void maximize() {
		driver.manage().window().maximize();
	}

	// 设置浏览器大小
	public static void manage(int x, int y) {
		Dimension dimension = new Dimension(x, y);
		driver.manage().window().setSize(dimension);
	}

	// 获取当前页面URL
	public static void getUrl(int x, int y) {
		driver.getCurrentUrl();
	}

	// 获取当前页面Title
	public static void getTitle(int x, int y) {
		driver.getTitle();
	}

}
