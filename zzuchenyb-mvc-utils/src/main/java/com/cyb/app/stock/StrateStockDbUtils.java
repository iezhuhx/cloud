package com.cyb.app.stock;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.jdbcx.JdbcConnectionPool;

import com.cyb.app.commondb.ConnectionUtils;
import com.cyb.app.h2.H2Common2DbUtils;
import com.cyb.app.h2.H2DBConnectionPool;
import com.cyb.app.h2.H2DBInfor;
import com.cyb.app.stock.pub.RealQuotation;
import com.cyb.utils.file.FileUtils;
import com.cyb.utils.page.Pagination;
import com.cyb.utils.text.ELUtils;
/**
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年7月6日 下午6:54:27
 */
public class StrateStockDbUtils {
	static Log log = LogFactory.getLog(StrateStockDbUtils.class);
	static long yi=10000000;
	static long thresholdCje=20*yi; 
	public static List<RealQuotation> shList= new ArrayList<RealQuotation>();
	public static List<RealQuotation> szList= new ArrayList<RealQuotation>();
	public static JdbcConnectionPool pool;
	public static int MaxStockCode=666666;
	static int batchSize=500;
	public static void main(String[] args) throws IOException, SQLException {
		initStockDB();//初始化表结构
		initStockInfo2H2("sh");
		initStockInfo2H2("sz");
		//initStockDbInfo();
		//readListBatch("D:\\data\\stock\\sh.txt","sh");
		//readListBatch("D:\\data\\stock\\sz.txt","sz");
		//analyse();
	}
	
	//穷举获取股票代码,
	public static void initStockInfo2H2(String exchange) throws IOException, SQLException{
		int start=20362;
		int page1=start/batchSize;
		int maxVal=MaxStockCode-start+1;
		Pagination page = new Pagination(1, batchSize, maxVal);//6位数最大值
			 for(int i=page1;i<page.getPageSize();i++){
		          Pagination cur =  page.getPage(i);
		          log.info("共"+page.getPageSize()+"页，当前页="+i);
		          List<String> codeParam  =new ArrayList<>(batchSize);
		          for(int idx=cur.getPageStart();idx<=cur.getPageEnd()+1;idx++){
		        	  codeParam.add(exchange+String.format("%06d", idx));
		          }
		          String param = String.join(",", codeParam);
		          saveStockCode( DrawQutoesUtils.getOriginQutoesBatchUseProxy(param));//批量抓取行情  jsoupGet
		   }
	}
	
	public static void saveStockCode(List<RealQuotation> data) throws SQLException{
		ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
		   for(RealQuotation rq:data){
			   try{
			   Map<String,Object> param = new HashMap<>();
			   param.put("code", rq.getCode());
			   param.put("name", rq.getName());
			   String sql = ELUtils.el(StockSQL.insertCodeTableSql, param);
			   log.info(sql);
			   dbUtil.update(sql);
			   }catch(Exception e){}
		   }
	}
	
	private static void initStockDB() {
		String url = "";
		url =  H2DBInfor.getTcpUrl("192.168.16.211", "9092", "D:/data/stock/DB", "stock");
		log.info(url);
		pool = H2DBConnectionPool.getJDBCConnectionPool(url,"sa","");
		try {
			H2Common2DbUtils.createTable(pool, "qutoes", StockSQL.createSQL);
			H2Common2DbUtils.createTable(pool, "CODE", StockSQL.codeTableSql);
			ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
			if(StockSQL.clearTag){
				dbUtil.update(StockSQL.DELETECODESQL);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	
	//读取最新行情信息
   public static void readList() throws IOException, SQLException{
	   log.info("sh行情读取中...");
	   List<String> dsh = FileUtils.readFileToList("D:\\data\\stock\\sh.txt");
	   for(String s:dsh){
		   shList.add(DrawQutoesUtils.getQutoesByString(DrawQutoesUtils.getOriginQutoes(s)));
	   }
	   System.out.println("sh行情读取中over");
	   System.out.println("sz行情读取中...");
	   List<String> dsz = FileUtils.readFileToList("D:\\data\\stock\\sz.txt");
	   for(String s:dsz){
		   szList.add(DrawQutoesUtils.getQutoesByString(DrawQutoesUtils.getOriginQutoes(s)));
	   }	
	   log.info("sz行情读取over");
	}
   
   
   
   public static void readListBatch(String path,String type) throws IOException, SQLException{
	   log.info(type+"行情读取中...");
	   List<String> list = FileUtils.readFileToList(path);
	   Pagination page = new Pagination(1, batchSize, list.size());
	   for(int i=1;i<page.getPageSize();i++){
		   Pagination cur =  page.getPage(i);
		   System.out.println("共"+page.getPageSize()+"页，当前页="+i);
		   String param = String.join(",", list.subList(cur.getPageStart(),(int)cur.getPageEnd()+1));
		   log.info(param);
		   if("sh".equals(type)){
		       shList.addAll(DrawQutoesUtils.getRealQutoesBatch(param));
		   }else{
			   szList.addAll(DrawQutoesUtils.getRealQutoesBatch(param));
		   }
	   }
	   List<RealQuotation> data=null;
	   int size=0;
	   if("sh".equals(type)){
		   size = shList.size();
		   data = shList;
	   }else{
		   size = szList.size();
		   data=szList;
	   }
	   ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
	   for(RealQuotation rq:data){
		   Map<String,Object> param = new HashMap<>();
		   param.put("code", rq.getCode());
		   param.put("name", rq.getName());
		   param.put("cjl", rq.getVolumn());   
		   param.put("cje", rq.getCash());
		   String sql = ELUtils.el(StockSQL.insertSQL, param);
		   System.out.println(sql);
		   dbUtil.update(sql);
	   }
	   log.info(type+"行情读取over"+list.size()+"="+size);
	}
   
   //分析，根据指定行情阀值过滤
	public static void analyse(){
		 for(RealQuotation d:shList){
			  System.out.println(d.getCash());
		  }
		 for(RealQuotation d:szList){
			  System.out.println(d.getCash());
		 }
	}
}
