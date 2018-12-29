package com.cyb.utils.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年12月26日
 */
public class PasswordValidator {
	
	public PasswordValidator(int minLen, int maxLen, String specialChars) {
		lenMin = minLen;
		lenMax = maxLen;
		specialChar = specialChars;
	}

	public PasswordValidator(String specialChars) {
		specialChar = specialChars;
	}

	public PasswordValidator(int minLen, int maxLen) {
		lenMin = minLen;
		lenMax = maxLen;
	}

	int lenMin = 6;
	int lenMax = 8;
	String specialChar = "!-_#!@";
	String numberPassword = "[0-9]{" + lenMin + "," + lenMax + "}";
	String letterPassword = "[a-zA-Z]{" + lenMin + "," + lenMax + "}";
	String letterOrNumberPassword = "[0-9a-zA-Z]{" + lenMin + "," + lenMax + "}";// 包含头两种
	String letterAndNumberPassword = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{" + lenMin + "," + lenMax
			+ "}$";
	String letterAndNumberAndSpecialCharPassword = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z" + specialChar + "]{" + lenMin + "," + lenMax
			+ "}$";

	public boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 指定长度，纯数字 纯字母或者数字字母组合
	 * 作者 : iechenyb<br>
	 * 方法描述: 说点啥<br>
	 * 创建时间: 2017年7月15日
	 * 
	 * @param str
	 * @return
	 */
	public boolean numOrLetterPassword(String str) {
		return match(letterOrNumberPassword, str);
	}

	/**
	 * 指定长度，必须同时包含数字 字母和特殊字符 
	 * 作者 : iechenyb<br>
	 * 方法描述: 说点啥<br>
	 * 创建时间: 2017年7月15日
	 * 
	 * @param str
	 * @return
	 */
	public boolean numAndLetterAndSpecialCharPassword(String str) {
		return match(letterAndNumberAndSpecialCharPassword, str);
	}
	/**
	 * 指定长度，必须同时包含数字 字母
	 * 作者 : iechenyb<br>
	 * 方法描述: 说点啥<br>
	 * 创建时间: 2017年7月15日
	 * 
	 * @param str
	 * @return
	 */
	public boolean numAndLetterPassword(String str) {
		return match(letterAndNumberPassword, str);
	}

	/**
	 * 要求：由数字和字母组成，并且要同时含有数字和字母，且长度要在8-16位之间。
	 * ^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$ 分开来注释一下： ^ 匹配一行的开头位置
	 * (?![0-9]+$) 预测该位置后面不全是数字 (?![a-zA-Z]+$) 预测该位置后面不全是字母 [0-9A-Za-z] {8,16}
	 * 由8-16位数字或这字母组成 $ 匹配行结尾位置 注：(?!xxxx) 是正则表达式的负向零宽断言一种形式，标识预该位置后不是xxxx字符。 附：
	 * 要求：可以包含数字、字母、下划线，并且要同时含有数字和字母，且长度要在8-16位之间。
	 * ^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{8,16}$
	 */
}
