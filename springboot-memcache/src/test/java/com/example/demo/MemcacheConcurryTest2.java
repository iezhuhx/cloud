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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.cyb.service.CacheServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemcacheConcurryTest2 {

	int reqNums = 10000*1;// 并发人数
	int relReqNums = 0;// 并发人数
	CountDownLatch persons = null;// 并发人数计数器
	private String code="iphone8x";
	private int numberToSale = 0;
	private int canBuyNum=0;
	private AtomicInteger successAcount;
	private AtomicInteger saleGoods;
	@Autowired
	CacheServiceImpl service;
	
	//@Before
	public void init(){
		numberToSale = new Random().nextInt(100)+1;
		canBuyNum=new Random().nextInt(10)+1;
		relReqNums = new Random().nextInt(reqNums)+10000;
		persons = new CountDownLatch(relReqNums);
		successAcount=new AtomicInteger(0);
		saleGoods=new AtomicInteger(0);
		service.delStock(code);
		service.addStock(code, numberToSale);
		System.out.println("并发总人数"+relReqNums+" 初始化代售数量："+service.getStock(code)+" 每人购买数量 "+canBuyNum);
	}
	
	
	@Test
	public void loopSendRequest(){
		for(int i=0;i<1000;i++){
			init();
			startTestCallabe(i+1);
			System.out.println("--------------------------------------------");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void startTestCallabe(int idx){
		long s = System.currentTimeMillis();
		
		List<FutureTask<Boolean>> tasks = new ArrayList<>(relReqNums);
		ExecutorService pool = Executors.newFixedThreadPool(2);
		for(int i=0;i<relReqNums;i++){
			FutureTask<Boolean> task = new FutureTask<Boolean>(new UserRequestCallable(code, canBuyNum));
			tasks.add(task);
			pool.submit(task);
		    persons.countDown();
		}
		for(int i=0;i<relReqNums;i++){
			try {
				tasks.get(i).get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		long e = System.currentTimeMillis();
		System.out.println(idx+"--期望预售 成功人数="+numberToSale/canBuyNum+"\t卖出总台数="+(numberToSale/canBuyNum)*canBuyNum);
		System.out.println("实际销售  成功人数="+successAcount.get()+"\t卖出总台数= "+saleGoods.get());
		Assert.isTrue((numberToSale/canBuyNum)==successAcount.get());//购买成功人数是否预期
		Assert.isTrue(((numberToSale/canBuyNum)*canBuyNum)==saleGoods.get());//购买数量是否预期值
		System.out.println("抢购耗时："+(e-s)/1000+"s"+(e-s)%1000+"ms");
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
				if(service.updateStock(code,buys)){
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
				if(service.updateStock(code,buys)){
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
