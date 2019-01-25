package com.cyb.app.stock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
			log.error(qutoes+"->"+e.toString());
		}
		return qutoes;
	}
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
	public static String getOriginQutoes(String stockcode) throws IOException{
		String orginStr = FileUtils.getJsonFromNet(url + stockcode, null, "gbk");// .replaceAll("\"",
		String right = orginStr.split("=")[1];
		if (right.length() > 4) {
			return right;
		}else{
			return "";
		} 
	}
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
}
