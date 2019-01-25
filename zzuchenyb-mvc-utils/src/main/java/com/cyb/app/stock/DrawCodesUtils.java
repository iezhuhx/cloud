package com.cyb.app.stock;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.app.stock.pub.RealQuotation;
import com.cyb.app.stock.pub.Stock;
import com.cyb.utils.file.FileUtils;
import com.cyb.utils.page.Pagination;
import com.cyb.utils.uuid.UUIDUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年7月24日
 */
public class DrawCodesUtils {
	public static String url="http://hq.sinajs.cn/list=";
	public static String baseDir="D:\\data\\stock\\";
	static Log log = LogFactory.getLog(DrawCodesUtils.class);
	class TaskSH implements  Runnable {
		public void run() {
			try {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					
				}
				initSZStockCodes();
			} catch (IOException e) {
				e.printStackTrace();
				
			}
		}
	}
	class TaskSZ implements  Runnable {
		public void run() {
			try {
				initSHStockCodes();
			} catch (IOException e) {
				e.printStackTrace();
				
			}
		}
	}
	public static void main(String[] args) throws Exception {
		/*initSZStockCodes();
		initSHStockCodes();*/
	}
	public static void initStockCodes() throws IOException{
		Stock stock = null;
		String[] lxs = new String[]{"sh","sz"};
		List<Stock> stocks= new ArrayList<Stock>(); 
		for(int lx=0;lx<2;lx++){
			for(int i=1;i<999999;i++){
				int len = String.valueOf(i).length();
				String code = getZeros(6-len)+i;
				String orginStr = FileUtils.getJsonFromNet(url+lxs[lx]+code, null,"gbk");//.replaceAll("\"", "").replaceAll(";", "");
				String left=orginStr.split("=")[0];
				String right = orginStr.split("=")[1];
				if(right.length()>4){
				  stock =new Stock();
			      stock.setId(UUIDUtils.getUUID());
			      stock.setCode(left.split("=")[0].substring(left.length()-6, left.length()));
			      stock.setName(right.split(",")[0].replace("\"", ""));
			      stock.setExchange(lxs[lx]);
			      log.info(left.split("=")[0].substring(left.length()-6, left.length())+","+right.split(",")[0].replace("\"", ""));
			      stocks.add(stock);
				}else{
					//log.info("code["+i+"]不可用！");
				}			
			}
			String path = FileUtils.getAbsolutePathAtClass(DrawCodesUtils.class)+lxs[lx]+"stocks.data";
			log.info("股票代码写入文件："+path);
		}
		
	}
	public static int pageSize=850;
	public static void initStockCodesBatch() throws IOException{
		String[] lxs = new String[]{"sh","sz"};
		List<Stock> stocks=null;
		StringBuffer sb = new StringBuffer();
		Pagination page = new Pagination(1, pageSize, 999999);
		int pageCount = page.getPageCount();
		for(int lx=0;lx<2;lx++){
			stocks= new ArrayList<Stock>(); 
			for(int i=1;i<=pageCount;i++){//遍历页数
				Pagination p_ = new Pagination(i, pageSize, 999999);
				for (int j = p_.getOffset(); j <= (p_.getPageSize() * i - 1) - 1; j++){ //遍历页面数据
					int len = String.valueOf(j).length();
					String code = lxs[lx]+getZeros(6-len)+j;
					sb.append(code+",");
				}
				log.info("交易所："+lxs[lx]+",总页数:"+p_.getPageCount()+",当前页："+i);
				stocks.addAll(getStockCodeBatch(sb.toString(),lxs[lx]));
				sb.delete(0, sb.length());
			}
			String path = FileUtils.getAbsolutePathAtClass(DrawCodesUtils.class)+lxs[lx]+"stocks.data";
			log.info("股票代码写入文件："+path);
		}
	}
	public static RealQuotation getQutoesByString(String orginStr) throws IOException{
		String left=orginStr.split("=")[0]; 
		String right = orginStr.split("=")[1];
		String code = left.split("=")[0].substring(left.length()-8, left.length());
		 if(right.length()>4){
			 RealQuotation qutoes = DrawQutoesUtils.String2Qutoes(right, code);
		     return qutoes;
		 }else{
			 return null;
		 }
	}
	public static Stock getStockCode(String orginStr,String exchange) throws IOException{
		String left=orginStr.split("=")[0]; 
		String right = orginStr.split("=")[1];
		String code = left.split("=")[0].substring(left.length()-8, left.length());
		Stock stock = null;
		 if(right.length()>4){
			  stock =new Stock();
		      stock.setId(UUIDUtils.getUUID());
		      stock.setCode(left.split("=")[0].substring(left.length()-6, left.length()));
		      stock.setName(right.split(",")[0].replace("\"", ""));
		      stock.setCode_(code);
		      stock.setExchange(exchange);
		      return stock;
		 }else{
			 return null;
		 }
	}
	public static List<Stock> getStockCodeBatch(String codes,String exchange) throws IOException{
		String orginStr = FileUtils.getJsonFromNet(url+codes, null,"gbk");//.replaceAll("\"", "").replaceAll(";", "");
		List<Stock> data = new ArrayList<Stock>();
		String[] strs = orginStr.replaceAll("\n", "#").split("#");
		for(String str:strs){
			Stock stock = getStockCode(str,exchange);
			if(stock!=null){
				data.add(stock);
			}
		}
		return data;
	}
	/*public static RealQutoes getQutoes(String stockcode) throws IOException{
		if(StringUtils.isNotEmpty(getOriginQutoes(stockcode))){
			 String orginStr = FileUtils.getJsonFromNet(url+stockcode, null,"gbk");//.replaceAll("\"", "").replaceAll(";", "");
			 String right = orginStr.split("=")[1];
			 if(right.length()>4){
				 RealQutoes qutoes = RealQutoesUtils.String2Qutoes(right, stockcode);
			     return qutoes;
			 }else{
				 return null;
			 }
		}
		return null;
	}*/
	
	public static void initSZStockCodes() throws IOException{
		Stock stock = null;
		String[] lxs = new String[]{"sz"};
		List<Stock> stocks= new ArrayList<Stock>(); 
		for(int lx=0;lx<1;lx++){
			for(int i=1;i<999999;i++){
				int len = String.valueOf(i).length();
				String code = getZeros(6-len)+i;
				String orginStr = FileUtils.getJsonFromNet(url+lxs[lx]+code, null,"gbk");//.replaceAll("\"", "").replaceAll(";", "");
				String left=orginStr.split("=")[0];
				String right = orginStr.split("=")[1];
				if(right.length()>4){
				  stock =new Stock();
			      stock.setId(UUIDUtils.getUUID());
			      stock.setCode(left.split("=")[0].substring(left.length()-6, left.length()));
			      stock.setName(right.split(",")[0].replace("\"", ""));
			      stock.setExchange(lxs[lx]);
			      log.info(left.split("=")[0].substring(left.length()-6, left.length())+","+right.split(",")[0].replace("\"", ""));
			      stocks.add(stock);
			      FileUtils.append(baseDir+"sz.txt", "sz"+stock.getCode()+"\n");
				}else{
					//log.info("code["+i+"]不可用！");
				}			
			}
		}
		
	}
	public static void initSHStockCodes() throws IOException{
		Stock stock = null;
		String[] lxs = new String[]{"sh"};
		List<Stock> stocks= new ArrayList<Stock>(); 
		for(int lx=0;lx<1;lx++){
			for(int i=1;i<999999;i++){
				int len = String.valueOf(i).length();
				String code = getZeros(6-len)+i;
				String orginStr = FileUtils.getJsonFromNet(url+lxs[lx]+code, null,"gbk");//.replaceAll("\"", "").replaceAll(";", "");
				String left=orginStr.split("=")[0];
				String right = orginStr.split("=")[1];
				if(right.length()>4){
				  stock =new Stock();
			      stock.setId(UUIDUtils.getUUID());
			      stock.setCode(left.split("=")[0].substring(left.length()-6, left.length()));
			      stock.setName(right.split(",")[0].replace("\"", ""));
			      stock.setExchange(lxs[lx]);
			      log.info(left.split("=")[0].substring(left.length()-6, left.length())+","+right.split(",")[0].replace("\"", ""));
			      stocks.add(stock);
			      FileUtils.append(baseDir+"sh.txt", "sh"+stock.getCode()+"\n");
				}else{
					//log.info("code["+i+"]不可用！");
				}			
			}
		}
		String path = FileUtils.getAbsolutePathAtClass(DrawCodesUtils.class)+"shstocks.data";
		log.info("股票代码写入文件："+path);
	}
	public static  String getZeros(int len){
		StringBuffer str = new StringBuffer();
		for(int i=1;i<=len;i++){
			str.append("0");
		}
		return str.toString();
	}
	
}
