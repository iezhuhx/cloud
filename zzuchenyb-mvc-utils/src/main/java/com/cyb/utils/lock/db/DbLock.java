package com.cyb.utils.lock.db;
/**
 * id 为主键
 * 数据库支持行级锁
 * @author Administrator
 *
 */
public interface DbLock  {
  public void insertLock(int id);//同一个id只能有一个插入成功，其他均失败！
  public void deleteLock(int id);//释放锁，删除数据
}
