package com.kiiik.pub.mybatis.mapper;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.mybatis.bean.EntityInfo;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月11日
 */
public interface GenericMybatisMapper {
	
	public int insertDBEntity(EntityInfo entityInfo);
	
	public int insertDBEntityBatch(List<EntityInfo> entityInfo);
	
	public int updateDBEntityByKey(EntityInfo entityInfo);
	
	public int deleteDBEntityByKey(EntityInfo entityInfo);
	
	public int deleteDBEntity(EntityInfo entityInfo);

	public Page<Map<String, Object>> queryDBEntity(EntityInfo info);
	
	public Page<Map<String, Object>> queryDBEntityComplex(@Param("entityInfo") EntityInfo entityInfo,@Param("complexCond")ComplexCondition complexCond);

}
