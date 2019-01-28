package com.cyb.utils.lock.zk;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkInterruptedException;

/**
 * 容易出现惊群效应危害：
 *  巨大的服务器性能省耗
 *  网络冲击
 *  可能造成宕机
 * @author DHUser
 *
 */
public class ZookeeperBasedDistributedLock implements Lock {

	protected ZkClient client;

	protected String lockKey;

	protected long lockExpires;

	public ZookeeperBasedDistributedLock(String lockKey, long lockExpires) {
		this.client = new ZkClient("localhost:2181");
		this.lockKey = lockKey;
		this.lockExpires = lockExpires;
	}
	
	public ZookeeperBasedDistributedLock(String lockKey) {
		this.client = new ZkClient("localhost:2181");
		this.lockKey = lockKey;
		this.lockExpires = 10;
	}
	public Condition newCondition() {
		return null;
	}

	public void lock() {
		if(!tryLock()){
			//没有获得锁，阻塞自己
			waitForLock();
			lock();
		}
	}

	private void waitForLock() {
		final CountDownLatch cdl = new CountDownLatch(1);
		IZkDataListener listener = new IZkDataListener() {
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("变更节点为：" + s + "，变更数据为：" + o);
            }
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("删除的节点为：" + s);//某一个客户端删除节点时，同时有大部分人释放了锁
                cdl.countDown();
            }
        };
        //订阅删除事件
		this.client.subscribeDataChanges(lockKey, listener);
		//阻塞自己
		if(client.exists(lockKey)){
			try {
				cdl.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.client.unsubscribeDataChanges(lockKey, listener);
	}

	public void lockInterruptibly() throws InterruptedException {
		
	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}

	public void unlock() {
		client.delete(lockKey);
	}

	public boolean tryLock() {
		try {
			client.createEphemeral(lockKey);//创建一个有序临时节点
		} catch (ZkInterruptedException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		} catch (ZkException e) {
			e.printStackTrace();
			return false;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return false;
		}//创建一个临时节点
		return true;
	}
}
