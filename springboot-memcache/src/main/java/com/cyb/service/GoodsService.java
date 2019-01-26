package com.cyb.service;

public interface GoodsService {
 public void delStock(String code);
 public void addStock(String code,int amount) ;
 public int getStock(String code);
 public boolean updateStock(String code,int buys);
}
