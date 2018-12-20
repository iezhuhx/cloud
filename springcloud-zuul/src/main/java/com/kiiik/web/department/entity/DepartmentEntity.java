package com.kiiik.web.department.entity;

import java.io.Serializable;
import com.kiiik.pub.mybatis.annotation.DBEntity;
import com.kiiik.pub.mybatis.annotation.DBColumn;
import com.kiiik.pub.mybatis.annotation.KeyColumn;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 数据库实体
 * 作者: iechenyb
 * 邮件: zzuchenyb@sina.com
 * 日期: 2018-11-08 09:34:39
 */
@DBEntity("t_sys_department")
public class DepartmentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")
	@ApiModelProperty(value="主键，请求时参数忽略")
	private Integer id;
	
			
	@DBColumn("departmentname")
	@ApiModelProperty(value="仅更新或者查询时，传参数！")
	private String departmentname;
	
			
	@DBColumn("subcompanyid1")
	@ApiModelProperty(value="仅更新或者查询时，传参数！")
	private Integer subcompanyid1;
	
			
	@DBColumn("supdepid")
	@ApiModelProperty(value="仅更新或者查询时，传参数！")
	private Integer supdepid;
	
			
	@DBColumn("showorder")
	@ApiModelProperty(value="仅更新或者查询时，传参数！")
	private Integer showorder;
	
	
	//设置值方法
	public void setId(Integer id) {
		this.id = id;
	}
	//获取值方法
	public Integer getId() {
		return id;
	}
	//设置值方法
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	//获取值方法
	public String getDepartmentname() {
		return departmentname;
	}
	//设置值方法
	public void setSubcompanyid1(Integer subcompanyid1) {
		this.subcompanyid1 = subcompanyid1;
	}
	//获取值方法
	public Integer getSubcompanyid1() {
		return subcompanyid1;
	}
	//设置值方法
	public void setSupdepid(Integer supdepid) {
		this.supdepid = supdepid;
	}
	//获取值方法
	public Integer getSupdepid() {
		return supdepid;
	}
	//设置值方法
	public void setShoworder(Integer showorder) {
		this.showorder = showorder;
	}
	//获取值方法
	public Integer getShoworder() {
		return showorder;
	}
}
