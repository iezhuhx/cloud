package com.kiiik.pub.bean;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 用户信息
 * 
 * @ConfigurationProperties : 被修饰类中的所有属性会和配置文件中的指定值（该值通过prefix找到）进行绑定
 */
@Configuration
@PropertySource("classpath:application-app.properties")
@ConfigurationProperties(prefix = "system.config")
public class SystemConfig {

	private String account;
	private Integer age;
	private Boolean active;
	private Date createdDate;
	private Map<String, Object> map;
	private List<Object> list;
	private Position position;
	private List<String> swaggerStaticUris;
	private List<String> securityExcloudUris;
	
	
	String druidUrlPatterns,druidAllow,druidDeny,duridLoginUsername,duridLoginPassword,duridResetEnable;
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public List<String> getSwaggerStaticUris() {
		return swaggerStaticUris;
	}

	public void setSwaggerStaticUris(List<String> swaggerStaticUris) {
		this.swaggerStaticUris = swaggerStaticUris;
	}

	public List<String> getSecurityExcloudUris() {
		return securityExcloudUris;
	}

	public void setSecurityExcloudUris(List<String> securityExcloudUris) {
		this.securityExcloudUris = securityExcloudUris;
	}

	public String getDruidUrlPatterns() {
		return druidUrlPatterns;
	}

	public void setDruidUrlPatterns(String druidUrlPatterns) {
		this.druidUrlPatterns = druidUrlPatterns;
	}

	public String getDruidAllow() {
		return druidAllow;
	}

	public void setDruidAllow(String druidAllow) {
		this.druidAllow = druidAllow;
	}

	public String getDruidDeny() {
		return druidDeny;
	}

	public void setDruidDeny(String druidDeny) {
		this.druidDeny = druidDeny;
	}

	public String getDuridLoginUsername() {
		return duridLoginUsername;
	}

	public void setDuridLoginUsername(String duridLoginUsername) {
		this.duridLoginUsername = duridLoginUsername;
	}

	public String getDuridLoginPassword() {
		return duridLoginPassword;
	}

	public void setDuridLoginPassword(String duridLoginPassword) {
		this.duridLoginPassword = duridLoginPassword;
	}

	public String getDuridResetEnable() {
		return duridResetEnable;
	}

	public void setDuridResetEnable(String duridResetEnable) {
		this.duridResetEnable = duridResetEnable;
	}
	
}