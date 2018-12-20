package com.kiiik.pub.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.kiiik.config.cache.BaseCacheKey;
import com.kiiik.pub.bean.R;
import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.pub.context.TimeContext;
import com.kiiik.utils.EnvUtils;
import com.kiiik.utils.RequestUtils;
import com.kiiik.web.log.bean.SystemLog;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 */
@Component
@Aspect
@Order(2)
public class AuthControllerAop extends BaseAop{
	Log log = LogFactory.getLog(AuthControllerAop.class);

	@Pointcut("execution(* com.kiiik.web.*.controller.*.*(..))")
	public void controller() {}

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 异常直接抛出，不在控制逻辑里处理！<br>
	 * 创建时间: 2017年7月15日hj12
	 * 
	 * @param proceedingJoinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "controller()")
	public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object obj = null;
		long s = System.currentTimeMillis();
		obj = proceedingJoinPoint.proceed();// 调用执行目标方法
		long e = System.currentTimeMillis();
		TimeContext.setTime(e - s);
		recordVisitLog(proceedingJoinPoint);
		return obj;
	}
	
	public Object doAfterThrowingAdvice(Throwable exception, Class<?> retType) {
		// 目标方法名：
		if (exception instanceof NullPointerException) {
			NullPointerException e = (NullPointerException) exception;
			log.error("发生了空指针异常!!!!!" + e.getMessage());
		} else {
			log.error("发生了异常！" + exception.getMessage());
		}

		if (retType == null) {
			return null;// 空方法
		} else if (R.class.toString().equals(retType.toString())) {
			// 如果是500或者404直接跳转到全局定义的页面
			return new R<Object>(exception);
		} else if (ModelAndView.class.toString().equals(retType.toString())) {
			// 如果是500或者404直接跳转到全局定义的页面
			ModelAndView view = new ModelAndView();
			view.addObject("msg", exception.getMessage());
			view.setViewName("/exception/500");
			return view;
		} else {// 其他返回类型"error"
			try {
				return retType.newInstance();// 异常时，返回一个指定类型的空对象
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	@Autowired
	EnvUtils env;
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 直接使用转换后的参数信息<br>
	 *创建时间: 2017年7月15日hj12
	 *@param proceedingJoinPoint
	 * @throws Exception 
	 */
	private void recordVisitLog(ProceedingJoinPoint proceedingJoinPoint) throws Exception {
		if(KiiikContants.DEV.equals(env.getActiveProfile())){
			try {
					Signature signature = proceedingJoinPoint.getSignature();    
					MethodSignature methodSignature = (MethodSignature)signature;    
					Method targetMethod = methodSignature.getMethod();
					if(targetMethod.isAnnotationPresent(CacheEvict.class)||targetMethod.isAnnotationPresent(Cacheable.class)||
							targetMethod.isAnnotationPresent(CachePut.class)){
						BaseCacheKey key = new BaseCacheKey(proceedingJoinPoint.getTarget(),targetMethod,proceedingJoinPoint.getArgs());
						System.out.println(key.hashCode()+"\t"+key);
				    }
			} catch (SecurityException e1) {
				e1.printStackTrace();
			}
		}
		Object[] args = proceedingJoinPoint.getArgs();//参数 
		List<Object> args_new= new ArrayList<Object>();
		for(int i=0;i<args.length;i++){
			if(args[i]==null){
				continue;
			}
			if(!args[i].toString().contains("RequestWrapper")&&
				!args[i].toString().contains("ResponseWrapper")
			){
				args_new.add(args[i]);//去掉包装类参数
			}
		}
		try {
			RequestAttributes ra = RequestContextHolder.getRequestAttributes();
			if (ra != null) {
				ServletRequestAttributes sra = (ServletRequestAttributes) ra;
				HttpServletRequest request = sra.getRequest();
				if (!StringUtils.isEmpty(request.getRemoteUser())) {
					SystemLog log= RequestUtils.getSystemLog(request,false);
					if(args!=null){
						log.setParam(JSON.toJSONString(args_new));//将request和response去掉
					}
					log.setModule(KiiikContants.ZUULNAME);
					genericService.insertDBEntity(log);
				}
			}
		} catch (Exception e) {
			// 日志异常容错，不进行记录
			e.printStackTrace();
		}
	}

}
