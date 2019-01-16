package com.cyb.utils.mail;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月15日
 */
interface  Builder {
	public void from(String from);
	public void to(String to);
	public void subject(String subject);
	public void emailContent(String emailContent);
	public void date(String date);
}
class EmailInformationBuilder implements Builder{
	EmailInformation emailInfor;
	EmailInformationBuilder(){
		emailInfor = new EmailInformation();
	}
	@Override
	public void from(String from) {
		// TODO Auto-generated method stub
		emailInfor.setFrom(from);
	}
	@Override
	public void to(String to) {
		// TODO Auto-generated method stub
		emailInfor.setTo(to);
	}
	@Override
	public void subject(String subject) {
		// TODO Auto-generated method stub
		emailInfor.setSubject(subject);
	}
	@Override
	public void emailContent(String emailContent) {
		// TODO Auto-generated method stub
		emailInfor.setEmailContent(emailContent);
	}
	@Override
	public void date(String date) {
		// TODO Auto-generated method stub
		emailInfor.setDate(date);
	}
	
}
public class EmailInformation {
	private String from;
	private String to;
	private String subject;
	private String emailContent;
	private String date;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
