package com.cyb.service;

import org.springframework.stereotype.Service;

@Service
public class DBServiceImpl implements GoodsService{

	@Override
	public int getStock(String code) {
		return 0;
	}

	@Override
	public int updateStock(String code, int buys) {
		return 0;
	}

}
