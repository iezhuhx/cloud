package com.kiiik.pub.aop;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.kiiik.config.redis.KiiikRedisClient;
import com.kiiik.pub.ann.KiiikCachesParam;
import com.kiiik.pub.ann.KiiikCachesParams;
import com.kiiik.utils.RedisUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月22日
 */
@Component
@Aspect
@Order(5)
public class CacheUpdateAop extends BaseAop{
	Log log = LogFactory.getLog(CacheUpdateAop.class);
	
	@Autowired
	CacheManager cacheManager;
	
	@Autowired
	KiiikRedisClient redisClient;
	
	 /**
     * Service层切点
     */
    @Pointcut("@annotation(com.kiiik.pub.ann.KiiikCachesParams)")    
    public void cacheAspect() {
        
    }
    
    @Around(value = "cacheAspect()")
	public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object obj = null;
		obj = proceedingJoinPoint.proceed();// 调用执行目标方法
		try{
			evitRedisCacheData(proceedingJoinPoint);//异常忽略
		}finally{}
		return obj;
	}

	private void evitRedisCacheData(ProceedingJoinPoint proceedingJoinPoint ) throws Exception {
		KiiikCachesParams cachesParams = getMethodAnnotation(proceedingJoinPoint, KiiikCachesParams.class);
		for(KiiikCachesParam cacheParam:cachesParams.caches()){
			String key = RedisUtils.keyBuilder(cacheParam.clazz(), cacheParam.cacheName());
			System.err.println("开始清除缓存"+key);
			redisClient.batchDelByKey(key);
		}
	}
    
}
