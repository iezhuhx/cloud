package com.cyb.utils.text;
import java.text.MessageFormat;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月26日
 */
public class MessageFormatUtils {
	
	Log log = LogFactory.getLog(MessageFormatUtils.class);
	public static void main(String[] args) {
		String expression = "param1={0},param2={1},param3={2},param1={0}";
		Object arr[] = { "he","ha中文"};
		String str = fillString(expression,arr);
		System.out.println(str);
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: {n}可以复用，参数不足时，{n}直接显示原内容<br>
	 *创建时间: 2017年7月15日
	 *@param expression
	 *@param params
	 *@return
	 */
	public static String fillString(String expression,Object[] params){
		MessageFormat mf = new MessageFormat(expression, Locale.CHINESE);
		return mf.format(params);
	}
	
	
	
}
