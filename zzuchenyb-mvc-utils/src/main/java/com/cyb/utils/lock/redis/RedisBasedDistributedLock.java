package com.cyb.utils.lock.redis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

import com.cyb.utils.lock.AbstractLock;

import redis.clients.jedis.Jedis;

/**
 * 单线程处理，效率依旧高！reis默认的有效期 30分钟=1800秒
 * @author Administrator
 * 节点挂掉，做集群，机房停电，跨机房部署！
 * 有效时间：单节点时间如果为1秒，则有效期为2；
 *
 */
public class RedisBasedDistributedLock extends AbstractLock {

	private Jedis jedis;

	protected String lockKey;

	protected long lockExpires;

	public RedisBasedDistributedLock(Jedis jedis, String lockKey, long lockExpires) {
		this.jedis = jedis;
		this.lockKey = lockKey;
		this.lockExpires = lockExpires;
	}

	protected boolean lock(boolean useTimeout, long time, TimeUnit unit, boolean interrupt) throws InterruptedException {
		System.out.println("test1");
		if (interrupt) {
			checkInterruption();//如果当前线程中断，则中断，抛出异常！
		}
		long start = System.currentTimeMillis();
		long timeout = unit.toMillis(time); // if !useTimeout, then it's useless
		//循环获取锁
		while (useTimeout ? isTimeout(start, timeout) : true) {
			if (interrupt) {
				checkInterruption();
			}
			if(tryLock()){
				return true;
			}
		}
		return false;
	}
   //只获取一次锁
	public boolean tryLock() {
		//设置锁的过期时间=当前时间+过期时间+1
		long lockExpireTime = System.currentTimeMillis() + lockExpires + 1;
		String stringOfLockExpireTime = String.valueOf(lockExpireTime);
		//setnx = set if not exists
		if (jedis.setnx(lockKey, stringOfLockExpireTime) == 1) { //等于1说明不存在，添加成功！
			locked = true;
			setExclusiveOwnerThread(Thread.currentThread());
			return true;
		}
		//其他线程或者应用写入了值
		String value = jedis.get(lockKey);
		//存在且尚未过期，则等待.......直到过期
		if (value != null && isTimeExpired(value)) { // 存在key，并且已经过期
			//设置新值，返回旧值，如果旧值过期，则刚好可以获取锁
			String oldValue = jedis.getSet(lockKey, stringOfLockExpireTime);//原子操作
			if (oldValue != null && isTimeExpired(oldValue)) {
				locked = true;
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
		} else {
			// TODO lock is not expired, enter next loop retrying
			//当获得锁，并且正确处理完业务后，会进行删除key！此时value=null
		}
		return false;
	}

	/**
	 * Queries if this lock is held by any thread.
	 * 
	 * @return {@code true} if any thread holds this lock and {@code false}
	 *         otherwise
	 */
	public boolean isLocked() {
		if (locked) {
			return true;
		} else {
			String value = jedis.get(lockKey);
			return !isTimeExpired(value);
		}
	}

	@Override
	protected void unlock0() {
		String value = jedis.get(lockKey);
		if (!isTimeExpired(value)) {
			doUnlock();
		}
	}
    
	private void checkInterruption() throws InterruptedException {
		if (Thread.currentThread().isInterrupted()) {
			throw new InterruptedException();
		}
	}

	private boolean isTimeExpired(String value) {
		return Long.parseLong(value) < System.currentTimeMillis();
	}

	private boolean isTimeout(long start, long timeout) {
		return start + timeout > System.currentTimeMillis();
	}
    //删除key
	private void doUnlock() {
		jedis.del(lockKey);
	}

	public Condition newCondition() {
		return null;
	}
}
