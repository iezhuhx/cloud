package com.cyb.app.stock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.cyb.app.reptile.ProxyRandomUtils;
import com.cyb.app.stock.pub.Contants;
import com.cyb.app.stock.pub.RealQuotation;
import com.cyb.utils.date.DateUnsafeUtil;
import com.cyb.utils.file.FileUtils;
import com.cyb.utils.http.MyHttpClient;
import com.cyb.utils.uuid.UUIDUtils;


/**
 * 实时行情计算
 * @author iechenyb
 *
 */
public class DrawQutoesUtils {
	static Log log = LogFactory.getLog(DrawQutoesUtils.class);
	public static String url="http://hq.sinajs.cn/list=";
	public static RealQuotation String2Qutoes(String qutoesStr,String code){	
		RealQuotation qutoes = new RealQuotation();
		qutoes.setId(UUIDUtils.getUUID());
		if(StringUtils.isEmpty(new String(qutoesStr))) {System.out.println("blank qutoes"); return null;}
		try {
			String[] dataArr = qutoesStr.replaceAll("\"", "").replaceAll(";", "").split(",");
			qutoes.setCode(code);
			qutoes.setName(dataArr[Contants.NAME]);
			qutoes.setOpen(dataArr[Contants.OPEN]);
			qutoes.setPreclose(dataArr[Contants.PRECLOSE] );
			qutoes.setHigh(dataArr[Contants.HIGH] );
			qutoes.setLow(dataArr[Contants.LOW] );
			qutoes.setPrice(dataArr[Contants.PRICE] );
			qutoes.setClose(dataArr[Contants.PRICE] );
			qutoes.setDay(dataArr[Contants.DAY] );
			qutoes.setTime(dataArr[Contants.TIME] );
			qutoes.setCash(dataArr[Contants.COLUMNCASH] );
			qutoes.setVolumn(dataArr[Contants.TURNVOLUME] );
			qutoes.setRecordTime(DateUnsafeUtil.timeToMilis() );
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(qutoesStr+"->"+e.toString());
		}
		return qutoes;
	}
	//使用代理获取行情
	public static String getOriginQutoesUseProxy(String stockcode) throws IOException{
		String orginStr = FileUtils.getJsonFromNet(url + stockcode, null, "gbk");// .replaceAll("\"",
		orginStr = MyHttpClient.doGet(url + stockcode, ProxyRandomUtils.findUseableProxyRandom());
		String right = orginStr.split("=")[1];
		if (right.length() > 4) {
			return right;
		}else{
			return "";
		} 
	}
	//用代理批量抓取
	public static List<RealQuotation> getOriginQutoesBatchUseProxy(String stockcode) throws IOException{
		String orginStr = FileUtils.getJsonFromNet(url + stockcode, null, "gbk");// .replaceAll("\"",
		orginStr = MyHttpClient.jsoupGet(url + stockcode, ProxyRandomUtils.findUseableProxyRandom());
				//MyHttpClient.doGet(url + stockcode, ProxyRandomUtils.findUseableProxyRandom());
		System.out.println(orginStr);
		
		String[] strs = orginStr.split(";");
		List<RealQuotation> lst = new ArrayList<RealQuotation>();
		for(String str:strs){
			RealQuotation rq = getQutoesByString(str);
			if(rq!=null){
				lst.add(rq);
			}
		}
		return lst;
	}
	//读取原始行情
	public static String getOriginQutoes(String stockcode) throws IOException{
		String orginStr = FileUtils.getJsonFromNet(url + stockcode, null, "gbk");// .replaceAll("\"",
		return orginStr;
	}
	//批量读取
	public static List<RealQuotation> getRealQutoesBatch(String code) throws IOException{
		String orginStr = FileUtils.getJsonFromNet(url+code, null,"gbk");//.replaceAll("\"", "").replaceAll(";", "");
		String[] strs = orginStr.replaceAll("\n", "#").split("#");
		List<RealQuotation> lst = new ArrayList<RealQuotation>();
		for(String str:strs){
			RealQuotation rq = getQutoesByString(str);
			if(rq!=null){
				lst.add(rq);
			}
		}
		return lst;
	}
	//根据原始行情数据解析行情数据
	public static RealQuotation getQutoesByString(String orginStr) throws IOException{
		log.info(orginStr);
		String left=orginStr.split("=")[0]; 
		String right = orginStr.split("=")[1];
		if(org.springframework.util.StringUtils.isEmpty(orginStr)) return null;
		String code = left.split("=")[0].substring(left.length()-8, left.length());
		 if(right.length()>4){
			 RealQuotation qutoes = DrawQutoesUtils.String2Qutoes(right, code);
		     return qutoes;
		 }else{
			 return null;
		 }
	}
}
