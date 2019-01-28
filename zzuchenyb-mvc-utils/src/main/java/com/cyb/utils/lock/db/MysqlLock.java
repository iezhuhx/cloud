package com.cyb.utils.lock.db;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MysqlLock implements Lock{
    int id_num;
    DbLock dbLock;
	@Override
	public void lock() {
		if(tryLock()){
			return ;
		}
		try{
			Thread.sleep(10000);
		}catch(Exception e){
			
		}
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		
	}

	@Override
	public Condition newCondition() {
		return null;
	}

	@Override
	public boolean tryLock() {
		try{
		dbLock.insertLock(id_num);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public boolean tryLock(long arg0, TimeUnit arg1) throws InterruptedException {
		return false;
	}

	@Override
	public void unlock() {
		dbLock.deleteLock(id_num);
		
	}

}
