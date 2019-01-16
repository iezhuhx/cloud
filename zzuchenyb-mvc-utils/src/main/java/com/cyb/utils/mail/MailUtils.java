package com.cyb.utils.mail;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.utils.response.R;
import com.sun.mail.util.MailSSLSocketFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月15日
 */
public class MailUtils {
	Log log = LogFactory.getLog(MailUtils.class);
	public static Properties getQQServerConfig() throws GeneralSecurityException{
		Properties props = new Properties();
		// 开启debug调试
		props.setProperty("mail.debug", "true");
		// 发送服务器需要身份验证
		props.setProperty("mail.smtp.auth", "true");
		// 设置邮件服务器主机名
		props.setProperty("mail.host", "smtp.qq.com");
		props.setProperty("mail.smtp.port", "465");
		// 发送邮件协议名称 jlrwhqbevplwbjea
		props.setProperty("mail.transport.protocol", "smtp");
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.socketFactory", sf);
		return props;
	} 
	
	public static Session getQQJavaMailSession() throws GeneralSecurityException, MessagingException{
		Session session = Session.getInstance(getQQServerConfig());
		return session;
	}
	
	public static R<String> sendEmail(EmailInformation email) throws NoSuchProviderException, GeneralSecurityException, MessagingException{
		Session session = getQQJavaMailSession();
		Transport transport=session.getTransport();
		QQServerInfor server = new QQServerInfor();
		transport.connect(server.getAccount()
				,server.getEntry());
		//发送地址 多个代表群发
		transport.sendMessage(convert(session,email), 
				new Address[] { new InternetAddress(
				email.getTo()) });
		transport.close();
		return new R<String>();
	}
	
	public static Message convert(Session session,EmailInformation email) throws MessagingException{
		Message msg = new MimeMessage(session);
		msg.setSubject(email.getSubject());
		// 设置邮件内容
		msg.setText(email.getEmailContent());
		// 设置发件人
		msg.setFrom(new InternetAddress(email.getFrom()));
		return msg;
	}
}
