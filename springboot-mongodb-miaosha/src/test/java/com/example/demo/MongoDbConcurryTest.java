package com.example.demo;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDbConcurryTest {

	String watchkeys = "watchkeys";
	int reqNums = 10000 * 10;// 并发人数
	CountDownLatch persons = new CountDownLatch(reqNums);// 并发人数计数器
	
	
	@Test
	public void test(){
		
	}
	class UserRequest implements Runnable {
		String code;
		int buys;

		public UserRequest(String code, int buys) {
			this.code = code;
			this.buys = buys;
		}

		@Override
		public void run() {
			try {
				persons.await();
			} catch (Exception e) {

			}
		}

	}
}
