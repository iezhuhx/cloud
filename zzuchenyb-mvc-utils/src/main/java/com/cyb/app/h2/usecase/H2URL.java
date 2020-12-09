package com.cyb.app.h2.usecase;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.cyb.utils.text.ELUtils;

/**
 * @Author iechenyb<br>
 * @Desc 类描述<br>
 * @CreateTime 2020年12月9日 上午9:14:27
 */
public class H2URL {

	static String defaultTcpurl = "jdbc:h2:tcp://${server}:${port}/${h2dbPath}/${dbName};";
	static String tcpUrl = "jdbc:h2:tcp://${server}:${port}/${h2dbPath}/${dbName};IFEXISTS=false;mv_store=false;MVCC=false";

	public static String server = "127.0.0.1";
	public static String port = "9092";//tcpport
	public static String webPort="8095";
	static String dbName = "test";
	static String h2dbPath = "d:/data/h2db";

	static Map<String, Object> param = new HashMap<>();

	static {
		param.put("server", server);
		param.put("port", port);
		param.put("h2dbPath", h2dbPath);
		param.put("dbName", "test");
	}

	public static String getTcpUrl(String dbName) {
		if (!StringUtils.isEmpty(dbName)) {
			param.put("dbName", dbName);
		}
		String url = ELUtils.el(tcpUrl, param);
		System.out.println("dburl：" + url);
		return url;
	}

	public static String getTcpUrl() {
		return getTcpUrl("");
	}

	public String getDefaultEmbedUrl() {//暂时未启用
		String url = "jdbc:h2:~/.h2/first/${dbName}";// ok
		// jdbc:h2:~/${h2dbPath}/${dbName} 不可以用~即代表主目录盘符
		url = "jdbc:h2:~/${h2dbPath}/${dbName}";// 指定路径,不可以带盘符
		Map<String, Object> param = new HashMap<>();
		param.put("dbName", dbName);
		param.put("h2dbPath", h2dbPath);
		return ELUtils.el(url, param);
	}

}
