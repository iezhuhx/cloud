package com.cyb.utils.superutil;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

public class TimerUtils {
	public static void test(long sleepTime){
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
  public static void main(String[] args) {
	  Stopwatch watch = Stopwatch.createStarted();
	  test(500);
	  long second = watch.elapsed(TimeUnit.MILLISECONDS);
	  System.out.println(second);
  }
}
