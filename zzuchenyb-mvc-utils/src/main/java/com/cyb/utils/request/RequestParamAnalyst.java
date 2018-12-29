package com.cyb.utils.request;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.cyb.utils.Contants;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年11月2日
 */
@Component
public class RequestParamAnalyst extends AbstractRequestParamAnalysis {
	Log log = LogFactory.getLog(RequestParamAnalyst.class);
    /**
     * get 与 delete 
     */
	public String getParams(HttpServletRequest request) {
		 Map<String,String[]> map = request.getParameterMap();  
		 if(CollectionUtils.isEmpty(map)){
			 return Contants.BLANK;
		 }else{
			 return JSON.toJSONString(map);
		 }
	}
	//子系统的请求参数获取ok，当前系统参数获取失败
	/**
	 * post与put
	 */
	public String postParams(HttpServletRequest request) {
		String result = Contants.BLANK;
            if (ServletFileUpload.isMultipartContent(request)) {// 是否合适上传文件请求
    			return result;
    		} else {// 非上传文件请求
    			try {
    				 //可以接受url参数
    				 Map<String,String[]> map = request.getParameterMap(); 
    				 if(!CollectionUtils.isEmpty(map)){
    					result = JSON.toJSONString(map);
    				 }else{//RequestBody
    					 //FileCopyUtils.copyToByteArray(requestWrapper.getInputStream())
    					result =  new String(FileCopyUtils.copyToString(request.getReader()));
    					if(!StringUtils.isEmpty(result)){
    						return result.replace(" ", Contants.BLANK);//多余的空格去掉
    					}
    				 }
    			} catch (IOException e) {
    				return Contants.BLANK;
    			}
    		}
        //}
		return result;
	}
}
