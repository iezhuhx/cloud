package com.cyb.app.stock.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.cyb.app.reptile.ProxyRandomUtils;
import com.cyb.app.stock.Contants;
import com.cyb.app.stock.pub.RealQuotation;
import com.cyb.utils.date.DateUnsafeUtil;
import com.cyb.utils.file.FileUtils;
import com.cyb.utils.http.MyHttpClient;


/**
 * 实时行情计算
 * @author iechenyb
 *
 */
public class DrawQutoesUtils {
	static Log log = LogFactory.getLog(DrawQutoesUtils.class);
	public static String url="http://hq.sinajs.cn/list=";
	/**
	 * 
	 *@Author iechenyb<br>
	 *@Desc "梅雁吉祥,3.220,3.210,3.150,3.250,3.150,3.150,3.160,17871296,57115108.000,912800,3.150,235600,3.140,127600,3.130,26700,3.120,46800,3.110,170200,3.160,496800,3.170,90100,3.180,48600,3.190,343300,3.200,2020-12-09,15:00:02,00,";
	 *   转换为bean<br>
	 *@CreateTime 2020年12月9日 下午4:56:21
	 *@param qutoesStr
	 *@param code
	 *@return
	 */
	public static RealQuotation String2Qutoes(String qutoesStr,String code){	
		/*System.out.println("行情信息："+qutoesStr);
		if(qutoesStr.contains("FAILED")) return null;*/
		RealQuotation qutoes = new RealQuotation();
		//qutoes.setId(UUIDUtils.getUUID());
		if(StringUtils.isEmpty(new String(qutoesStr))) {System.out.println("blank qutoes"); return null;}
		try {
			String[] dataArr = qutoesStr.replaceAll("\"", "").replaceAll(";", "").split(",");
			qutoes.setCode(code);
			qutoes.setName(dataArr[Contants.NAME]);
			qutoes.setOpen(Double.valueOf(dataArr[Contants.OPEN]));
			qutoes.setPreclose(Double.valueOf(dataArr[Contants.PRECLOSE]) );
			qutoes.setHigh(Double.valueOf(dataArr[Contants.HIGH] ));
			qutoes.setLow(Double.valueOf(dataArr[Contants.LOW] ));
			qutoes.setPrice(Double.valueOf(dataArr[Contants.PRICE]) );
			qutoes.setClose(Double.valueOf(dataArr[Contants.PRICE] ));
			qutoes.setDay(dataArr[Contants.DAY] );
			qutoes.setTime(dataArr[Contants.TIME] );
			qutoes.setCje(Double.valueOf(dataArr[Contants.COLUMNCASH]) );
			qutoes.setCjl(Long.valueOf(dataArr[Contants.TURNVOLUME] ));
			qutoes.setRecordTime(DateUnsafeUtil.timeToMilis() );
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(qutoesStr+"->"+e.toString());
		}
		return qutoes;
	}
	//使用代理获取行情stockcode=sh00001，sh00002等
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
		System.out.println(code);
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
		 if(right.length()>4&&!right.contains("FAILED")){
			 RealQuotation qutoes = DrawQutoesUtils.String2Qutoes(right, code);
		     return qutoes;
		 }else{
			 return null;
		 }
	}
}
