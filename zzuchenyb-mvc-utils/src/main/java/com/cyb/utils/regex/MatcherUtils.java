package com.cyb.utils.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherUtils {
	static String IPV4EX="((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
	static String IPV6EX="((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
	public static void main(String[] args) {
		System.out.println(getIpv4s("a1.3.2.6aa 127.0.0.1 sdf"));
	}
	public static boolean matchIpv4(String str){
		return getMatcher(IPV4EX, str).matches();
	}
	public static boolean matchIpv6(String str){
		return getMatcher(IPV6EX, str).matches();
	}
	public static List<String> getIpv4s(String ipString){
		List<String> ips = new ArrayList<String>();
		Pattern p = Pattern.compile(IPV4EX);
		Matcher m = p.matcher(ipString);
		while (m.find()) {
			String result = m.group();
			ips.add(result);
		}
		return ips;
	}

	public static  Matcher getMatcher(String regex,String str){
		Pattern p = Pattern.compile(regex);
		 Matcher m = p.matcher(str);
		 return m;
	}
}
