package com.cyb.app.reptile;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年12月28日
 */
public class ProxyInfor {
	String ip;
	int port;

	public ProxyInfor(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	public ProxyInfor(String ipport){
		this.ip = ipport.split(":")[0];
		this.port = Integer.valueOf(ipport.split(":")[1]);
	}
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	
	public String toString(){
		return ip+":"+port;
	}

}
