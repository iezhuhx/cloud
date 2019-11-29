package com.kiiik.web.actIdInfo.service;

import com.kiiik.web.actIdInfo.entity.ActIdInfoEntity;
import com.kiiik.pub.bean.R;

/**
 * 服务接口定义<br>
 * 作者: iechenyb<br>
 * 邮件: zzuchenyb@sina.com<br>
 * 日期: 2019-07-03 16:46:48<br>
 */
public interface ActIdInfoService  {

/**
 * 
 *作者 :  iechenyb<br>
 *方法描述: 新增记录<br>
 *创建时间: 2019-07-03 16:46:48<br>
 *@param 
 *@return
 */
 R<String> addActIdInfoEntity(ActIdInfoEntity entity);
 
 /**
 * 
 *作者 :  iechenyb<br>
 *方法描述: 更新记录<br>
 *创建时间: 2019-07-03 16:46:48<br>
 *@param 
 *@return
 */
 R<String> updActIdInfoEntity(ActIdInfoEntity entity);
 
 /**
 * 
 *作者 :  iechenyb<br>
 *方法描述: 根据主键删除记录<br>
 *创建时间: 2019-07-03 16:46:48<br>
 *@param 
 *@return
 */
 R<String> delActIdInfoEntity(Integer id);
}

