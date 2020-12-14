package com.cyb.app.spider;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.net.telnet.TelnetClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

/**
 * 描述：
 *  //小舒代理  http://www.xsdaili.com/dayProxy/2019/11/1.html
 * @author iechenyb
 * @create --
 */
public class HttpProxyUtils {
    public static void main(String[] args) throws Exception {
        trustEveryone();//设置https信任
        //setProxy();
       // xicidaili(185,2148);
        //analyIpPort();
        //kuaidaili(0,0);
    }
    private static TelnetClient telnet = null;
    public static  boolean telnet(String ip,int port){
        long start = System.currentTimeMillis();
       /* String ip = "118.178.227.1701";
        int port =80;*/
        //System.out.println(telnet(ip,port));
        try {
            telnet = new TelnetClient();
            telnet.setDefaultTimeout(100);
            telnet.setConnectTimeout(100);
            telnet.connect(ip, port);

            telnet.disconnect();
            return true;
        } catch (IOException e) {
            //e.printStackTrace();
            return false;
        }finally {
          //  System.out.println(System.currentTimeMillis()-start);
        }

    }



    private static void analyIpPort() throws IOException {
       File dir = new File("D:\\data\\proxy\\xicidaili");
       File[] files = dir.listFiles();
       for(int i=0;i<files.length;i++){//files.length
           Document doc = Jsoup.parse(files[i],"utf-8");
           Elements trs =  doc.getElementById("ip_list").getElementsByTag("tr");
           for(int j=1;j<trs.size();j++){//trs.size()
               String curIp = trs.get(j).getElementsByTag("td").get(1).text();
               String curPort = trs.get(j).getElementsByTag("td").get(2).text();
              // System.out.println(i+"#"+j);
               if(telnet(curIp,Integer.valueOf(curPort))) {//可用性检查
                   System.out.println(curIp+ ":"+ curPort);
               }
           }
       }
    }
    static String url ="https://www.xicidaili.com/wt/index";
    static String genFileName="D:\\data\\proxy\\xicidaili\\index.html";
    static String  agent="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)"
            + "  Chrome/56.0.2924.87 Safari/537.36" ;

    public static void setProxy(){
        String ip = "118.178.227.171";
        String port ="80";
        System.setProperty("https.proxySet", "true");
        System.getProperties().put("https.proxyHost", ip);
        System.getProperties().put("https.proxyPort", port);
        System.getProperties().setProperty("http.proxyHost", ip);
        System.getProperties().setProperty("http.proxyPort", port);
    }
    public static void kuaidaili(int startPage,int endPage)throws Exception {
         String url ="https://www.kuaidaili.com/free/inha/index/";
         String genFileName="D:\\data\\proxy\\kuaidaili\\index.html";
        for(int i=startPage;i<=endPage;i++){
            String curFilePath = genFileName.replace("index",i+"");
            File curFile = new File(curFilePath);
            String curUrl =url.replace("index",i+"");
            saveHtmlToFile(i,curUrl,curFile);
        }
    }
    public static void xicidaili(int startPage,int endPage)throws Exception {
        for(int i=startPage;i<=endPage;i++){
            String curFilePath = genFileName.replace("index",i+"");
            File curFile = new File(curFilePath);
            String curUrl =url.replace("index",i+"");
            saveHtmlToFile(i,curUrl,curFile);
        }
    }

    public static void saveHtmlToFile(int i,String url,File curFile) throws IOException {
        String res=Jsoup.connect(url)
                .userAgent(agent)
                .ignoreContentType(true)
                .ignoreHttpErrors(true)
                .get().html();
        if(!StringUtils.isEmpty(res)&&res.length()>100) {
            FileCopyUtils.copy(res.getBytes(), curFile);
        }

    }
    public static void saveHtmlToFile1(String url,File curFile) throws IOException {
        saveHtmlToFile(0,url,curFile);
    }

    public static void saveHtmlToFile(String url,String filePath){
        saveHtmlToFile(url,new File(filePath));
    }

    public static void saveHtmlToFile(String url,File file){
        try {
            saveHtmlToFile1(url,file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 信任任何站点，实现https页面的    正常访问
     *
     */

    public static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[] { new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } }, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }
}
