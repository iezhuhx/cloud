package com.kiiik.pub.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *作者 : iechenyb<br>
 *类描述: 缓存清除项集合<br>
 *创建时间: 2018年11月22日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KiiikCachesParams {
    /**
     * A list of {@link KiiikCachesParam}s available to the API operation.
     */
	KiiikCachesParam[] caches();
}