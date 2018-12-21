package com.cyb.utils.request;

import javax.servlet.http.HttpServletRequest;

import com.cyb.utils.Contants;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月2日
 */
public abstract class AbstractRequestParamAnalysis  implements RequestParamAnalysis{
	public  String putParams(HttpServletRequest request){
		return Contants.BLANK;
	}
	
	public  String deleteParams(HttpServletRequest request){
		return Contants.BLANK;
	}
	
	public  String parseParams(HttpServletRequest request){
		String methodUpper = request.getMethod().toUpperCase();
		if("GET".equals(methodUpper)){
			return getParams(request);
		}else if("POST".equals(methodUpper)){
			return postParams(request);
		}else if("PUT".equals(methodUpper)){
			return postParams(request);
		}else if("DELETE".equals(methodUpper)){
			return getParams(request);
		}
		return Contants.BLANK;
	}
}
