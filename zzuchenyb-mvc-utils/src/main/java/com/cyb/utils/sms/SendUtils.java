package com.cyb.utils.sms;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.utils.http.MyHttpClient;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2019年1月15日
 */
public class SendUtils {
	Log log = LogFactory.getLog(SendUtils.class);
	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	public static int sendSms(String phone) {
		int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);
		String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");
		@SuppressWarnings("unused")
		NameValuePair[] data = { // 提交短信
				new NameValuePair("account", "C72562***"), // 查看用户名请登录用户中心->验证码、通知短信->帐户及签名设置->APIID
				new NameValuePair("password", "ad95c26915ec51bd68d87a72***"), // 查看密码请登录用户中心->验证码、通知短信->帐户及签名设置->APIKEY
				// new NameValuePair("password",
				// util.StringUtil.MD5Encode("密码")),
				new NameValuePair("mobile", phone), // 手机号码
				new NameValuePair("content", content), // 内容
		};
		@SuppressWarnings("unused")
		String SubmitResult = MyHttpClient.doPost(Url);
		return 0;
	}

	// 测试
	public static void main(String[] args) {
		sendSms("13521******");
	}
}
