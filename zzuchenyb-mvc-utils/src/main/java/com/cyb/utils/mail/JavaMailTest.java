package com.cyb.utils.mail;
import java.security.GeneralSecurityException;

import javax.mail.MessagingException;

public class JavaMailTest {
	public static void main(String[] args) throws MessagingException, GeneralSecurityException {
		QQServerInfor qqServer = new QQServerInfor();
		EmailInformation email = new EmailInformation();
		email.setEmailContent("测试内容!");
		email.setFrom(qqServer.getAccount()+"@qq.com");
		email.setTo("1048417686@qq.com");
		email.setSubject("邮件主题！");
		MailUtils.sendEmail(email);
	}
}
