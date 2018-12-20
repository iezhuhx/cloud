package com.kiiik.pub.mybatis.bean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月17日
 */
public class ComplexConditionNode {
	Log log = LogFactory.getLog(ComplexConditionNode.class);
	private String link;
	private Object[] objects;
	private Object object;
 
	public ComplexConditionNode(String link) {
		super();
		this.link = link;
	}
 
	public ComplexConditionNode(String link, Object... objects) {
		super();
		this.link = link;
		this.objects = objects;
	}
 
	public String getLink() {
		return link;
	}
 
	public void setLink(String link) {
		this.link = link;
	}
 
	public Object[] getObjects() {
		return objects;
	}
 
	public void setObjects(Object... objects) {
		this.objects = objects;
	}
 
	public Object getObject() {
		object = objects[0];
		return object;
	}
}
