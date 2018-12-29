package com.cyb.utils.validator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月26日
 */
public class RexgexValidator extends AbstractValidator{
	Log log = LogFactory.getLog(RexgexValidator.class);
	public   boolean isIp(String value){return ValidatorUtils.isIP(value);}//手机号
	public   boolean isUrl(String value){return ValidatorUtils.IsUrl(value);}//手机号
	public   boolean isPhone(String value){return ValidatorUtils.isMobile(value);}//手机号
	public   boolean IsTelephone(String value){return ValidatorUtils.IsTelephone(value);}//座机号码
	public   boolean isEmail(String value){return ValidatorUtils.isEmail(value);}//电子邮箱
	public   boolean IsLowCharString(String value){return ValidatorUtils.IsLowChar(value);}//是否全小写
	public   boolean IsUpperCharString(String value){return ValidatorUtils.IsUpChar(value);}//是否全大写
	public   boolean idCard(String value){return ValidatorUtils.IsIDcard(value);}//身份证
	public   boolean isNumber(String value){return ValidatorUtils.IsIntNumber(value);}//整数
	public   boolean isDate(String value){return ValidatorUtils.isDate(value);}//是否是合法日期
	public   boolean isChinese(String value){return ValidatorUtils.IsChinese(value);}//是否中文
	public   boolean isLetter(String value){return ValidatorUtils.IsLetter(value);}//是否大小写字母组合
	public   boolean isPassword(String value){return ValidatorUtils.IsPassword(value);}//校验密码 规则自己设置
	public   boolean IsPostalCode(String value){return ValidatorUtils.IsPostalcode(value);}//邮政编码
	public   boolean isYear(String value){return true;}//1-12
	public   boolean isMonth(String value){return true;}//1-12
	public   boolean isDay(String value){return true;}//1-31
	public   boolean isHour(String value){return true;}//0-24
	public   boolean isMinute(String value){return true;}//0-59
	public   boolean isSecond(String value){return true;}//0-59
}
