package com.kiiik.utils.req;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.kiiik.pub.contant.KiiikContants;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年11月2日
 */
@Component
public class RequestParamAnalyst extends AbstractRequestParamAnalysis {
	Log log = LogFactory.getLog(RequestParamAnalyst.class);

	@Override
	public String getParams(HttpServletRequest request) {
		 Map<String,String[]> map = request.getParameterMap();  
		 if(CollectionUtils.isEmpty(map)){
			 return KiiikContants.BLANK;
		 }else{
			 return JSON.toJSONString(map);
		 }
	}
	//子系统的请求参数获取ok，当前系统参数获取失败
	@Override
	public String postParams(HttpServletRequest request) {
		String result = KiiikContants.BLANK;
            // 签名处理过程 end....
            if (ServletFileUpload.isMultipartContent(request)) {// 是否合适上传文件请求
    			return result;
    		} else {// 非上传文件请求
    			try {
    				 Map<String,String[]> map = request.getParameterMap(); 
    				 if(!CollectionUtils.isEmpty(map)){
    					result = JSON.toJSONString(map);
    				 }else{
    					 //FileCopyUtils.copyToByteArray(requestWrapper.getInputStream())
    					result =  new String(FileCopyUtils.copyToString(request.getReader()));
    					if(!StringUtils.isEmpty(result)){
    						return result.replace(" ", KiiikContants.BLANK);//多余的空格去掉
    					}
    				 }
    			} catch (IOException e) {
    				return KiiikContants.BLANK;
    			}
    		}
        //}
		return result;
	}
}
