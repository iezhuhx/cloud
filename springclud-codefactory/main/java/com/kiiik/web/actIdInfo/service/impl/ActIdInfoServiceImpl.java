package com.kiiik.web.actIdInfo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.kiiik.pub.bean.R;
import com.kiiik.pub.service.BaseService;
import com.kiiik.pub.mybatis.dao.GenericDao;
import com.kiiik.web.actIdInfo.dao.ActIdInfoDao;
import com.kiiik.web.actIdInfo.entity.ActIdInfoEntity;
import com.kiiik.web.actIdInfo.service.ActIdInfoService;

/**
 * 业务处理层<br>
   <br>
 * 作者: iechenyb<br>
 * 邮件: zzuchenyb@sina.com<br>
 * 日期: 2019-07-03 16:46:48<br>
 */
@Service
public class ActIdInfoServiceImpl extends BaseService  implements ActIdInfoService {
	
	//当前业务数据库服务接口
	@Autowired
	ActIdInfoDao  actIdInfoDao;
	
    /**
	 * 
	 *作者 :  iechenyb<br>
	 *方法描述: 新增记录<br>
	 *创建时间: 2019-07-03 16:46:48<br>
	 *@param 
	 *@return
	 */
	 @SuppressWarnings("unchecked")
	 public R<String> addActIdInfoEntity(ActIdInfoEntity entity){
	 	//去重逻辑 需要重写
	 	ActIdInfoEntity tmp = new ActIdInfoEntity();
	 	tmp.setId(entity.getId());
	 	if(!CollectionUtils.isEmpty(genericDao.queryDBEntityList(tmp))){
			 return new R<String>().fail("***["+entity.getId()+"]已经存在!");
		}
	 	int count = genericDao.insertDBEntity(entity);
		if(count==0){
			return new R<String>().success("新增记录失败!");
		}else{
			return new R<String>().success("新增记录成功!");
		}
	 }
	 
	/**
	 * 
	 *作者 :  iechenyb<br>
	 *方法描述: 更新记录<br>
	 *创建时间: 2019-07-03 16:46:48
	 *@param 
	 *@return
	 */
	 @SuppressWarnings("unchecked")
	 public R<String> updActIdInfoEntity(ActIdInfoEntity entity){
	 	//去重逻辑 需要重写
	 	ActIdInfoEntity tmp = null;
		//校验名称是否重复
		tmp = genericDao.queryDBEntitySingleComplex(ActIdInfoEntity.class, 
					new ComplexCondition().col("id").notIn(entity.getId())
					.and()
					.col("anyColName").eq(entity.getId()));
		if(tmp!=null){
			return new R<String>().fail("***["+entity.getId()+"]已经存在!");
		}
	 	int count = genericDao.updateDBEntityByKey(entity);
		if(count==0){
			return new R<String>().success("更新记录失败!");
		}else{
			return new R<String>().success("更新记录成功!");
		}
	 }
	 
	/**
	 * 
	 *作者 :  iechenyb<br>
	 *方法描述: 删除记录<br>
	 *创建时间: 2019-07-03 16:46:48
	 *@param 
	 *@return
	 */
	 @SuppressWarnings("unchecked")
	 public R<String> delActIdInfoEntity(Integer id){
	    ActIdInfoEntity entity = new ActIdInfoEntity();
		entity.setId(id);
		int count = genericDao.deleteDBEntityByKey(entity);
		if(count==0){
			return new R<String>().success("删除记录失败!");
		}else{
			return new R<String>().success("删除记录成功!");
		}
	 }
 
}
