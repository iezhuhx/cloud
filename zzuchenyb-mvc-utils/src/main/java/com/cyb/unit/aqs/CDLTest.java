package com.cyb.unit.aqs;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.utils.random.RandomUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月31日
 */
public class CDLTest {
	Log log = LogFactory.getLog(CDLTest.class);
	public static void main(String[] args) {
	       CountDownLatch begin = new CountDownLatch(1);
	       CountDownLatch end = new CountDownLatch(5);
	       /*计数器大于线程个数，阻塞，使用错误！
	        *计数器小于线程数，则按照先进先服务的原则，运行先到的服务！实际情况并不是
	        *随机选择两个任务或者多个任务,同样非正常使用！！
	        *计数器与线程数必须相等！
	        */
	       for(int i=1; i<=50; i++){
	           Thread thread = new Thread(
	        		   new Player(begin,end,i));
	           thread.start();
	       }
	       try{
	           System.out.println("the race begin");
	           begin.countDown();
	           end.await();
	           System.out.println("the race end");
	       }catch(Exception e){
	            e.printStackTrace();
	       }

	    }
}
	/**
	 * 选手
	 */
	class Player implements Runnable{

	    private CountDownLatch begin;

	    private CountDownLatch end;
	    private int id;

	    Player(CountDownLatch begin,CountDownLatch end,int id){
	        this.begin = begin;
	        this.end = end;
	        this.id=id;
	    }

	    public void run() {
	        
	        try {
	        	 Thread.sleep(RandomUtils.getNum(1, 10)*100);
	            begin.await();
	            Thread.currentThread().setName("选手"+id);
	           
	            System.out.println(Thread.currentThread().getName() + " arrived !");;
	            end.countDown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	    }
	}

