package com.kiiik.web.log.bean;

import com.kiiik.pub.mybatis.annotation.DBColumn;
import com.kiiik.pub.mybatis.annotation.DBEntity;
import com.kiiik.pub.mybatis.annotation.KeyColumn;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年10月17日 只有操作正常的请求才会记录日志
 */
@DBEntity("t_sys_log")
@ApiModel(description = "系统菜单信息", value = "系统菜单信息！")
public class SystemLog {
	@KeyColumn(useGeneratedKeys = true)
	@DBColumn(value = "id", insertIfNull = "default")
	@ApiModelProperty(value = "主键，请求时参数忽略")
	public Integer id;
	
	@ApiModelProperty(value="客户端IP地址")
	@DBColumn("client_ip")
	public String clientIp;// 客户端ip
	
	@ApiModelProperty(value="访问时间")
	@DBColumn(value="visit_time",needTimestamp=true)
	public String visitorTime;// 访问时间
	
	@ApiModelProperty(value="访问地址")
	@DBColumn("uri")
	public String uri;// 请求路径
	
	@ApiModelProperty(value="操作员信息")
	@DBColumn("operator")
	public String operator;// 操作员 //操作员职工号 必须唯一！！！！
	
	@ApiModelProperty(value="模块名称")
	@DBColumn("module")
	public String module;// 模块名称 module为uri的前缀 比如 uri /system/user 则 // module为/system
	
	@ApiModelProperty(value="请求参数")
	@DBColumn("param")
	public String param;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getVisitorTime() {
		return visitorTime;
	}

	public void setVisitorTime(String visitorTime) {
		this.visitorTime = visitorTime;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("操作员[" + operator + "]");
		sb.append("在[" + visitorTime + "]使用IP[" + clientIp + "]访问了");
		sb.append("模块[" + module + "]的");
		sb.append("请求地址[" + uri + "]。");
		return sb.toString();
	}

}
