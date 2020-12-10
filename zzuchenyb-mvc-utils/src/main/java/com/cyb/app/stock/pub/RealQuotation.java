package com.cyb.app.stock.pub;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年7月25日
 */
public class RealQuotation {
	private Long id;
	private String code;//sh600868
	private String name;
	private Double open;
	private Double high;
	private Double low;
	private Double close;
	private Double preclose;
	private Double price;
	private String day;
	private String time;
	private Double cje;//成交额
	private Long cjl;//成交量
	private String recordTime;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Double getOpen() {
		return open;
	}
	public void setOpen(Double open) {
		this.open = open;
	}
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
	public Double getHigh() {
		return high;
	}
	public void setHigh(Double high) {
		this.high = high;
	}
	public Double getLow() {
		return low;
	}
	public void setLow(Double low) {
		this.low = low;
	}
	public Double getClose() {
		return close;
	}
	public void setClose(Double close) {
		this.close = close;
	}
	public Double getPreclose() {
		return preclose;
	}
	public void setPreclose(Double preclose) {
		this.preclose = preclose;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Double getCje() {
		return cje;
	}
	public void setCje(Double cje) {
		this.cje = cje;
	}
	public Long getCjl() {
		return cjl;
	}
	public void setCjl(Long cjl) {
		this.cjl = cjl;
	}
	public String getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
	
	
}
