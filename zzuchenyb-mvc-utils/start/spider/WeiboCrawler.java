package com.cyb.app.spider;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 描述：
 *
 * @author iechenyb
 * @create --
 */
public class WeiboCrawler extends BreadthCrawler {

    String cookie;

    public WeiboCrawler(String crawlPath) throws Exception {
        super(crawlPath, false);
        /* 获取新浪微博的cookie，账号密码以明文形式传输，请使用小号 */
        cookie = WeiboCN.getSinaCookie("383065059@qq.com", "");

        //设置线程数
        setThreads(3);
        //设置每个线程的爬取间隔
       getConf().setExecuteInterval(1000);
    }

    @Override
    public Page  getResponse(CrawlDatum crawlDatum) throws Exception {
        HttpRequest request = new HttpRequest(crawlDatum);
        request.setCookie(cookie);
        return request.responsePage();
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        int pageNum = Integer.valueOf(page.meta("pageNum"));
        /* 抽取微博 */
        Elements weibos = page.select("div.c[id]");
        for (Element weibo : weibos) {
            System.out.println("第" + pageNum + "页\t" + weibo.text());
        }
    }

    public static void main(String[] args) throws Exception {
        WeiboCrawler crawler = new WeiboCrawler("crawl_weibo");

        /* 对某人微博前5页进行爬取 */
        for (int i = 1; i <= 5; i++) {
            String seedUrl = "http://weibo.cn/zhouhongyi?vt=4&page=" + i;
            crawler.addSeedAndReturn(seedUrl).meta("pageNum", i);
        }
        crawler.start(1);
    }

}