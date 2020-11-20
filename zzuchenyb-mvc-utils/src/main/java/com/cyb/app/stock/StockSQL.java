package com.cyb.app.stock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月23日
 */
public class StockSQL {
	
	Log log = LogFactory.getLog(StockSQL.class);
	public static String createSQL="create table qutoes(code varchar  PRIMARY KEY,name varchar,cjl varchar,cje varchar)";
	public static String insertSQL="insert into qutoes values('${code}','${name}','${cjl}','${cje}')";
	public static String preTradeSQL="select max(rq) rq from holiday where rq<${rq} and type=0";
	public static String nextTradeSQL="select min(rq) rq from holiday where rq>${rq} and type=0";
	public static String tradeSQL="select type from holiday where rq=${rq}";
	
	//有行情的代码
	public static boolean  clearTag = false;
	public static final String DELETECODESQL = "DELETE FROM CODE";
	public static String codeTableSql="create table CODE(code varchar NOT NULL PRIMARY KEY,name varchar,time varchar)";
	public static String insertCodeTableSql="insert into CODE values('${code}','${name}',sysdate)";
}
