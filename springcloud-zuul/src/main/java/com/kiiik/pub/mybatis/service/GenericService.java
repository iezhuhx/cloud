package com.kiiik.pub.mybatis.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.kiiik.pub.bean.KiiikPage;
import com.kiiik.pub.mybatis.bean.ComplexCondition;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年9月11日
 */
public interface GenericService {
	public int insertDBEntity(Object Object);
	
	public int insertDBEntityBatch(List<Object> entitys);

	public int deleteDBEntityByKey(Object Object);
	
	public int deleteDBEntity(Object Object);//根据所有的属性值删除记录
	
	public int deleteDBEntityByKeyBatchs( Object object,List<Integer> ids) throws Exception;
	
	public <T> int deleteDBEntityByKeyBatchs(Class<T> clazz,List<Integer> ids) throws InstantiationException, IllegalAccessException;

	public int updateDBEntityByKey(Object Object);
	
	
	public <T> int insertDBEntityT(T entity);
	
	public <T> int insertDBEntityBatchT(List<T> entitys);

	

	public <T> int deleteDBEntityByKeyT(T entity);
	
	public <T> int deleteDBEntityT(T entity);//根据所有的属性值删除记录
	
	public <T> int deleteDBEntityByKeyBatchsT(T entity,List<Integer> ids);
	
	/*public <T> int deleteDBEntityByKeyBatchsT(Class<T> clazz,List<Integer> ids);*/
	
	public <T> int updateDBEntityByKeyT(T entity);
	
	public <T> int updateDBEntity(T values,T condition);
	
	
	public <T> T queryDBEntitySingle(T entity);
	public <T> List<T> queryDBEntityList(T entity);
	public <T> List<T> queryDBEntityList(T entity,String... orderBys);
	public <T> Page<T> queryDBEntityList(T entity,int pageNum,int pageSize,String... orderBys);
	public <T> Page<T> queryDBEntityList(T entity,KiiikPage page,String... orderBys);
	//单表模糊查询
	public <T> T queryDBEntitySingleLike(T entity);
	public <T> List<T> queryDBEntityListLike(T entity);
	public <T> List<T> queryDBEntityListLike(T entity,String... orderBys);
	public <T> Page<T> queryDBEntityListLike(T entity,int pageNum,int pageSize,String... orderBys);
	public <T> Page<T> queryDBEntityListLike(T entity,KiiikPage page,String... orderBys);
	
	
	public <T> T queryDBEntitySingleComplex(Class<T> clazz,	ComplexCondition condition);
	public <T> List<T> queryDBEntityListComplex(Class<T> clazz,	ComplexCondition condition);
	public <T> Page<T> queryDBEntityListComplexTop(Class<T> clazz,	ComplexCondition condition, int top,String orderBys);
	public <T> Page<T> queryDBEntityListComplex(Class<T> clazz,	ComplexCondition condition, int pageNum, int pageSize);
	public <T> Page<T> queryDBEntityListComplex(Class<T> clazz,	ComplexCondition condition, int pageNum, int pageSize,String... orderBys);
	public <T> Page<T> queryDBEntityListComplex(Class<T> clazz,	ComplexCondition condition, KiiikPage page);
	public <T> Page<T> queryDBEntityListComplex(Class<T> clazz,	ComplexCondition condition, KiiikPage page,String... orderBys);

}
