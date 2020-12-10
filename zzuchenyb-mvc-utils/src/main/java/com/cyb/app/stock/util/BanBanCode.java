package com.cyb.app.stock.util;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.cyb.app.stock.pub.Stock;
import com.cyb.utils.regex.RegexUtils;
/**
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年12月9日 下午4:06:17
 */
public class BanBanCode {
	Log log = LogFactory.getLog(BanBanCode.class);
	public static  List<Stock> analyseCodeFromHtml() throws IOException{
		String shHtml = "d:/data/stock/banban/html/sh.html";
		Document doc = Jsoup.parse(new File(shHtml), "UTF-8");
		Elements nodes  = doc.getElementsByTag("li");
		List<Stock> allStock = new ArrayList<Stock>();
		for(int i=0;i<nodes.size();i++){
			String codeString = nodes.get(i).text();
			if(RegexUtils.isMath(codeString, "*(*)")){
				String name = codeString.split("\\(")[0];
				String code=codeString.split("\\(")[1];
				code = code.substring(0, code.length()-1);
				allStock.add(new Stock("sh"+code,name,"sh"));
				//System.out.println(name+","+code);
			}
		}
		
		String szHtml = "d:/data/stock/banban/html/sz.html";
		Document doc1 = Jsoup.parse(new File(szHtml), "UTF-8");
		Elements nodes1  = doc1.getElementsByTag("li");
		for(int i=0;i<nodes1.size();i++){
			String codeString = nodes1.get(i).text();
			if(RegexUtils.isMath(codeString, "*(*)")){
				String name = codeString.split("\\(")[0];
				String code=codeString.split("\\(")[1];
				code = code.substring(0, code.length()-1);
				allStock.add(new Stock("sz"+code,name,"sz"));
				//System.out.println(name+","+code);
			}
		}
		return allStock;
	}
	public static void main(String[] args) {
		try {
			analyseCodeFromHtml();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
}
