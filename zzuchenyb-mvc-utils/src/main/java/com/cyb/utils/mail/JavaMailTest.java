package com.cyb.utils.mail;

import com.cyb.utils.date.DateUnsafeUtil;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;

public class JavaMailTest {
	public static void main(String[] args) throws MessagingException, GeneralSecurityException {
		QQServerInfor qqServer = new QQServerInfor();
		EmailInformation email = null;
		email = new EmailInformation
				.Builder()
				.from(qqServer.getAccount()+"@qq.com")
		        .to(qqServer.getDefaultTO())
				.emailContent("test !")
				.subject("邮件主题！")
				.date("201901252").build();
		MailUtils.sendEmail(email);
	}
}
