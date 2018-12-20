package com.kiiik.web.system.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年1月30日
 */
@ApiModel("树节点信息")
public class TreeNode  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("记录id")
	private String id;
	@ApiModelProperty("节点的父节点id")
	private String pid;
	@ApiModelProperty("节点名称")
	private String name;
	@ApiModelProperty("预留字段")
	private String icon;
	@ApiModelProperty("预留字段，目前有值时，表示部门所在公司id")
	private String reservedField;//预留字段
	@ApiModelProperty("通用子节点信息")
	private List<TreeNode> children;
	@ApiModelProperty("部门信息")
	private List<TreeNode> deparments;
	@ApiModelProperty("节点类型 1 公司 2部门 3员工")
	private String type;//1 公司 2部门 3员工
	public TreeNode(){
		this.children = new ArrayList<TreeNode>();
	}
	public TreeNode(String recId,String name,String pid) {
		this.id = recId;
		this.pid = pid;
		this.name = name;
		this.children = new ArrayList<TreeNode>();
		this.deparments = new ArrayList<TreeNode>();
	}

	public TreeNode(String recId, String pid, String nodeName, String icon) {
		this.id = recId;
		this.pid = pid;
		this.name = nodeName;
		this.icon = icon;
		this.children = new ArrayList<TreeNode>();
		this.deparments = new ArrayList<TreeNode>();
	}

	public TreeNode(String id, String pid, String nodeName, String icon, List<TreeNode> children) {
		this.id = id;
		this.pid = pid;
		this.name = nodeName;
		this.icon = icon;
		this.children = children;
		this.deparments = new ArrayList<TreeNode>();
	}

	public String getId() {
		return id;
	}

	public void setId(String recId) {
		this.id = recId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String nodeName) {
		this.name = nodeName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	public String getReservedField() {
		return reservedField;
	}
	public void setReservedField(String reservedField) {
		this.reservedField = reservedField;
	}
	public List<TreeNode> getDeparments() {
		return deparments;
	}
	public void setDeparments(List<TreeNode> deparments) {
		this.deparments = deparments;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
