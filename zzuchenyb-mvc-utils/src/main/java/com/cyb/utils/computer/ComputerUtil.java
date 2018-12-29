package com.cyb.utils.computer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ComputerUtil {
	private static InetAddress addr;
	static {
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public static String getRealIP() {
		String ip = addr.getHostAddress().toString();// 获得本机IP
		return ip;
	}

	public static String getDefaultIP() {
		return "127.0.0.1";
	}

	public static String getDefaultIPDomain() {
		return "localhost";
	}

	public static String getName() {
		String computerName = addr.getHostName().toString();// 获得本机IP
		return computerName;
	}

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 获取本地mac地址<br>
	 * 创建时间: 2017年7月15日hj12
	 * 
	 * @param host
	 * @return
	 */
	public static String getMacAddress(String host) {
		String mac = "";
		StringBuffer sb = new StringBuffer();
		try {
			NetworkInterface ni = NetworkInterface.getByInetAddress(InetAddress.getByName(host));
			byte[] macs = ni.getHardwareAddress();
			for (int i = 0; i < macs.length; i++) {
				mac = Integer.toHexString(macs[i] & 0xFF);
				if (mac.length() == 1) {
					mac = '0' + mac;
				}
				sb.append(mac + "-");
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		mac = sb.toString();
		mac = mac.substring(0, mac.length() - 1);
		return mac;

	}

	public static String setSystemTime(String date, String time) {
		String osName = System.getProperty("os.name");
		String dateTimeMessage = date + " " + time;
		try {
			if (osName.matches("^(?i)Windows.*$")) { // Window 系统
				String cmd;

				cmd = " cmd /c date " + date; // 格式：yyyy-MM-dd
				Runtime.getRuntime().exec(cmd);

				cmd = " cmd /c time " + time; // 格式 HH:mm:ss
				Runtime.getRuntime().exec(cmd);
			} else if (osName.matches("^(?i)Linux.*$")) {// Linux 系统
				String command = "date -s " + "\"" + date + " " + time + "\"";// 格式：yyyy-MM-dd
																				// HH:mm:ss
				Runtime.getRuntime().exec(command);
			} else {

			}
		} catch (IOException e) {
			return e.getMessage();
		}
		return dateTimeMessage;
	}

	public static void main(String[] args) {
		/*
		 * System.out.println(ComputerUtil.getRealIP());
		 * System.out.println(ComputerUtil.getName());
		 * System.out.println(getMacAddress(ComputerUtil.getRealIP()));
		 * System.out.println(getMacAddress("192.168.108.221"));//不可用
		 */
		setSystemTime("2018-01-01", "12:56:32");
	}
}
