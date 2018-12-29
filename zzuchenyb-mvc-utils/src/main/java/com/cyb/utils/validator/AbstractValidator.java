package com.cyb.utils.validator;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月26日
 */
public abstract class AbstractValidator implements Validator{
	public   boolean isIp(String value){return true;}//手机号
	public   boolean isUrl(String value){return true;}//手机号
	public   boolean isPhone(String value){return true;}//手机号
	public   boolean IsTelephone(String value){return true;}//座机号码
	public   boolean isEmail(String value){return true;}//电子邮箱
	public   boolean IsLowCharString(String value){return true;}//是否全小写
	public   boolean IsUpperCharString(String value){return true;}//是否全大写
	public   boolean idCard(String value){return true;}//身份证
	public   boolean isNumber(String value){return true;}//整数
	public   boolean isDate(String value){return true;}//是否是合法日期
	public   boolean isChinese(String value){return true;}//是否中文
	public   boolean isLetter(String value){return true;}//是否大小写字母组合
	public   boolean isPassword(String value){return true;}//校验密码 规则自己设置
	public   boolean IsPostalCode(String value){return true;}//邮政编码
	public   boolean isYear(String value){return true;}//1-12
	public   boolean isMonth(String value){return true;}//1-12
	public   boolean isDay(String value){return true;}//1-31
	public   boolean isHour(String value){return true;}//0-24
	public   boolean isMinute(String value){return true;}//0-59
	public   boolean isSecond(String value){return true;}//0-59
}
