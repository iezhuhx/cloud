package com.kiiik.pub.mybatis.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kiiik.pub.mybatis.annotation.DBColumn;
import com.kiiik.pub.mybatis.annotation.DBEntity;
import com.kiiik.pub.mybatis.annotation.KeyColumn;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.mybatis.bean.ComplexConditionNode;
import com.kiiik.pub.mybatis.bean.EntityInfo;
import com.kiiik.pub.mybatis.bean.EntityInfoCol;
import com.kiiik.pub.mybatis.mapper.GenericMybatisMapper;

import tk.mybatis.orderbyhelper.OrderByHelper;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年9月11日
 */
@Repository
public class GenericDaoImpl implements GenericDao {
	Log log = LogFactory.getLog(GenericDaoImpl.class);
	
	@Autowired
	private GenericMybatisMapper genericDao;

	private Map<String, EntityInfo> cache = new HashMap<String, EntityInfo>();

	private EntityInfo getInfoNoValue(Class<? extends Object> clasz) {

		String className = clasz.getName();
		// 使用类名从缓存读取
		if (cache.get(className) != null) {
			try {
				return cache.get(className).clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		// 如果没有则检查注解
		checkDBEntity(clasz);

		EntityInfo info = new EntityInfo();
		info.setClassName(className);

		// 获取表信息
		DBEntity dbEntity = (DBEntity) clasz.getAnnotation(DBEntity.class);
		if (StringUtils.isNotBlank(dbEntity.database())) {
			info.setDatabase(dbEntity.database());
		}
		info.setTableName(dbEntity.value());
		// 获取字段信息
		Field[] fields = clasz.getDeclaredFields();
		for (Field eachField : fields) {
			EntityInfoCol curCol = new EntityInfoCol();
			// 读取注解
			DBColumn dbColumn = eachField.getAnnotation(DBColumn.class);
			if (dbColumn == null)
				continue;
			String entityColName = eachField.getName();// class 字段名
			String dbColName = dbColumn.value(); // db字段名
			String insertIfNull = dbColumn.insertIfNull().trim();
			String updateIfNull = dbColumn.updateIfNull().trim();
			// 表字段默认与class字段相同
			if (StringUtils.isBlank(dbColName)) {
				dbColName = entityColName;
			}

			curCol.setEntityColName(entityColName);
			curCol.setDbColName(dbColName);
			if (StringUtils.isNotBlank(insertIfNull)) {
				curCol.setInsertIfNull(insertIfNull);
			}
			if (StringUtils.isNotBlank(updateIfNull)) {
				curCol.setUpdateIfNull(updateIfNull);
			}

			KeyColumn keyColumn = eachField.getAnnotation(KeyColumn.class);
			if (keyColumn != null) {
				curCol.setIsKeyColumn(true);
				curCol.setUseGeneratedKeys(keyColumn.useGeneratedKeys());
			} else {
				curCol.setIsKeyColumn(false);
				curCol.setUseGeneratedKeys(false);
			}

			info.getCols().add(curCol);
		}
		cache.put(className, info);
		return info;
	}

	private EntityInfo getInfoWithValue(Object entity) {
		EntityInfo config = getInfoNoValue(entity.getClass());
		for (EntityInfoCol colConfg : config.getCols()) {
			try {
				colConfg.setValue(PropertyUtils.getProperty(entity, colConfg.getEntityColName()));
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalArgumentException("read property from entity error");
			}
		}
		return config;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void checkDBEntity(Class clazz) {
		DBEntity dbEntity = (DBEntity) clazz.getAnnotation(DBEntity.class);
		if (dbEntity == null)
			throw new IllegalArgumentException("not dbEntity ,plase check your beans with [DBEntity] annotation");

	}

	/*
	 * (non-Javadoc)
	 * 更新或者是插入
	 * @see
	 * com.kingdee.finance.p2p.service.generic.GenericService#saveDBEntity(java.
	 * lang.Object)
	 */
	public int saveDBEntity(Object entity) {
		EntityInfo info = getInfoWithValue(entity);
		if (info.getKeyCol().getValue() == null) {
			return insertDBEntity(entity);
		} else {
			return updateDBEntityByKey(entity);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kingdee.finance.p2p.service.generic.GenericService#insertDBEntity(
	 * java.lang.Object)
	 */
	public int insertDBEntity(Object entity) {
		EntityInfo info = getInfoWithValue(entity);
		int rows = genericDao.insertDBEntity(info);
		// 使用生成的自增长主键
		Object generatedKey = info.getGeneratedKey();
		EntityInfoCol col = info.getKeyCol();
		try {
			// 原主键为空才填入 否则不填
			if (col.getValue() == null) {
				BeanUtils.setProperty(entity, col.getEntityColName(), generatedKey);
			}
		} catch (Exception e) {
			throw new RuntimeException("set generatedKey error", e.fillInStackTrace());
		}
		return rows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.finance.p2p.service.generic.GenericService#
	 * insertDBEntityBatch(java.util.List)
	 */
	public int insertDBEntityBatch(List<Object> entitys) {
		List<EntityInfo> infos = new LinkedList<EntityInfo>();
		for (Object each : entitys) {
			infos.add(getInfoWithValue(each));
		}
		int rows = genericDao.insertDBEntityBatch(infos);
		return rows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.finance.p2p.service.generic.GenericService#
	 * updateDBEntityByKey(java.lang.Object)
	 */
	public int updateDBEntityByKey(Object entity) {
		EntityInfo info = getInfoWithValue(entity);
		if (!info.hasKeyCol() || info.getKeyCol().getValue() == null) {
			throw new IllegalArgumentException("update cannot done when key property is null");
		}
		int rows = genericDao.updateDBEntityByKey(info);
		return rows;
	}
	
	public int updateDBEntityByKeyBatch(Object entity){
		
		return 0;
	}
	
	public int deleteDBEntityByKeyBatch(){
		
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.finance.p2p.service.generic.GenericService#
	 * deleteDBEntityByKey(java.lang.Object)
	 */
	public int deleteDBEntityByKey(Object entity) {
		EntityInfo info = getInfoWithValue(entity);
		if (!info.hasKeyCol()) {
			throw new IllegalArgumentException("update cannot done when key property is null");
		}
		int rows = genericDao.deleteDBEntityByKey(info);
		return rows;
	}

	/**
	 * order解析 将bean property 解析为数据库 column
	 * 
	 * @param entity
	 * @param orderBys
	 * @return
	 */
	private String orderParse(Class<?> clazz, String... orderBys) {
		if (orderBys == null) {
			return null;
		}
		EntityInfo info = getInfoNoValue(clazz);
		// 排序解析处理--将bean property 解析为数据库 column
		StringBuffer orderStr = new StringBuffer();
		for (int i = 0; i < orderBys.length; i++) {
			String eachOrderBy = orderBys[i];
			if (StringUtils.isBlank(eachOrderBy)) {
				continue;
			}
			String[] orderArray = eachOrderBy.split("\\s+");
			if (orderArray.length < 1) {
				throw new IllegalArgumentException("order parse error.");
			}
			String order = orderArray[0];
			String sort = null;
			if (orderArray.length == 1) {
				sort = "ASC";
			} else {
				sort = orderArray[1];
				if (!StringUtils.equalsIgnoreCase(sort, "asc") && !StringUtils.equalsIgnoreCase(sort, "desc")) {
					throw new IllegalArgumentException("order sort parse error.");
				}
			}
			EntityInfoCol infoCol = info.getColByEntityColName(order);
			orderStr.append(infoCol.getDbColName()).append("\t").append(sort);
			if (i < orderBys.length - 1) {
				orderStr.append(",");
			}
		}
		return orderStr.toString();
	}
	
	private <T> T getFirst(List<T> list){
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	/* (non-Javadoc)
	 * @see com.kingdee.finance.p2p.service.generic.GenericService#queryDBEntity(T)
	 */
	public <T> T queryDBEntitySingle(T entity){
		return getFirst(queryDBEntityList(entity));
	}
	
	/* (non-Javadoc)
	 * @see com.kingdee.finance.p2p.service.generic.GenericService#queryDBEntityList(T)
	 */
	public <T> List<T> queryDBEntityList(T entity){
		return queryDBEntityList(entity, "");
	}
	public <T> List<T> queryDBEntityList(T entity,String... orderBys){
		return queryDBEntityList(entity, 1, 0, orderBys);
	}
	
	
	/* (non-Javadoc)
	 * @see com.kingdee.finance.p2p.service.generic.GenericService#queryDBEntityList(java.lang.Object, int, java.lang.String[])
	 */
	public <T> List<T> queryDBEntityListTop(T entity, int top, String... orderBys) {
		// TODO Auto-generated method stub
		return queryDBEntityList(entity, 1, top, orderBys);
	}
	
	@SuppressWarnings("unchecked")
	public <T> Page<T> queryDBEntityList(T entity,int pageNum,int pageSize,String... orderBys){
		EntityInfo info=getInfoWithValue(entity);
		if(orderBys!=null&&!"".equals(orderBys)&&orderBys.length>0&&!"".equals(orderBys[0])){
			//StringBuffer sb = new StringBuffer(" order by ");
			List<String> orderCols = new ArrayList<String>();
			for(int i=0;i<orderBys.length;i++){
				//sb.append(orderBys[i]+",");
				if(!org.springframework.util.StringUtils.isEmpty(orderBys[i])){
					String a = orderBys[i];
					if(a.toUpperCase().contains("ASC")||a.toUpperCase().contains("DESC")){
						orderCols.add(orderBys[i]);//非空字段进行查询
				    }
				}
			}
			//info.setOrderCols(sb.substring(0, sb.length()-1));
			info.setOrderCols(orderCols);
		}
		/*
		 * String orderStr=this.orderParse(entity.getClass(), orderBys);
		if(StringUtils.isNotBlank(orderStr)){
			OrderByHelper.orderBy(orderStr);
		}*/
		//分页
		PageHelper.startPage(pageNum, pageSize);
		Page<Map<String, Object>> list = genericDao.queryDBEntity(info);
		Page<T> result= (Page<T>) list.clone();
		result.clear();
		for(Map<String, Object> eachBeanMap:list){
			T eachObject = null;
			try {
				eachObject = (T)(entity.getClass().newInstance());
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			
			try {
				BeanUtils.copyProperties(eachObject, eachBeanMap);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			result.add(eachObject);
		}
		return result;
	}
	/**
	 * 不设置属性值的时候  代表删除所有数据
	 */
	@Override
	public int deleteDBEntity(Object entity) {
		EntityInfo info = getInfoWithValue(entity);
		if (!info.hasKeyCol()) {
			throw new IllegalArgumentException("update cannot done when key property is null");
		}
		int rows = genericDao.deleteDBEntity(info);
		return rows;
	}
	//----------------------------复杂的单表查询-------------------------------------
	public <T> T queryDBEntitySingleComplex(Class<T> clazz,	ComplexCondition condition){
		return getFirst(queryDBEntityListComplex(clazz, condition));
	}
	public <T> List<T> queryDBEntityListComplex(Class<T> clazz,	ComplexCondition condition) {
		return queryDBEntityListComplex(clazz, condition, 1, 0);
	}
	
	public <T> Page<T> queryDBEntityListComplexTop(Class<T> clazz,	ComplexCondition condition, int top,String orderBys) {
		return queryDBEntityListComplex(clazz, condition, 1, top,orderBys);
	}
	
	public <T> Page<T> queryDBEntityListComplex(Class<T> clazz,	ComplexCondition condition, int pageNum, int pageSize) {
		return queryDBEntityListComplex(clazz, condition, pageNum, pageSize,"");
	}
	
	@SuppressWarnings({ "unchecked" })
	public <T> Page<T> queryDBEntityListComplex(Class<T> clazz,	ComplexCondition condition, int pageNum, int pageSize,String... orderBys) {
		EntityInfo info=getInfoNoValue(clazz);
		String orderStr=this.orderParse(clazz, orderBys);
		if(StringUtils.isNotBlank(orderStr)){
			OrderByHelper.orderBy(orderStr);
		}
		
		//分页
		PageHelper.startPage(pageNum, pageSize);
		//将condition 中 col 替换为数据库对应字段
		for (ComplexConditionNode eachNode:condition.getNodes()) {
			if("col".equals(eachNode.getLink())){
				EntityInfoCol entityInfoCol= info.getColByEntityColName((String) eachNode.getObjects()[0]);
				eachNode.setObjects(entityInfoCol.getDbColName());
			}
		}
		
		Page<Map<String, Object>> list = genericDao.queryDBEntityComplex(info,condition);
		Page<T> result= (Page<T>) list.clone();
		result.clear();
		for(Map<String, Object> eachBeanMap:list){
			T eachObject = null;
			try {
				eachObject = (T)(clazz.newInstance());
			} catch (Exception e1) {
				log.error("instantiate new bean error.",e1.fillInStackTrace());
			} 
			
			try {
				BeanUtils.copyProperties(eachObject, eachBeanMap);
			} catch (Exception e) {
				log.error("copyProperties from Map to bean error.",e.fillInStackTrace());
			} 
			result.add(eachObject);
		}
		return result;
	}
	
	

	/*@Override
	public Page<Map<String, Object>> queryDBEntity(EntityInfo entityInfo) {
		return genericDao.queryDBEntity(entityInfo);
	}*/
}
