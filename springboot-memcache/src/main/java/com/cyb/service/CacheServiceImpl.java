package com.cyb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schooner.MemCached.MemcachedItem;
import com.whalin.MemCached.MemCachedClient;

@Service
public class CacheServiceImpl implements GoodsService{
	@Autowired
    MemCachedClient client;
	
	@Override
	public int getStock(String code) {
		Object ret = client.get(code);
		if(ret==null){
			return 0;
		}
		int stock = Integer.valueOf(ret.toString().trim());
		return stock;
	}
    //return client.decr(code,buys)>=0?true:false;//错误用法  =-1 没有code时 返回-1 10-100=-90 返回0 0-100 =-100 返回0 
	@Override
	public boolean updateStock(String code, int buys) {
		MemcachedItem item = client.gets(code);
		long casUnique = item.getCasUnique();
		Integer amount = Integer.valueOf(item.getValue().toString().trim());
		if(amount<buys){
			return false;
		}
		if(client.cas(code, amount-buys, casUnique)){
			return true;
		}
		try {
			Thread.sleep(1);//new Random().nextInt(10)+1
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return updateStock(code,buys);
	}

	@Override
	public void addStock(String code,int amount) {
		client.add(code, amount);
	}

	@Override
	public void delStock(String code) {
		client.delete(code);
	}

}
