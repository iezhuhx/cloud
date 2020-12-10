package com.cyb.app.hutool;

import cn.hutool.core.annotation.Alias;

/**
 * @Author iechenyb<br>
 * @Desc 类描述<br>
 * @CreateTime 2020年12月10日 下午2:12:51
 */
public class CsvBean {
	@Alias("姓名")
	private String name;
	private String gender;
	private String focus;
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFocus() {
		return focus;
	}

	public void setFocus(String focus) {
		this.focus = focus;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	 public String toString(){
		 return this.name+","+this.gender+","+this.getFocus();
	 }
}
