package com.cyb.utils.computer;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

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

	public static void show() {
		try {
			// 用 getLocalHost() 方法创建的InetAddress的对象
			InetAddress address = InetAddress.getLocalHost();
			System.out.println(address.getHostName());// 主机名
			System.out.println(address.getCanonicalHostName());// 主机别名
			System.out.println(address.getHostAddress());// 获取IP地址
			System.out.println("===============");

			// 用域名创建 InetAddress对象
			InetAddress address1 = InetAddress.getByName("bms.kiiik.com");
			// 获取的是该网站的ip地址，如果我们所有的请求都通过nginx的，所以这里获取到的其实是nginx服务器的IP地址
			System.out.println(address1.getHostName());// www.wodexiangce.cn
			System.out.println(address1.getCanonicalHostName());// 124.237.121.122
			System.out.println(address1.getHostAddress());// 124.237.121.122
			System.out.println("===============");

			// 用IP地址创建InetAddress对象
			InetAddress address2 = InetAddress.getByName("220.181.111.188");
			System.out.println(address2.getHostName());// 220.181.111.188
			System.out.println(address2.getCanonicalHostName());// 220.181.111.188
			System.out.println(address2.getHostAddress());// 220.181.111.188
			System.out.println("===============");

			// 根据主机名返回其可能的所有InetAddress对象
			InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");
			for (InetAddress addr : addresses) {
				System.out.println(addr);
				// www.baidu.com/220.181.111.188
				// www.baidu.com/220.181.112.244
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	public static String getServerIp() {
        // 获取操作系统类型
        String sysType = System.getProperties().getProperty("os.name");
        String ip;
        if (sysType.toLowerCase().startsWith("win")) {  // 如果是Windows系统，获取本地IP地址
            String localIP = null;
            try {
                localIP = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
            }
            if (localIP != null) {
                return localIP;
            }
        } else {
            ip = getIpByEthNum("eth0"); // 兼容Linux
            if (ip != null) {
                return ip;
            }
        }
        return "获取服务器IP错误";
    }
	 /**
     * 根据网络接口获取IP地址
     * @param ethNum 网络接口名，Linux下是eth0
     * @return
     */
    private static String getIpByEthNum(String ethNum) {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (ethNum.equals(netInterface.getName())) {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = (InetAddress) addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
        }
        return "获取服务器IP错误";
    }
	public static void main(String[] args) {
		/*
		 * System.out.println(ComputerUtil.getRealIP());
		 * System.out.println(ComputerUtil.getName());
		 * System.out.println(getMacAddress(ComputerUtil.getRealIP()));
		 * System.out.println(getMacAddress("192.168.108.221"));//不可用
		 */
		System.out.println(getDefaultIP());// 127.0.0.1
		System.out.println(getRealIP());// 127.0.0.1
		System.out.println(getDefaultIP());
		show();
		System.out.println(CmdUtils.getLocalIPForJava());
		System.out.println(CmdUtils.getLocalIPForCMD());
		System.out.println(getServerIp());
		// setSystemTime("2018-01-01", "12:56:32");//修改系统的时间
	}
}
