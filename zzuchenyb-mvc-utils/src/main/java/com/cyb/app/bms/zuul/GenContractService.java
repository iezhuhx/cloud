package com.cyb.app.bms.zuul;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyb.app.bms.JsoupUtils;
import com.cyb.utils.file.FileUtils;
import com.cyb.utils.text.ELUtils;
import com.meterware.httpunit.WebClient;


/**
 * @Author iechenyb<br>
 * @Desc 类描述<br>
 * @CreateTime 2020年11月24日 上午11:23:16
 */
public class GenContractService extends JobMain {
	Log log = LogFactory.getLog(GenContractService.class);
	static String curUrl1 = "http://zuul.cesfutures.com:8000/qhbms/bms/listclient.do?method=listclient&clientname=&id_card_no=&fund_account=877&open_type=&client_type=&mobile=&open_status=&owner_type=&lock_user=&source=&proc_no=&prov=&city=&iscomplete=1&currentPage=${page}&pageMethod=next";
    static String curUrl="http://bms.kiiik.com/kiiikoa/bms/listclient.do?method=listclient";
	public static void main(String[] args) {
		try {
			登录("7408d5f6-9f90-4085-865b-aaeadab6508e");
			genContract();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static void genContract() {
		int nums = 252;// 608
		int i = 1;
		for (i = 1; i <= 1; i++) {
			Map<String, Object> param = new HashMap<>();
			param.put("page", i);
			param.put("fund_account", "87");
			param.put("iscomplete", "1");
			param.put("currentPage", i);
			try {
				// FileUtils.appendEnter("d:/data/rjqy20(01-10).csv",
				// param.get("rq").toString());
				String url = ELUtils.el(curUrl, param);
				System.out.println(url);
				Connection con = JsoupUtils.getConnection(url);
				setCookie(con);
				/*
				 * = Jsoup.connect(url); con.ignoreContentType(true);
				 * con.timeout(10000000);
				 */
				con.data();
				Document doc = con.post();
				Elements res = doc.select("tr");
				System.out.println(doc.html());// 航金融内部办公系统
												// 您的浏览器好像不支持JavaScript,请更改您的浏览器选项
				System.out.println(doc.html().length() + "->" + res.size());
			} catch (IOException e) {
				e.printStackTrace();

			}

		}
	}

}
