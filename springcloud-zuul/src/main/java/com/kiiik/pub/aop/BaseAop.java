package com.kiiik.pub.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.web.property.KiiikProperties;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年11月22日
 */
public class BaseAop {
	Log log = LogFactory.getLog(BaseAop.class);
	
	@Autowired
	GenericService genericService;

	@Autowired
	KiiikProperties kiiik;

	protected Method  getMethod(ProceedingJoinPoint pjp){
		Signature signature = pjp.getSignature();    
		MethodSignature methodSignature = (MethodSignature)signature;    
		Method targetMethod = methodSignature.getMethod();
		return targetMethod;
	}
	
	protected <T extends Annotation> T getMethodAnnotation(ProceedingJoinPoint pjp, Class<T> clazz) {
		return getMethod(pjp).getAnnotation(clazz);
	}

	protected <T extends Annotation> T getMethodAnnotation(JoinPoint joinPoint, Class<T> clazz) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		return method.getAnnotation(clazz);
	}

	// 获取的方法无法正常获取注解方法
	protected Method getMethod(JoinPoint joinPoint) throws NoSuchMethodException, SecurityException {
		// 拦截的实体类
		Object target = joinPoint.getTarget();
		// 拦截的方法名称
		String methodName = joinPoint.getSignature().getName();
		// 拦截的方法参数
		// 拦截的放参数类型
		Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
		Method method = target.getClass().getMethod(methodName, parameterTypes);
		return method;
	}

	// 获取的方法无法正常获取注解方法
	protected Method getMethod2(JoinPoint joinPoint)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		MethodInvocationProceedingJoinPoint methodPoint = (MethodInvocationProceedingJoinPoint) joinPoint;
		Field proxy = methodPoint.getClass().getDeclaredField("methodInvocation");
		proxy.setAccessible(true);
		ReflectiveMethodInvocation j = (ReflectiveMethodInvocation) proxy.get(methodPoint);
		Method method = j.getMethod();
		return method;
	}

	protected boolean isRequestFromUrl(Method m) {
		return m.isAnnotationPresent(RequestMapping.class) || m.isAnnotationPresent(GetMapping.class)
				|| m.isAnnotationPresent(PostMapping.class) || m.isAnnotationPresent(PutMapping.class);
	}

	public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
		// 目标方法名：
		log.info(joinPoint.getSignature().getName());
		if (exception instanceof NullPointerException) {
			log.info("发生了空指针异常!!!!!");
		}
	}

	protected void recordVisitLog() {
		try {
			RequestAttributes ra = RequestContextHolder.getRequestAttributes();
			if (ra != null) {
				ServletRequestAttributes sra = (ServletRequestAttributes) ra;
				HttpServletRequest request = sra.getRequest();
				if (!StringUtils.isEmpty(request.getRemoteUser())) {
				}
			}
		} catch (Exception e) {
			// 日志异常容错，不进行记录
			e.printStackTrace();
		}
	}
}
