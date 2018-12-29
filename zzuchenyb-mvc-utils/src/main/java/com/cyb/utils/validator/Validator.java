package com.cyb.utils.validator;

/**
 *作者 : iechenyb<br>
 *类描述: 验证规则<br>
 *创建时间: 2018年12月26日
 */
public interface Validator {
	boolean isIp(String value);//手机号
	boolean isUrl(String value);//手机号
	boolean isPhone(String value);//手机号
	boolean IsTelephone(String value);//座机号码
	boolean isEmail(String value);//电子邮箱
	boolean IsLowCharString(String value);//是否全小写
	boolean IsUpperCharString(String value);//是否全大写
	boolean idCard(String value);//身份证
	boolean isNumber(String value);//整数
	boolean isDate(String value);//是否是合法日期
	boolean isChinese(String value);//是否中文
	boolean isLetter(String value);//是否大小写字母组合
	boolean isPassword(String value);//校验密码 规则自己设置
	boolean IsPostalCode(String value);//邮政编码
	boolean isYear(String value);//1-12
	boolean isMonth(String value);//1-12
	boolean isDay(String value);//1-31
	boolean isHour(String value);//0-24
	boolean isMinute(String value);//0-59
	boolean isSecond(String value);//0-59
}
