package com.ww.activiti.controller;

import java.io.Serializable;

/**
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年11月6日 下午3:25:53
 */
public class EnumObjectParam  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String e1name;
	String e2age;
	public EnumObjectParam(){
		
	}
	
	public EnumObjectParam(String e1name,String e2age){
		this.e1name = e1name;
		this.e2age = e2age;
	}
	public String getE1name() {
		return e1name;
	}
	public void setE1name(String e1name) {
		this.e1name = e1name;
	}
	public String getE2age() {
		return e2age;
	}
	public void setE2age(String e2age) {
		this.e2age = e2age;
	}
	
}
