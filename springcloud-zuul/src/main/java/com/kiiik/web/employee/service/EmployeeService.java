package com.kiiik.web.employee.service;

import com.kiiik.web.employee.entity.EmployeeEntity;

import java.util.List;

import com.kiiik.pub.bean.R;

/**
 * 服务接口定义
 * 作者: iechenyb
 * 邮件: zzuchenyb@sina.com
 * 日期: 2018-11-08 09:34:39
 */
public interface EmployeeService  {

/**
 * 
 *作者 :  iechenyb<br>
 *方法描述: 新增记录<br>
 *创建时间: 2018-11-08 09:34:39
 *@param 
 *@return
 */
 R<String> addEmployeeEntity(EmployeeEntity entity);
 
 /**
 * 
 *作者 :  iechenyb<br>
 *方法描述: 更新记录<br>
 *创建时间: 2018-11-08 09:34:39
 *@param 
 *@return
 */
 R<String> updEmployeeEntity(EmployeeEntity entity);
 
 /**
 * 
 *作者 :  iechenyb<br>
 *方法描述: 根据主键删除记录<br>
 *创建时间: 2018-11-08 09:34:39
 *@param 
 *@return
 */
 R<String> delEmployeeEntity(List<Integer> ids);
}

