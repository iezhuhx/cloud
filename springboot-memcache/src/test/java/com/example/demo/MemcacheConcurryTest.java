package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.schooner.MemCached.MemcachedItem;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemcacheConcurryTest {

	String watchkeys = "watchkeys";
	int reqNums = 10000*100;// 并发人数
	CountDownLatch persons = new CountDownLatch(reqNums);// 并发人数计数器
	private String code="iphone8x";
	private int numberToSale = new Random().nextInt(100)+1;
	private int canBuyNum=new Random().nextInt(10)+1;
	private AtomicInteger successAcount=new AtomicInteger(0);
	private AtomicInteger saleGoods=new AtomicInteger(0);;
	@Autowired
	SockIOPool pool;
	
	@Autowired
	MemCachedClient cache;
	@Before
	public void init(){
		cache.delete(code);
		cache.add(code, numberToSale);
		System.out.println("并发总人数"+reqNums+" 初始化代售数量："+cache.get(code)+" 每人购买数量 "+canBuyNum);
	}
	
	
	@Test
	public void startTestRunnable(){
		for(int i=0;i<reqNums;i++){
			new Thread(new UserRequestRunnable(code, canBuyNum)).start();
		    persons.countDown();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("期望预售的结果 成功人数="+numberToSale/3+" 购买总台数="+(numberToSale/3)*canBuyNum);
		System.out.println("购买成功人数 "+successAcount.get());
		System.out.println("卖出iphone8x总数  "+saleGoods.get());
	}
	
	@Test
	public void startTestCallabe(){
		long s = System.currentTimeMillis();
		List<FutureTask<Boolean>> tasks = new ArrayList<>(reqNums);
		ExecutorService pool = Executors.newFixedThreadPool(2);
		for(int i=0;i<reqNums;i++){
			FutureTask<Boolean> task = new FutureTask<Boolean>(new UserRequestCallable(code, canBuyNum));
			tasks.add(task);
			pool.submit(task);
		    persons.countDown();
		}
		for(int i=0;i<reqNums;i++){
			try {
				tasks.get(i).get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		long e = System.currentTimeMillis();
		System.out.println("期望预售 成功人数="+numberToSale/canBuyNum+"\t卖出总台数="+(numberToSale/canBuyNum)*canBuyNum);
		System.out.println("实际销售  成功人数="+successAcount.get()+"\t卖出总台数= "+saleGoods.get());
		System.out.println("抢购耗时："+(e-s)/1000+"s"+(e-s)%1000+"ms");
	}
	@Test
	public void test(){
		System.out.println(cache);
		cache.add("hello", "memcached Test");//
		cache.delete("hello");
		cache.add("hello", "memcached Test2");//
		cache.add("hello1", "memcached Test1");//
		System.out.println("get value : " + cache.get("hello"));//ֵ
		System.out.println("get value1 : " + cache.get("hello1"));//ֵ
		System.out.println("****");
	}
	
	public boolean updateStock(String code,int buys){
		MemcachedItem item = cache.gets(code);
		long casUnique = item.getCasUnique();
		Integer amount = Integer.valueOf(item.getValue().toString().trim());
		if(amount<buys){
			return false;
		}
		if(cache.cas(code, amount-buys, casUnique)){
			return true;
		}
		try {
			Thread.sleep(new Random().nextInt(10)+1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return updateStock(code,buys);
		
	}
	
	
	class UserRequestRunnable implements Runnable {
		String code;
		int buys;

		public UserRequestRunnable(String code, int buys) {
			this.code = code;
			this.buys = buys;
		}

		@Override
		public void run() {
			try {
				persons.await();
				if(updateStock(code,buys)){
					synchronized (persons) {
						successAcount.incrementAndGet();
						saleGoods.addAndGet(buys);
					}
				}
			} catch (Exception e) {

			}
		}

	}
	
	class UserRequestCallable implements Callable<Boolean>  {
		String code;
		int buys;

		public UserRequestCallable(String code, int buys) {
			this.code = code;
			this.buys = buys;
		}

		public Boolean call() {
			try {
				persons.await();
				if(updateStock(code,buys)){
					synchronized (persons) {
						successAcount.incrementAndGet();
						saleGoods.addAndGet(buys);
					}
				}
				return true;
			} catch (Exception e) {
				return false;
			}
		}

	}
}
