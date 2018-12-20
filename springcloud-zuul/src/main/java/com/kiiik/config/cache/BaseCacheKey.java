package com.kiiik.config.cache;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.CollectionUtils;

import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.utils.RedisUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月20日
 */
public class BaseCacheKey {
	Log log = LogFactory.getLog(BaseCacheKey.class);
	private final Object[] params;
	private final int hashCode;
	private final String className;
	private final String methodName;
	private final String simpleName;
	private  String baseKey=KiiikContants.BLANK;//kiiik.zuul.baseKey.***
 
	public BaseCacheKey(Object target, Method method, Object[] elements) throws Exception{
		this.className=target.getClass().getName();
		this.simpleName=target.getClass().getSimpleName();
		this.methodName=getMethodName(method);
		this.params = new Object[elements.length];
		System.arraycopy(elements, 0, this.params, 0, elements.length);
		this.hashCode=generatorHashCode();
		initKey(method);
	}
	
	public BaseCacheKey(Object target, Method method) throws Exception{
		this.className=target.getClass().getName();
		this.simpleName=target.getClass().getSimpleName();
		this.methodName=getMethodName(method);
	    this.params = null;
	    StringBuilder sb = new StringBuilder();
        sb.append(target.getClass().getName());
        sb.append(method.getName());
     	this.hashCode= sb.toString().hashCode();
     	initKey(method);
	}
	//key#timeout#refresh
	public String initKey(Method method) throws Exception{
		List<String> keys = new ArrayList<>();
		if(method.isAnnotationPresent(Cacheable.class)){
			Cacheable a = method.getAnnotation(Cacheable.class);
			if(!ArrayUtils.isEmpty(a.cacheNames())){
				keys = Arrays.asList(a.cacheNames());
			}else{
				keys = Arrays.asList(a.value());
			}
		}else if(method.isAnnotationPresent(CachePut.class)){
			CachePut a = method.getAnnotation(CachePut.class);
			if(!ArrayUtils.isEmpty(a.cacheNames())){
				keys = Arrays.asList(a.cacheNames());
			}else{
				keys = Arrays.asList(a.value());
			}
		}else if(method.isAnnotationPresent(CacheEvict.class)){
			CacheEvict a = method.getAnnotation(CacheEvict.class);
			if(!ArrayUtils.isEmpty(a.cacheNames())){
				keys = Arrays.asList(a.cacheNames());
			}else{
				keys = Arrays.asList(a.value());
			}
		}else{
			throw new Exception("缓存key的value或者names格式不正确。格式=唯一名称#过期时间#刷新时间");
		}
		if(!CollectionUtils.isEmpty(keys)){
			baseKey = keys.get(0).split(KiiikContants.CACHE_SPLIT)[0];
		}else{
			baseKey=KiiikContants.BLANK;
		}
		return baseKey;
	}
	
	private String getMethodName(Method method){
		StringBuilder builder = new StringBuilder(method.getName());
		Class<?>[] types = method.getParameterTypes();
		if(types.length!=0){
			builder.append("(");
			for(Class<?> type:types){
				String name = type.getName();
				builder.append(name+",");
			}
			builder.append(")");
		}
		return builder.toString();
	}
 
	@Override
	public boolean equals(Object obj){
		if(this==obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseCacheKey o=(BaseCacheKey) obj;
		if(this.hashCode!=o.hashCode())
			return false;
		
		if (!Arrays.equals(params, o.params))
			return false;
		
		return true;
	}
	
	@Override
	public final int hashCode() {
		return hashCode;
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 参数为空时，每个对象都一样<br>
	 *创建时间: 2018年11月22日
	 *@return
	 */
	private int generatorHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hashCode;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
		if(params.length>0){
			result = prime * result + Arrays.deepHashCode(params);//没有考虑对象的情况,需要对每个对象参数进行重写hashcode方法
		}
		return result;
	}
    /**
     * 类名+方法名+name|value+hashcode
     */
	@Override
	public String toString() {
		/*return "BaseCacheKey [params=" + Arrays.deepToString(params) + ", className=" + className + ", methodName="
				+ methodName + "]";*/
	    return RedisUtils.keyBuilder(simpleName, baseKey, String.valueOf(hashCode));
	}
}
