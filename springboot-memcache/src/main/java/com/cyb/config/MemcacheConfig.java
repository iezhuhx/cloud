package com.cyb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

@Configuration
public class MemcacheConfig {

	String[] servers = { "127.0.0.1:11211" };
	Integer[] weights = { 3 };
	
    @Bean
	public SockIOPool initSocketIOPool(){
		SockIOPool pool = SockIOPool.getInstance();

		pool.setServers(servers);
		pool.setWeights(weights);

		pool.setInitConn(5);
		pool.setMinConn(5);
		pool.setMaxConn(250);
		pool.setMaxIdle(1000 * 60 * 60 * 6);

		pool.setMaintSleep(30);

		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setSocketConnectTO(0);

		pool.initialize();

		return pool;
	}
    @Bean
    public MemCachedClient  memCachecClient(){
    	MemCachedClient client = new MemCachedClient();
    	return client;
    }
}
