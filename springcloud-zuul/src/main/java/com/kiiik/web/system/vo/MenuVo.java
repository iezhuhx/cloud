package com.kiiik.web.system.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kiiik.pub.mybatis.annotation.DBColumn;
import com.kiiik.pub.mybatis.annotation.DBEntity;
import com.kiiik.pub.mybatis.annotation.KeyColumn;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@DBEntity("t_sys_menu")
@ApiModel(description="系统菜单信息",value="系统菜单信息！")
public class MenuVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @作者:iechenyb</br>
	 * @功能描述：</br>
	 * @创建时间：2016年11月3日下午1:24:22</br>
	 */
	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")	
	@ApiModelProperty(value="主键，请求时参数忽略")
	private Integer id;
	@ApiModelProperty(value="菜单名称")
	@DBColumn("menuName")
	private String menuName;//类型名称
	@ApiModelProperty(value="请求路径")
	@DBColumn("url")
	private String url;//类型编号
	@ApiModelProperty(value="父菜单主键")
	@DBColumn("parentId")
	private Integer parentId;//父类型编号
	@ApiModelProperty(value="是否叶子节点 1是 0不是")
	@DBColumn("isLeaf")
	private Integer isLeaf;//是否叶子节点
	@ApiModelProperty(value="菜单描述")
	@DBColumn("menuDesc")
	private String menuDesc;//类型描述
	@DBColumn("createTime")
	@ApiModelProperty(value="创建时间 yyyymmddhhmmss，请求时参数忽略")
	private Long createTime;//创建时间
	@DBColumn("cretatePerson")
	@ApiModelProperty(value="创建人员信息，请求时参数忽略")
	private String cretatePerson;//创建人员
	@DBColumn("modifyTime")
	@ApiModelProperty(value="修改时间 ，请求时参数忽略")
	private Long modifyTime;//修改时间
	@DBColumn("modifyPerson")
	@ApiModelProperty(value="修改人员 ，请求时参数忽略")
	private String modifyPerson;//修改人员
	@DBColumn("ordor")
	@ApiModelProperty(value="菜单排序权重值(兄弟菜单排序)")
	private Integer ordor ;//菜单排序
	@ApiModelProperty(value="子节点，请求时参数忽略")
	private List<MenuVo> children = new ArrayList<MenuVo>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getMenuDesc() {
		return menuDesc;
	}
	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getCretatePerson() {
		return cretatePerson;
	}
	public void setCretatePerson(String cretatePerson) {
		this.cretatePerson = cretatePerson;
	}
	public Long getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifyPerson() {
		return modifyPerson;
	}
	public void setModifyPerson(String modifyPerson) {
		this.modifyPerson = modifyPerson;
	}
	public Integer getOrdor() {
		return ordor;
	}
	public void setOrdor(Integer ordor) {
		this.ordor = ordor;
	}
	public List<MenuVo> getChildren() {
		return children;
	}
	public void setChildren(List<MenuVo> children) {
		this.children = children;
	}
	
}
