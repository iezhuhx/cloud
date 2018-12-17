package com.kiiik.utils;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月3日下午3:05:37
 */
public class RequestUtils {
	Log log = LogFactory.getLog(RequestUtils.class);
	
	public static String getProjectAbsPath(HttpServletRequest req){
		return req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+"/"+req.getContextPath();
	}
}
