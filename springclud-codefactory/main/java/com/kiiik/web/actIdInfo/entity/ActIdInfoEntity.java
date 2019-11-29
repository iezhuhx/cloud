package com.kiiik.web.actIdInfo.entity;

import java.io.Serializable;
import com.kiiik.pub.mybatis.annotation.DBEntity;
import com.kiiik.pub.mybatis.annotation.DBColumn;
import com.kiiik.pub.mybatis.annotation.KeyColumn;
import io.swagger.annotations.ApiModelProperty;

/**
 * 数据库实体Bean<br>
 * <br>
 * 作者: iechenyb<br>
 * 邮件: zzuchenyb@sina.com<br>
 * 日期: 2019-07-03 16:46:48<br>
 */
@DBEntity("act_id_info")
public class ActIdInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")
	@ApiModelProperty(value="主键")
	private Integer id;
	
			
	@DBColumn("REV_")
	@ApiModelProperty(value=)
	private Integer rev;
	
			
	@DBColumn("USER_ID_")
	@ApiModelProperty(value=)
	private String userId;
	
			
	@DBColumn("TYPE_")
	@ApiModelProperty(value=)
	private String type;
	
			
	@DBColumn("KEY_")
	@ApiModelProperty(value=)
	private String key;
	
			
	@DBColumn("VALUE_")
	@ApiModelProperty(value=)
	private String value;
	
			
	@DBColumn("PASSWORD_")
	@ApiModelProperty(value=)
	private unknowType password;
	
			
	@DBColumn("PARENT_ID_")
	@ApiModelProperty(value=)
	private String parentId;
	
	
	//设置值方法
	public void setId(String id) {
		this.id = id;
	}
	//获取值方法
	public String getId() {
		return id;
	}
	//设置值方法
	public void setRev(Integer rev) {
		this.rev = rev;
	}
	//获取值方法
	public Integer getRev() {
		return rev;
	}
	//设置值方法
	public void setUserId(String userId) {
		this.userId = userId;
	}
	//获取值方法
	public String getUserId() {
		return userId;
	}
	//设置值方法
	public void setType(String type) {
		this.type = type;
	}
	//获取值方法
	public String getType() {
		return type;
	}
	//设置值方法
	public void setKey(String key) {
		this.key = key;
	}
	//获取值方法
	public String getKey() {
		return key;
	}
	//设置值方法
	public void setValue(String value) {
		this.value = value;
	}
	//获取值方法
	public String getValue() {
		return value;
	}
	//设置值方法
	public void setPassword(unknowType password) {
		this.password = password;
	}
	//获取值方法
	public unknowType getPassword() {
		return password;
	}
	//设置值方法
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	//获取值方法
	public String getParentId() {
		return parentId;
	}
}
