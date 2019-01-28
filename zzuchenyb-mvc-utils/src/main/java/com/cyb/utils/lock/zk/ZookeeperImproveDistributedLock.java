package com.cyb.utils.lock.zk;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkInterruptedException;

/**
 * 
 * @author DHUser
 * 改善所锁  解决惊群效应
 */
public class ZookeeperImproveDistributedLock  implements Lock {

	protected ZkClient client;

	protected String lockKey;

	protected long lockExpires;
	
	protected String currentPath;//临时序列当前节点
	
	protected String beforePath;//临时序列前一个节点

	public ZookeeperImproveDistributedLock(String lockKey, long lockExpires) {
		this.client = new ZkClient("localhost:2181");
		this.client.setZkSerializer(new ZKCustomSerializer());
		this.lockKey = lockKey;
		this.lockExpires = lockExpires;
		if(!client.exists(lockKey)){//先创建一个根节点
			try {
				this.client.createPersistent(lockKey);
			} catch (ZkInterruptedException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (ZkException e) {
				e.printStackTrace();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
			
		}
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
		this.client.subscribeDataChanges(beforePath, listener);
		//阻塞自己
		if(client.exists(beforePath)){
			try {
				cdl.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.client.unsubscribeDataChanges(beforePath, listener);
	}

	public void lockInterruptibly() throws InterruptedException {
		
	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}

	public void unlock() {
		client.delete(currentPath);
	}

	public boolean tryLock() {
		try {
			
			if(currentPath==null){
				//创建0000000x
				currentPath = this.client.createEphemeralSequential(lockKey+"/", "anystr");
			}
			List<String> childs = this.client.getChildren(lockKey);
			Collections.sort(childs);
			
			//判断当前节点是否最小的
			if(currentPath.equals(lockKey+"/"+childs.get(0))){
				return true;//成功获取锁
			}else{
				//取前一个节点
				//获取当前节点的索引号
				int curIndex = childs.indexOf(currentPath.substring(lockKey.length()+1));
				beforePath  = lockKey+"/"+childs.get(curIndex-1);
			}
		    return false;
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
	}

}