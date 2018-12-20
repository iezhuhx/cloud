package com.kiiik.web.company.entity;

import java.io.Serializable;
import com.kiiik.pub.mybatis.annotation.DBEntity;
import com.kiiik.pub.mybatis.annotation.DBColumn;
import com.kiiik.pub.mybatis.annotation.KeyColumn;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 数据库实体
 * 作者: iechenyb
 * 邮件: zzuchenyb@sina.com
 * 日期: 2018-11-08 09:34:39
 */
@DBEntity("t_sys_company")
@ApiModel(value = "Company", description = "公司信息描述")
public class CompanyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")
	@ApiModelProperty(value="主键，请求时参数忽略")
	private Integer id;
	
			
	@DBColumn("subcompanyname")
	@ApiModelProperty(value="仅更新或者查询时，传参数！")
	private String subcompanyname;
	
			
	@DBColumn("companyid")
	@ApiModelProperty(value="仅更新或者查询时，传参数！")
	private Integer companyid;
	
			
	@DBColumn("supsubcomid")
	@ApiModelProperty(value="仅更新或者查询时，传参数！")
	private Integer supsubcomid;
	
			
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
	public void setSubcompanyname(String subcompanyname) {
		this.subcompanyname = subcompanyname;
	}
	//获取值方法
	public String getSubcompanyname() {
		return subcompanyname;
	}
	//设置值方法
	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}
	//获取值方法
	public Integer getCompanyid() {
		return companyid;
	}
	//设置值方法
	public void setSupsubcomid(Integer supsubcomid) {
		this.supsubcomid = supsubcomid;
	}
	//获取值方法
	public Integer getSupsubcomid() {
		return supsubcomid;
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
