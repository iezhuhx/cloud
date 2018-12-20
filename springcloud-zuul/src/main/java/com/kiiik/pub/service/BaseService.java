package com.kiiik.pub.service;
import org.springframework.beans.factory.annotation.Autowired;

import com.kiiik.pub.mybatis.dao.GenericDao;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
public class BaseService {
	
	@Autowired
	protected GenericDao genericDao;
}
