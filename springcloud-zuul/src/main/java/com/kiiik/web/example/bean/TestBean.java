package com.kiiik.web.example.bean;

import java.io.Serializable;

import com.kiiik.pub.mybatis.annotation.DBColumn;
import com.kiiik.pub.mybatis.annotation.DBEntity;
import com.kiiik.pub.mybatis.annotation.KeyColumn;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月17日
 */
@DBEntity("test_table")
public class TestBean implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TestBean(){}
    public TestBean(String id,String account,String name,String password){
    	this.id = id;//如果用redis缓存， 则建值必须是字符串
    	this.account = account;
    	this.name = name;
    	this.password = password;
    }
    
	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")	
	private String id;
	
	@DBColumn("account") 
	private String account;
	
	@DBColumn("name") 
	private String name;
	
	@DBColumn(value="password",insertIfNull="'456789'")	
	private String password;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

}
