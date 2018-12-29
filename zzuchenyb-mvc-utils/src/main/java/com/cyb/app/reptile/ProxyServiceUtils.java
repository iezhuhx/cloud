package com.cyb.app.reptile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import com.cyb.utils.computer.CmdUtils;
import com.cyb.utils.file.FileUtils;
import com.cyb.utils.http.HttpsClient;
import com.cyb.utils.http.MyHttpClient;
import com.cyb.utils.page.Pagination;
import com.cyb.utils.text.ELUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月28日
 */
public class ProxyServiceUtils {
	Log log = LogFactory.getLog(ProxyServiceUtils.class);
	
	static String proxy1="https://www.kuaidaili.com/free/inha/1/";
	static String proxyn="https://www.kuaidaili.com/free/inha/${page}/";
	static List<ProxyInfor> useableProxy = new ArrayList<>();
	static ProxyInfor defaultProxy = new ProxyInfor("182.150.35.145:8080");
	static String bingfafileName = "d:/data/proxy/useable${page}.txt";
	static String defaultFileName = "d:/data/proxy/useable.txt";
	static int total=0;
	public static void init() throws IOException{
		List<String> data = ELUtils.find(FileUtils.read("d:\\data\\proxy\\proxy.txt"), ELUtils.ipPort);
		for(String d:data){
			if(CmdUtils.telnet(new ProxyInfor(d))){
				System.out.println(d);
				useableProxy.add(new ProxyInfor(d));
			}
		}
	}
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		//init();
		defaultProxy = ProxyRandomUtils.findUseableProxyRandom();
		String str = HttpsClient.get(proxy1,defaultProxy);
		String aa = Jsoup.parse(str).getElementById("listnav").html();
		String total1 = Jsoup.parse(aa).getElementsByTag("a").last().html();
		System.out.println("总页数："+total1);
		/*提取tr时，html必须有table，否则会被忽略*/
		total = Integer.valueOf(total1);
		//seriGet(total);
		bingfaGet(total);
		
	}
	static class Task implements Runnable {
		Pagination page;
		Task(Pagination page){
			this.page = page;
		}
		public void run() {
			for(int i=page.getPageStart();i<page.getPageEnd();i++){
				Map<String,Object> param = new HashMap<>();
				param.put("page", i);
				try {
					获取每一页的代理地址(ELUtils.el(proxyn, param),ELUtils.el(bingfafileName, param));
				} catch (IOException e) {
					System.out.println("并发任务失败：page="+i);
				}
			}
		}
	}
	private static void bingfaGet(int total) throws IOException {
		Pagination page = new Pagination(1,100,total);
		ExecutorService worker = Executors.newFixedThreadPool(20);
		for(int i=1;i<=page.getPageCount();i++){
			Pagination pageTmp = new Pagination(i,100,total);
			worker.submit(new Task(pageTmp));
		}
		worker.shutdown();
	}

	public static void seriGet(int total) throws IOException{
		for(int i=1;i<=total;i++){
			Map<String,Object> param = new HashMap<>();
			param.put("page", i);
			获取每一页的代理地址(ELUtils.el(proxyn, param),defaultFileName);
		}
	}
	
	public static void 获取每一页的代理地址(String url,String defaultFileName) throws IOException {
		System.out.println(url);
		String str = HttpsClient.get(url,defaultProxy);
		if(StringUtils.isEmpty(str)){return ;}
		if(str.length()<30){ return ;}
		Elements trs = Jsoup.parse(str).getElementById("list").getElementsByTag("tr");
	    for(int i=0;i<trs.size();i++){
	    	String ip = trs.get(i).getElementsByAttributeValue("data-title", "IP").html();
	    	String port = trs.get(i).getElementsByAttributeValue("data-title", "PORT").html();
	    	if(!StringUtils.isEmpty(ip)&&!StringUtils.isEmpty(port)){
		    	if(CmdUtils.telnet(ip, Integer.valueOf(port))){
			    	System.out.println(ip+":"+port);
			    	FileUtils.append(defaultFileName, ip+":"+port+"\n");
			    }
	    	}
	    }
	}
	
	//代理数据已经加密
	public static void manualProxy() throws MalformedURLException, IOException{
		String str =MyHttpClient.doGet("http://ip.zdaye.com/dayProxy/ip/204629.html");
		System.out.println(str);
		/*Document doc = Jsoup.parse(new URL("http://ip.zdaye.com/dayProxy/ip/204629.html"),1000);
		String aa = doc.getElementsByAttributeValue("class", "cont").html();
		System.out.println(aa);*/
	}
}
