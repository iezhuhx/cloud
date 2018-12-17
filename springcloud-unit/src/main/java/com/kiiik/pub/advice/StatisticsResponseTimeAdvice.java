package com.kiiik.pub.advice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.kiiik.config.SwitchProperties;
import com.kiiik.pub.bean.res.ResultBean;
import com.kiiik.pub.context.TimeContext;

import net.sf.json.JSONObject;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年4月23日
 */
@ControllerAdvice
public class StatisticsResponseTimeAdvice implements ResponseBodyAdvice<Object> {
	Log logger = LogFactory.getLog(StatisticsResponseTimeAdvice.class);
	@Autowired
	SwitchProperties switchPro;
	@Override
	public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
	}

	@Override
	public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
			Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
			ServerHttpResponse serverHttpResponse) {
		
		if (!methodParameter.hasMethodAnnotation(ResponseBody.class)&&
				(methodParameter.getContainingClass().getAnnotation(RestController.class)==null)) {
			return o;
		} else { 
			if(switchPro.isReturnTimeToView()){
				if(methodParameter.hasMethodAnnotation(PostMapping.class)){
					PostMapping rq = methodParameter.getMethodAnnotation(PostMapping.class);
					return customerReturnObject(o,rq.value()[0]) ;
				}else{
					return customerReturnObject(o,"".intern()) ;
				}
			}else{
				return o;
			}
		}
	}
	
	public  Object customerReturnObject(Object o,String fid){
		if(o==null){ 
			return o;
		}
		if(switchPro.isShowControllerTime()&&TimeContext.getTime()>switchPro.getShowOverExeTime()){//并发的时候，将controller执行时间大于100毫秒的方法进行记录
			logger.info("vasservice["+fid+"]方法执行时间 "+TimeContext.getTime());
		}
		if(o.getClass().getSimpleName().contains("ResultBean")){
			//自定义对象上新增一个参数，也可以，不需要在其他bean上定义一个time字段
			@SuppressWarnings("unchecked")
			ResultBean<Object> ret = (ResultBean<Object>) o;
			if(ret.getD()==null){
				ret.setD("");
			}
			JSONObject object = JSONObject.fromObject(o);
			if(switchPro.isShowControllerTime()){
				object.put("time", TimeContext.getTime());
			}
			return object;
		} else if(o.getClass().getSimpleName().contains("VO")) {
			ResultBean<Object> ret  = new ResultBean<Object>();
			ret.data(o).success("执行成功");
			JSONObject object = JSONObject.fromObject(ret);
			if(switchPro.isShowControllerTime()){
				object.put("time", TimeContext.getTime());
			}
			return object;
		}else
		{ 
			return o;
		}
	}
}