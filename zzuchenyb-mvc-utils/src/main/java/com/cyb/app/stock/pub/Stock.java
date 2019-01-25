package com.cyb.app.stock.pub;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Stock implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public String id;
	public String name;
	public String code;
	public String exchange;
	public String classify;//分类  农业 金融等
	public String province;//省份
	public String industry;//A股 B股等
	public String timeOfMarket;//上市时间
	public String code_;
	//A股总股本,A股流通股本
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getTimeOfMarket() {
		return timeOfMarket;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public void setTimeOfMarket(String timeOfMarket) {
		this.timeOfMarket = timeOfMarket;
	}

	public Stock mapRow(ResultSet rs, int arg1) throws SQLException {
		    Stock userInfo = new Stock();
	        userInfo.setId(rs.getString("id"));
	        userInfo.setName(rs.getString("name"));
	        userInfo.setCode(rs.getString("code"));
	        userInfo.setExchange(rs.getString("exchange3"));
	        return userInfo;
	}
	
	public String getCode_() {
		return code_;
	}

	public void setCode_(String code_) {
		this.code_ = code_;
	}

	public String toString(){
		return this.code+","+this.name+", "+this.industry+","+this.exchange;
	}
}
