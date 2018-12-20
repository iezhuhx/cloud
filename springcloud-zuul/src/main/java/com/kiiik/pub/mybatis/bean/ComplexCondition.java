package com.kiiik.pub.mybatis.bean;
import java.util.LinkedList;
import java.util.List;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月17日
 */
public class ComplexCondition {
	List<ComplexConditionNode> nodes=new LinkedList<ComplexConditionNode>();
	
	
	public List<ComplexConditionNode> getNodes() {
		return nodes;
	}
 
	public ComplexCondition col(String col){
		nodes.add(new ComplexConditionNode("col", col));
		return this;
	}
	
	public ComplexCondition and(){
		nodes.add(new ComplexConditionNode("and"));
		return this;
	}
	public ComplexCondition or(){
		nodes.add(new ComplexConditionNode("or"));
		return this;
	}
	
	public ComplexCondition and(ComplexCondition innerCondition){
		nodes.add(new ComplexConditionNode("and"));
		nodes.add(new ComplexConditionNode("("));
		nodes.addAll(innerCondition.getNodes());
		nodes.add(new ComplexConditionNode(")"));
		return this;
	}
	
	public ComplexCondition or(ComplexCondition innerCondition){
		nodes.add(new ComplexConditionNode("or"));
		nodes.add(new ComplexConditionNode("("));
		nodes.addAll(innerCondition.getNodes());
		nodes.add(new ComplexConditionNode(")"));
		return this;
	}
	
	public ComplexCondition eq(Object value){
		nodes.add(new ComplexConditionNode("eq", value));
		return this;
	}
	
	public ComplexCondition gt(Object value){
		nodes.add(new ComplexConditionNode("gt", value));
		return this;
	}
	
	public ComplexCondition lt(Object value){
		nodes.add(new ComplexConditionNode("lt", value));
		return this;
	}
	
	public ComplexCondition gte(Object value){
		nodes.add(new ComplexConditionNode("gte", value));
		return this;
	}
	
	public ComplexCondition lte(Object value){
		nodes.add(new ComplexConditionNode("lte", value));
		return this;
	}
	
	public ComplexCondition beginWith(Object value){
		nodes.add(new ComplexConditionNode("bw", value));
		return this;
	}
	
	public ComplexCondition beginNotWith(Object value){
		nodes.add(new ComplexConditionNode("bn", value));
		return this;
	}
	
	public ComplexCondition endWith(Object value){
		nodes.add(new ComplexConditionNode("ew", value));
		return this;
	}
	
	public ComplexCondition endNotWith(Object value){
		nodes.add(new ComplexConditionNode("en", value));
		return this;
	}
	
	public ComplexCondition contains(Object value){
		nodes.add(new ComplexConditionNode("cn", value));
		return this;
	}
	
	public ComplexCondition notContains(Object value){
		nodes.add(new ComplexConditionNode("nc", value));
		return this;
	}	
	
	public ComplexCondition isNull(){
		nodes.add(new ComplexConditionNode("nu"));
		return this;
	}
	
	
	public ComplexCondition notNull(){
		nodes.add(new ComplexConditionNode("nn"));
		return this;
	}
	
	public ComplexCondition in(Object... objects){
		nodes.add(new ComplexConditionNode("in",objects));
		return this;
	}
	
	public ComplexCondition inList(List<?> objects){
		nodes.add(new ComplexConditionNode("in",objects.toArray()));
		return this;
	}
	
	public ComplexCondition notIn(Object... objects){
		nodes.add(new ComplexConditionNode("ni",objects));
		return this;
	}
 
	@Override
	public String toString() {
		String result="";
		for (ComplexConditionNode eachNode:nodes) {
			result+= eachNode.getLink();
			result+=" ";
			if(eachNode.getObjects()==null){
				continue;
			}
			for(Object eachObject:eachNode.getObjects()){
				result+=eachObject.toString()+" ";
			}
		}
		return result;
	}
}
