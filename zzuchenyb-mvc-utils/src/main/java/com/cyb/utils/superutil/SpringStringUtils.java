package com.cyb.utils.superutil;

import java.util.Set;

import org.springframework.util.StringUtils;

public class SpringStringUtils {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String str="1a,2b,3c,4d";
		Set<String> str1 = StringUtils.commaDelimitedListToSet(str);
		String[] str2 = StringUtils.commaDelimitedListToStringArray(str);
		StringUtils.uncapitalize(str);//首字母小写
		
		/*hasText：检查字符串中是否包含文本

		hasLength：检测字符串是否长度大于0

		isEmpty：检测字符串是否为空（若传入为对象，则判断对象是否为null）

		commaDelimitedStringToArray：逗号分隔的String转换为数组

		collectionToDelimitedString：把集合转为CSV格式字符串

		replace 替换字符串

		delimitedListToStringArray：相当于split
		uncapitalize：首字母小写

		collectionToDelimitedCommaString：把集合转为CSV格式字符串

		tokenizeToStringArray：和split基本一样，但能自动去掉空白的单词*/
		
		
		
	}
	
}
