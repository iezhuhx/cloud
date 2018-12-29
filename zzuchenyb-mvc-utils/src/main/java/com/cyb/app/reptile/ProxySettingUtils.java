package com.cyb.app.reptile;

public class ProxySettingUtils {
	public static void setServiceProxy(ProxyInfor proxy) {
		setServiceProxy(proxy.getIp(), String.valueOf(proxy.getPort()));
	}

	public static void setServiceProxy(String host, String port) {
		System.setProperty("http.proxyHost", host);
		System.setProperty("http.proxyPort", port);
		System.getProperties().setProperty("http.maxRedirects", "50");
		System.getProperties().setProperty("proxySet", "true");
	}

	public static void removeServiceProxy() {
		System.getProperties().remove("http.proxyHost");
		System.getProperties().remove("http.proxyPort");
		System.getProperties().remove("http.maxRedirects");
		System.getProperties().remove("proxySet");
	}
}