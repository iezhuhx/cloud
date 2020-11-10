package com.cyb.utils.text;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.app.commondb.TestBean;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月27日
 */
public class StringUtils {
	Log log = LogFactory.getLog(StringUtils.class);
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 去掉首尾空格<br>
	 *创建时间: 2017年7月15日
	 *@param value
	 *@return
	 */
	public String trim(String value){
		if(org.springframework.util.StringUtils.isEmpty(value)) return "";
		return value.replaceAll("^\\s+|\\s+$", ""); 
	}
	
	
	public static <T>  String object2String(T t){
		return ToStringBuilder.reflectionToString(t);
	}
	
	public static void main(String[] args) {
		System.out.printf("%8s","12345678");
		System.out.println();
		System.out.printf("%8s","1234");
		System.out.println();
		System.out.printf("%8s","中国");
		System.out.println();
		System.out.printf("%-8s","5678");
		System.out.println();
		System.out.printf("%-8s","中国");
		System.out.println();
		TestBean bean = new TestBean();
		bean.setRq("123456");
		System.out.println(object2String(bean));
	}
}
