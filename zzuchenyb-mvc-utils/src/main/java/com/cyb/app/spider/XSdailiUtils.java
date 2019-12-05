package com.web.collector;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.json.HTTP;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：
 *
 * @author iechenyb
 * @create --
 */
public class XSdailiUtils {
    static List<Index> indexs = new ArrayList<>();
    static List<String> urls = new ArrayList<>();//所有的具体页面
    public static void main(String[] args) throws IOException {
        //analyIndex();//解析索引
        //getHtmlFromIndex();//解析所有的页面
        //getIpPortFromPage();//解析页面链接
        //analyPagesContent();//将具体的代理详情页面抓取
        //xsdaili();//根据内容提取ip端口号
        analyPorxy();
    }
    static String webIndex ="http://www.xsdaili.com/";
    private static void analyPorxy() {
        File file= new File("D:\\data\\proxy\\xsdaili\\result\\");
        File [] files = file.listFiles();
        int index=1;
        for(int i=0;i<files.length;i++){//.length
            try {
                Elements brs =  Jsoup.parse(files[i],"utf-8")
                       .getElementsByAttributeValue("class","cont");
                for(int j=0;j<brs.size();j++){
                   // System.out.println(brs.get(j).text());
                    getFinalProxy(brs.get(j).text());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void getFinalProxy(String content){
        Pattern p = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+)\\:(\\d+)");
        Matcher m = p.matcher(content);
       //将符合规则的提取出来
        while(m.find()) {
           /* System.out.println("ip:"+m.group(1));
            System.out.println("port:"+m.group(2));*/
           if(HttpProxyUtils.telnet(m.group(1),Integer.valueOf(m.group(2)))) {
               System.out.println(m.group(1) + ":" + m.group(2));
           }
        }
    }
    private static void analyPagesContent() {
        File file= new File(filePath);
        File [] files = file.listFiles();
        int index=1;
        for(int i=0;i<files.length;i++){//.length
            try {
               Elements as =  Jsoup.parse(files[i],"utf-8").getElementsByTag("a");
               for(int j=0;j<as.size();j++){
                   String href = as.get(j).attr("href");
                  if(!StringUtils.isEmpty(href)&&href.contains("/dayProxy/ip/")){
                      HttpProxyUtils.saveHtmlToFile(webIndex+href,"D:\\data\\proxy\\xsdaili\\result\\"+index+".html");
                      index++;
                  }
               }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    static String filePath = "D:\\data\\proxy\\xsdaili\\page\\";
    public static void getIpPortFromPage(){

        int index=0;
        for(String url:urls){
            try {
                index++;
                HttpProxyUtils.saveHtmlToFile(url,new File(filePath+index+".html"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void  getHtmlFromIndex(){
        for(Index idx:indexs){
            String curMonthUrl = idx.url.substring(0,idx.url.lastIndexOf("/")+1);
            for(int i=1;i<=idx.pages;i++){
               // System.out.println(curMonthUrl+i+".html");
                urls.add(curMonthUrl+i+".html");
            }
        }
    }

    private static void analyIndex() {
        try {
           Elements as = Jsoup.parse(new File("D:\\data\\proxy\\xsdaili\\index.html"),"utf-8")
            .getElementsByAttributeValue("class","list-group").get(0).children();
            for(int i=1;i<as.size();i++){
                String url=as.get(i).attr("href");
                int total = Integer.valueOf(as.get(i).children().get(1).text());
               // System.out.println(url+"#"+total);
                indexs.add(new Index(url,total));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   static class Index{
        public String url;
        public int pages;
        public Index(String url,int total){
            this.url = "http://www.xsdaili.com/"+url;
            pages = total/15;
            if(pages*15<total){
                pages = pages+1;
            }
        }
   }
    //id不存在时，重定向到同一个页面
    public static void xsdaili() throws IOException {
        String url ="http://www.xsdaili.com/dayProxy/ip/index.html";
        String genFileName="D:\\data\\proxy\\xsdaili\\index.html";
        for(int i=2136;i<=10000;i++){
            System.out.println("index:"+i);
            String curFilePath = genFileName.replace("index",i+"");
            File curFile = new File(curFilePath);
            String curUrl =url.replace("index",i+"");
            HttpProxyUtils.saveHtmlToFile(i,curUrl,curFile);
        }
    }


}
