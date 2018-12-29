package com.cyb.app.csdn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.quartz.SchedulerException;
import org.springframework.util.CollectionUtils;

import com.cyb.app.reptile.ProxyInfor;
import com.cyb.app.reptile.ProxyRandomUtils;
import com.cyb.app.task.QuartzManager;
import com.cyb.utils.computer.CmdUtils;
import com.cyb.utils.file.FileUtils;
import com.cyb.utils.http.HttpsClient;
import com.cyb.utils.random.RandomUtils;
import com.cyb.utils.text.ELUtils;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年12月29日
 */
public class CsdnUtils {
	Log log = LogFactory.getLog(CsdnUtils.class);
	static List<String> articles = new ArrayList<>();
	static String articlePage = "https://blog.csdn.net/iechenyb/article/list/${page}";
	static String articlePrex = "https://blog.csdn.net/zzuchenyb/article/details";
	static String articlePath = "d:/data/csdn/articles.txt";
	public static void initArticle() {
		try {
			if(!CollectionUtils.isEmpty(articles)){
				return ;
			}//已经初始化
			articles = FileUtils.readFileToList(articlePath);
			if(!CollectionUtils.isEmpty(articles)){
				return ;
			}//文件里没有内容
			for (int j = 1; j < 50; j++) {
				System.out.println("初始化执行分页数:"+j);
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("page", j);
				String pageIndex = ELUtils.el(articlePage, param);
				String s = HttpsClient.get(pageIndex);
				Elements as = Jsoup.parse(s).getElementsByTag("a");
				for (int i = 0; i < as.size(); i++) {
					String href = as.get(i).parent().getElementsByTag("a").attr("href");
					if (href.contains(articlePrex) && !href.endsWith("comments")) {
						System.out.println(href);
						articles.add(href);
						FileUtils.appendString2File(href+"\n",articlePath);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("初始化失败");
		}
	}

	public static int getArticleRandom() {
		int idx = RandomUtils.getNum(0, articles.size() - 1);
		System.out.println("文章总数："+articles.size()+",随机索引:" + idx);
		return idx;
	}

	public static void visitArticle() {
		String url =null;
		try {
			ProxyInfor proxy = ProxyRandomUtils.findUseableProxyRandom();
			if(!CmdUtils.telnet(proxy)){
				return ;
			}
			url = articles.get(getArticleRandom());
			HttpsClient.get(url, proxy);
			System.out.println(proxy+"["+CmdUtils.telnet(proxy)+"]--->"+url);
		} catch (Exception e) {
			System.out.println("访问失败：" + url);
		}

	}
    public static void start() throws ClassNotFoundException, SchedulerException{
        String job_name = "动态任务调度";  
        String cron = "*/10 * * * * ?"; 
        QuartzManager.addJob(job_name, CsdnJob.class, cron);  
    }
	public static void main(String[] args) throws ClassNotFoundException, SchedulerException {
		start();
	}
}
