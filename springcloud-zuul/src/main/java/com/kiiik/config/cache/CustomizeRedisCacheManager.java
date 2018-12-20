package com.kiiik.config.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.util.Assert;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年11月21日
 */
public class CustomizeRedisCacheManager extends RedisCacheManager {
	Log log = LogFactory.getLog(CustomizeRedisCacheManager.class);

	CustomizeRedisCacheManager(RedisOperations<?, ?> redisOperations) {
		super(redisOperations);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected RedisCache createCache(String cacheName) {
		Assert.hasText(cacheName, "CacheName must not be null or empty!");

		String[] values = cacheName.split("#");

		long expiration = computeExpiration(values);
		return new RedisCache(values[0], (isUsePrefix() ? getCachePrefix().prefix(cacheName) : null),
				getRedisOperations(), expiration, false);
	}

	private long computeExpiration(String[] values) {
		if (values.length > 1) {
			return Long.parseLong(values[1]);
		}
		// 如果说想使用默认的过期时间而不指定特殊时间，则可以直接@Cacheable(cacheNames="name")，不需要加'#过期时间'了
		return super.computeExpiration(values[0]);
	}
}
