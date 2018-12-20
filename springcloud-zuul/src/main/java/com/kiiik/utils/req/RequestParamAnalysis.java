package com.kiiik.utils.req;

import javax.servlet.http.HttpServletRequest;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月2日
 */
public interface RequestParamAnalysis {
	public String getParams(HttpServletRequest request);
	String postParams(HttpServletRequest request);
	String putParams(HttpServletRequest request);
	String deleteParams(HttpServletRequest request);
	String parseParams(HttpServletRequest request);
}
