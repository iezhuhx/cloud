package com.cyb.utils.jsr.bean;

import java.util.List;
import java.util.Map;

import com.cyb.utils.jsr.annotation.Decimal;
import com.cyb.utils.jsr.annotation.Email;
import com.cyb.utils.jsr.annotation.Integer;
import com.cyb.utils.jsr.annotation.ListEmpty;
import com.cyb.utils.jsr.annotation.Long;
import com.cyb.utils.jsr.annotation.MapEmpty;
import com.cyb.utils.jsr.annotation.Pattern;
import com.cyb.utils.jsr.annotation.Phone;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
@ApiModel("统一验证bean")
public class ValidateBean {
	
	@ApiModelProperty("用户名,不可以为空")
	private String username;
	
	@Long(min=1,max=5,message="数字必须为1-5")
	@ApiModelProperty("Long数字必须为1-5")
	private Long blv;
	
	@Long(min=1,max=5,message="数字必须为1-5")
	@ApiModelProperty("long数字必须为1-5")
	private long lv;
	
	@Integer(min=1,max=5,message="数字必须为1-5")
	@ApiModelProperty("Integer数字必须为1-5")
	private Integer biv;
	
	@Integer(min=1,max=5,message="数字必须为1-5")
	@ApiModelProperty("int数字必须为1-5")
	private int iv;
	
	@ApiModelProperty("密码，非空")
	private String password;
	
	@Phone(message="手机号码格式不正确！")
	private String phone;
	
	@Email(message="邮箱格式不正确！")
	private String email;
	
	@Pattern(message="正则校验不正确！", regex = "[1-9]{2,5}")
	@ApiModelProperty("正则校验：[1-9]{2,5}")
	private String pattern;
	
	@Decimal(min=0.0,max=10.0,precision=2)
	@ApiModelProperty("Double 校验")
	private Double bdv;
	
	@Decimal(min=0.0,max=10.0,precision=2)
	@ApiModelProperty("double 校验")
	private double dv;
	
	/*@NotEmpty
	@ApiModelProperty("对象测试！")
	ChildBean user;
	public ChildBean getUser() {
		return user;
	}
	public void setUser(ChildBean user) {
		this.user = user;
	}*/
	@ListEmpty
	private  List<String> list;
	@MapEmpty
	private  Map<String,String> map;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Long getBlv() {
		return blv;
	}
	public void setBlv(Long blv) {
		this.blv = blv;
	}
	public long getLv() {
		return lv;
	}
	public void setLv(long lv) {
		this.lv = lv;
	}
	public Integer getBiv() {
		return biv;
	}
	public void setBiv(Integer biv) {
		this.biv = biv;
	}
	public int getIv() {
		return iv;
	}
	public void setIv(int iv) {
		this.iv = iv;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public Double getBdv() {
		return bdv;
	}
	public void setBdv(Double bdv) {
		this.bdv = bdv;
	}
	public double getDv() {
		return dv;
	}
	public void setDv(double dv) {
		this.dv = dv;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
