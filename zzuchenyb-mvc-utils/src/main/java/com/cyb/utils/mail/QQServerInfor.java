package com.cyb.utils.mail;

import lombok.Data;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月15日
 */
@Data
public class QQServerInfor {
	public String account="38306505996";
	public  String entry="jlrwhqbevplwbjea";
	public  String defaultTO="104841768656@qq.com";
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
	public String getDefaultTO() {
		return defaultTO;
	}
	public void setDefaultTO(String defaultTO) {
		this.defaultTO = defaultTO;
	}
	
}
