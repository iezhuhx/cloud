package com.cyb.utils.sql.bean;

/**
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年12月10日 上午10:57:25
 */
public class SQLBean {
	
	public Long id=1l;
	public long idl = 1;
	public String name="cheyb";
	public Double score=23.02;
	public double scored=23.04;
	public int age=10;
	public Integer agei = 20;
	public short shot=1;
	//public char chr = '中'; 暂不支持
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public long getIdl() {
		return idl;
	}
	public void setIdl(long idl) {
		this.idl = idl;
	}
	public double getScored() {
		return scored;
	}
	public void setScored(double scored) {
		this.scored = scored;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Integer getAgei() {
		return agei;
	}
	public void setAgei(Integer agei) {
		this.agei = agei;
	}
	public short getShot() {
		return shot;
	}
	public void setShot(short shot) {
		this.shot = shot;
	}
/*	public char getChr() {
		return chr;
	}
	public void setChr(char chr) {
		this.chr = chr;
	}
	*/
	
}
