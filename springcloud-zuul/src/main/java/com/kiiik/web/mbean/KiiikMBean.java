package com.kiiik.web.mbean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.kiiik.web.rsa.service.RsaService;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月16日
 */
@Component
@ManagedResource (objectName= "com.kiiik.bean:name=RSAOperator" , description= "必要时刷新公秘钥信息！" )  
public   class  KiiikMBean{  
	@Autowired
	RsaService rsaService;
	
    private String name="kiiik";  
    private int age;  
      
    
    @ManagedOperation(description="刷新公秘钥")  
    public String updateRsaInfor() {  
        try {
			rsaService.genRSASer();
		} catch (Exception e) {
			e.printStackTrace();
			return "刷新失败";
		}//刷新
        return "刷新成功！";
    }  
    
    
    
    @ManagedAttribute(description="The Name Attribute")  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    @ManagedAttribute()  
    public String getName() {  
        return name;  
    }    
    public int getAge() {  
        return age;  
    }  
    public void setAge(int age) {  
        this.age = age;  
    }  
      
    @ManagedOperation(description="Add two numbers")  
    @ManagedOperationParameters({  
    @ManagedOperationParameter(name = "x", description = "The first number"),  
    @ManagedOperationParameter(name = "y", description = "The second number")})  
    public int add_1(int x, int y) {  
        return x + y;  
    }  
  
    @ManagedOperation  
    public int add_2(int x, int y){  
        return x + y;  
    }  
    
    public void dontExposeMe() {  
        throw new RuntimeException();  
    } 
}
