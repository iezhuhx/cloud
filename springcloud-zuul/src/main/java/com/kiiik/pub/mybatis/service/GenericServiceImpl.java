package com.kiiik.pub.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;
import com.kiiik.pub.bean.KiiikPage;
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

	@Override
	public int deleteDBEntityByKeyBatchs(Object object, List<Integer> ids) throws Exception {
		if(CollectionUtils.isEmpty(ids)){
			throw new Exception("ids不能为空！");
		}
		return genericDao.deleteDBEntityByKeyBatchs(object, ids);
	}

	@Override
	public <T> T queryDBEntitySingleLike(T entity) {
		return genericDao.queryDBEntitySingleLike(entity);
	}

	@Override
	public <T> List<T> queryDBEntityListLike(T entity) {
		return genericDao.queryDBEntityListLike(entity);
	}

	@Override
	public <T> List<T> queryDBEntityListLike(T entity, String... orderBys) {
		return genericDao.queryDBEntityListLike(entity, orderBys);
	}

	@Override
	public <T> Page<T> queryDBEntityListLike(T entity, int pageNum, int pageSize, String... orderBys) {
		return genericDao.queryDBEntityListLike(entity, pageNum, pageSize, orderBys);
	}

	@Override
	public <T> int insertDBEntityT(T entity) {
		// TODO Auto-generated method stub
		return genericDao.insertDBEntityT(entity);
	}

	@Override
	public <T> int updateDBEntityByKeyT(T entity) {
		// TODO Auto-generated method stub
		return genericDao.updateDBEntityByKeyT(entity);
	}

	@Override
	public <T> int deleteDBEntityByKeyT(T entity) {
		// TODO Auto-generated method stub
		return genericDao.deleteDBEntityByKeyT(entity);
	}

	@Override
	public <T> int deleteDBEntityT(T entity) {
		// TODO Auto-generated method stub
		return genericDao.deleteDBEntityT(entity);
	}

	@Override
	public <T> int deleteDBEntityByKeyBatchsT(T entity, List<Integer> ids) {
		// TODO Auto-generated method stub
		return genericDao.deleteDBEntityByKeyBatchsT(entity, ids);
	}

	@Override
	public <T> int insertDBEntityBatchT(List<T> entitys) {
		// TODO Auto-generated method stub
		return genericDao.insertDBEntityBatchT(entitys);
	}

	@Override
	public <T> int updateDBEntity(T values, T condition) {
		return genericDao.updateDBEntity(values, condition);
	}

	@Override
	public <T> Page<T> queryDBEntityList(T entity, KiiikPage page, String... orderBys) {
		// TODO Auto-generated method stub
		return genericDao.queryDBEntityList(entity, page, orderBys);
	}

	@Override
	public <T> Page<T> queryDBEntityListLike(T entity, KiiikPage page, String... orderBys) {
		// TODO Auto-generated method stub
		return genericDao.queryDBEntityListLike(entity, page, orderBys);
	}

	@Override
	public <T> Page<T> queryDBEntityListComplex(Class<T> clazz, ComplexCondition condition, KiiikPage page) {
		// TODO Auto-generated method stub
		return genericDao.queryDBEntityListComplex(clazz, condition, page);
	}

	@Override
	public <T> Page<T> queryDBEntityListComplex(Class<T> clazz, ComplexCondition condition, KiiikPage page,
			String... orderBys) {
		// TODO Auto-generated method stub
		return genericDao.queryDBEntityListComplex(clazz, condition, page,orderBys);
	}

	@Override
	public <T> int deleteDBEntityByKeyBatchs(Class<T> clazz, List<Integer> ids) throws InstantiationException, IllegalAccessException {
		return genericDao.deleteDBEntityByKeyBatchs(clazz, ids);
	}
	
}
