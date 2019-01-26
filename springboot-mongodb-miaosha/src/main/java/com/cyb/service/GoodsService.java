package com.cyb.service;

public interface GoodsService {
 public int getStock(String code);
 public int updateStock(String code,int buys);
}
