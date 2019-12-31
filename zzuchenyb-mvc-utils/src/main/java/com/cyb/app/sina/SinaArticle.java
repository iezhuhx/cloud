package com.cyb.app.sina;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cyb.utils.file.FileUtils;
import com.cyb.utils.http.HttpClientUtils;


public class SinaArticle {
  static String fileListPath="D:\\data\\sina\\filelist.txt";
  static String url = "http://blog.sina.com.cn/s/articlelist_2569018761_0_idx.html";
  public static void main(String[] args) {
	  List<String> list = FileUtils.readFileToList(fileListPath);
	  for(String u:list){
		  HttpClientUtils.doGet(u);
	  }
  }
  
  
  
  public void dowloadFileList(){
	  try {
		  for(int i=1;i<=7;i++)
		  readList(url.replace("idx", i+++""));
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
  
  public static void readList(String url) throws IOException{
	  org.jsoup.nodes.Document doc = Jsoup.connect(url).ignoreContentType(true)
	  .get();
	  Elements divs = doc.getElementsByAttributeValue("class", "articleCell SG_j_linedot1");
	  for(Element e:divs){
		  String href = e.getElementsByTag("a").get(0).attr("href");
		  System.out.println("href:"+href);
		  FileUtils.append(fileListPath, href+"\n");
	  }
  }
}
