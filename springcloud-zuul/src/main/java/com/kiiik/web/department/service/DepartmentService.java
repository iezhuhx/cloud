package com.kiiik.web.department.service;

import com.kiiik.web.department.entity.DepartmentEntity;

import java.util.List;
import java.util.Map;

import com.kiiik.pub.bean.R;

/**
 * 服务接口定义
 * 作者: iechenyb
 * 邮件: zzuchenyb@sina.com
 * 日期: 2018-11-08 09:34:39
 */
public interface DepartmentService  {

/**
 * 
 *作者 :  iechenyb<br>
 *方法描述: 新增记录<br>
 *创建时间: 2018-11-08 09:34:39
 *@param 
 *@return
 */
 R<String> addDepartmentEntity(DepartmentEntity entity);
 
 /**
 * 
 *作者 :  iechenyb<br>
 *方法描述: 更新记录<br>
 *创建时间: 2018-11-08 09:34:39
 *@param 
 *@return
 */
 R<String> updDepartmentEntity(DepartmentEntity entity);
 
 /**
 * 
 *作者 :  iechenyb<br>
 *方法描述: 根据主键删除记录<br>
 *创建时间: 2018-11-08 09:34:39
 *@param 
 *@return
 */
 R<String> delDepartmentEntity(List<Integer> ids);
 Map<String,String> getDepartmentNameById();
}

