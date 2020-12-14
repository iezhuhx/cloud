package com.cyb.app.spider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 描述：
 * http://jx.598110.com/?url=src
 * @author iechenyb
 * @create --
 */
@Data
@Slf4j
@ToString
@AllArgsConstructor
class Vedio{
    private String name;
    private String url;
    private String desc;
}
@Slf4j
public class AiqiUtils {
    public static List<Vedio> vedios = new ArrayList<>();
    public static String searchUrl = "http://so.iqiyi.com/so/q_key?source=default&sr=450291991267";
    public static HashMap<String,String> indexStatics= new HashMap<String,String>();
    public static void main(String[] args) {
        String key = "红楼梦";
        searchIqiyiVedio(key);//视频搜索
        searchIqiyiMovie(key);//电影搜索
        log.info(vedios.toString());
    }
    public static void searchIqiyiMovie(String vedioName){
        try {
            Element movie = Jsoup
                    .connect(searchUrl.replace("key", vedioName))
                    .ignoreHttpErrors(true)
                    .ignoreContentType(true)
                    .get()
                    .getElementsByAttributeValue("class","qy-search-result-tit")
                    .get(0).getElementsByTag("a").get(0);
            Vedio vedio = new Vedio(movie.text(),movie.attr("href"),movie.text());
            log.info(vedio.toString());
        }catch (Exception e){
         e.printStackTrace();
        }
    }
    public static void searchIqiyiVedio(String vedioName){
        vedioName = URLEncoder.encode(vedioName);
        try {
            Elements allList =   Jsoup
                    .connect(searchUrl.replace("key",vedioName))
                    .ignoreHttpErrors(true)
                    .ignoreContentType(true)
                    .get()
                    .getElementsByAttributeValue("class","qy-search-result-album")
                    .get(0).getElementsByAttributeValue("class","album-list")
                    .get(0).getElementsByTag("a");
            if(allList.size()>0){
                for (int i=0;i<allList.size();i++){
                    String index =allList.get(i).text().trim();
                    if(!indexStatics.containsKey(index)) {
                        vedios.add(new Vedio(allList.get(i).attr("title"), allList.get(i).attr("href"), index));
                        indexStatics.put(index,"0");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
