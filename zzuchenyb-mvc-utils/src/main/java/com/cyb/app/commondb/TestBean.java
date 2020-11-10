package com.cyb.app.commondb;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cyb.utils.file.FileUtils;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月25日
 */
public class TestBean {
	public TestBean() {}

	String rq;

	public String getRq() {
		return rq;
	}

	public void setRq(String rq) {
		this.rq = rq;
	}
	public static void main(String[] args) {
		Map<String,String> data = new LinkedHashMap<String,String>();
		List<String> d = FileUtils.readFileToList("D:\\1工作文件\\002需求整理\\005报表迁移\\dd.txt");
		for(String s:d){
			System.out.println(s);
			data.put(s.split("#")[1], s.split("#")[0]);
		}
		System.out.println(JSON.toJSONString(data));
	}
}