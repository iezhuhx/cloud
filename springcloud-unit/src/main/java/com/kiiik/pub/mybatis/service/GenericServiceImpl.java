package com.kiiik.pub.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.mybatis.dao.GenericDao;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2genericDao.18年9月11日
 */
@Service
public class GenericServiceImpl implements GenericService {

	@Autowired
	GenericDao genericDao;
	
	@Override
	public int insertDBEntity(Object object) {
		return genericDao.insertDBEntity(object);
	}

	@Override
	public int updateDBEntityByKey(Object Object) {
		// TODO Auto-generated method stub
		return genericDao.updateDBEntityByKey(Object);
	}

	@Override
	public int deleteDBEntityByKey(Object Object) {
		// TODO Auto-generated method stub
		return genericDao.deleteDBEntityByKey(Object);
	}

	@Override
	public int deleteDBEntity(Object Object) {
		// TODO Auto-generated method stub
		return genericDao.deleteDBEntity(Object);
	}

	@Override
	public int insertDBEntityBatch(List<Object> entitys) {
		return genericDao.insertDBEntityBatch(entitys);
	}


	//================================================================	
	
	public <T> List<T> queryDBEntityList(T entity){
		return genericDao.queryDBEntityList(entity);
	}

	@Override
	public <T> T queryDBEntitySingle(T entity) {
		return genericDao.queryDBEntitySingle(entity);
	}

	@Override
	public <T> List<T> queryDBEntityList(T entity, String... orderBys) {
		return genericDao.queryDBEntityList(entity,orderBys);
	}

	@Override
	public <T> Page<T> queryDBEntityList(T entity, int pageNum, int pageSize, String... orderBys) {
		return genericDao.queryDBEntityList(entity, pageNum, pageSize, orderBys);
	}

	@Override
	public <T> T queryDBEntitySingleComplex(Class<T> clazz, ComplexCondition condition) {
		return genericDao.queryDBEntitySingleComplex(clazz, condition);
	}

	@Override
	public <T> List<T> queryDBEntityListComplex(Class<T> clazz, ComplexCondition condition) {
		return genericDao.queryDBEntityListComplex(clazz, condition);
	}

	@Override
	public <T> Page<T> queryDBEntityListComplexTop(Class<T> clazz, ComplexCondition condition, int top,
			String orderBys) {
		return genericDao.queryDBEntityListComplexTop(clazz, condition, top, orderBys);
	}

	@Override
	public <T> Page<T> queryDBEntityListComplex(Class<T> clazz, ComplexCondition condition, int pageNum, int pageSize) {
		return genericDao.queryDBEntityListComplex(clazz, condition, pageNum, pageSize);
	}

	@Override
	public <T> Page<T> queryDBEntityListComplex(Class<T> clazz, ComplexCondition condition, int pageNum, int pageSize,
			String... orderBys) {
		return genericDao.queryDBEntityListComplex(clazz, condition, pageNum, pageSize,orderBys);
	}
	
}
