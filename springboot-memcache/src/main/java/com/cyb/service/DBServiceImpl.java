package com.cyb.service;

import org.springframework.stereotype.Service;

@Service
public class DBServiceImpl implements GoodsService{

	@Override
	public int getStock(String code) {
		return 0;
	}

	@Override
	public boolean updateStock(String code, int buys) {
		return true;
	}

	@Override
	public void delStock(String code) {
		
	}

	@Override
	public void addStock(String code, int amount) {
	}
	
}
