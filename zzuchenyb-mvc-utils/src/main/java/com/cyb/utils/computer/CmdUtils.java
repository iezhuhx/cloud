package com.cyb.utils.computer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.telnet.TelnetClient;

import com.cyb.app.reptile.ProxyInfor;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2017年7月27日
 */
public class CmdUtils {
	static Log log = LogFactory.getLog(CmdUtils.class);

	public static boolean exeCMD(String command) {
		boolean result = true;
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("cmd.exe " + command);
			if (process.getErrorStream().read() != -1) {
				result = false;
			}
			process.destroy();
			System.out.println("oer");
		} catch (Exception e) {
			result = false;
			log.info("Exe command error!");
		}
		return result;
	}

	public static String exeCMDWithResult(String command) {
		StringBuilder sb = new StringBuilder();
		try {
			Process p = Runtime.getRuntime().exec("cmd.exe /c " + command);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "gbk"));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + ",");
			}
			br.close();
			p.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String getLocalIPForCMD() {
		StringBuilder sb = new StringBuilder();
		String command = "cmd.exe /c ipconfig | findstr IPv4";
		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				line = line.substring(line.lastIndexOf(":") + 2, line.length());
				sb.append(line + ",");
			}
			br.close();
			p.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String getLocalIPForJava() {
		StringBuilder sb = new StringBuilder();
		try {
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			while (en.hasMoreElements()) {
				NetworkInterface intf = (NetworkInterface) en.nextElement();
				Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
				while (enumIpAddr.hasMoreElements()) {
					InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()
							&& inetAddress.isSiteLocalAddress()) {
						sb.append(inetAddress.getHostAddress().toString() + "\n");
					}
				}
			}
		} catch (SocketException e) {
		}
		return sb.toString();
	}
	public static boolean telnet(ProxyInfor proxy){
		return telnet(proxy.getIp(),proxy.getPort());
	}
	@SuppressWarnings("unused")
	public static boolean telnet(String ip, int port) {
		TelnetClient telnet = new TelnetClient();
		try {
			telnet.setConnectTimeout(500);//2秒则判定不可达
			telnet.connect(ip, port);
			return true;
		} catch (IOException e) {
			//e.printStackTrace();
			String msgtext = ip + ":" + port + " is not reachable!";
			//System.out.println(msgtext);
			try {
				telnet.disconnect();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
			return false;
		}
		
	}

	public static boolean ping(String ip) {
		try {
			return InetAddress.getByName(ip).isReachable(100000);
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(ip+"不通！");
			return false;
		} 
	}

	public static void main(String[] args) {
		// java -version 不可以查看
		/*
		 * System.out.println(exeCMDWithResult("netstat -a"));
		 * System.out.println(exeCMDWithResult("ipconfig | findstr IPv4"));
		 */
		// System.out.println(getLocalIPForCMD());
		// System.out.println(getLocalIPForJava());
		/*for (int i = 0; i < 1; i++) {
			System.out.println(i + "," + exeCMDWithResult("ping 180.169.108.228").contains("找不到主机"));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		System.out.println(telnet("192.168.108.224", 8085));
		System.out.println(ping("192.168.108.224"));
	}
}
