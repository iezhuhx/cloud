package com.kiiik.web.system.po;
import java.io.Serializable;

import com.kiiik.pub.mybatis.annotation.DBColumn;
import com.kiiik.pub.mybatis.annotation.DBEntity;
import com.kiiik.pub.mybatis.annotation.KeyColumn;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 *作者 : iechenyb<br>
 *类描述: bean的字段基础类型都用包装类定义<br>
 *创建时间: 2018年10月18日
 */
@DBEntity("t_sys_user")
@ApiModel(value="系统用户信息")
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @作者:iechenyb</br>
	 * @功能描述：</br>
	 * @创建时间：2016年11月1日下午4:56:32</br>
	 */
	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")	
	@ApiModelProperty("主键")
	private Integer id;  
	
	@ApiModelProperty(value="用户名称",hidden=true)
	@DBColumn(value="username")
    private String userName;  
	
	@ApiModelProperty(value="用户编号",hidden=true)
	@DBColumn(value="empno")
    private String empNo;  
	
	@ApiModelProperty(value="密码",hidden=true)
	@DBColumn("password")
    private String password; 
	
    @ApiModelProperty(value="上次登录时间，忽略",hidden=true)
    @DBColumn(value="lastLoginTime",needTimestamp=true)
    public Long lastLoginTime;
    
    @ApiModelProperty(value="登录时间，忽略",hidden=true)
    @DBColumn(value="loginTime",needTimestamp=true)
    public Long loginTime;
    
    @DBColumn("lastLoginIp")
    @ApiModelProperty(value="上次登录ip，忽略",hidden=true)
    public String lastLoginIp;
    
    @DBColumn("loginIp")
    @ApiModelProperty(value="最新登录ip，忽略",hidden=true)
    public String loginIp;
    
    @DBColumn("loginSum")
    @ApiModelProperty(value="登录次数，忽略",hidden=true)
    public Integer loginSum;
    
    @DBColumn("isEffect")
    @ApiModelProperty(value="是否可用 1可用 其他值不可用")
    public Integer isEffect;
    
    @DBColumn("operateID")
    @ApiModelProperty(value="记录操作人员id,忽略",hidden=true)
    Integer operateID;
    
    public User() {  
        super();  
    }  
   
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public String getPassword() {  
        return password;  
    }  
    public void setPassword(String password) {  
        this.password = password;  
    }  
   
	public Long getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public Integer getLoginSum() {
		return loginSum;
	}
	public void setLoginSum(Integer loginSum) {
		this.loginSum = loginSum;
	}
	public Integer getIsEffect() {
		return isEffect;
	}
	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}
	
	public String getLoginIp() {
		return loginIp;
	}


	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getEmpNo() {
		return empNo;
	}


	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public Integer getOperateID() {
		return operateID;
	}

	public void setOperateID(Integer operateID) {
		this.operateID = operateID;
	}

	public Long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}
	
}
