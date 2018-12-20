package com.kiiik.web.system.utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月7日
 */
public class OrganizationTreeUtils {
	Log log = LogFactory.getLog(OrganizationTreeUtils.class);
	public static void main(String[] args) {
		List<TreeNode> data = new ArrayList<>();
		TreeNode tn1 = new TreeNode("5","东航金控有限责任公司","0");
		TreeNode tn2 = new TreeNode("7","东航集团财务有限责任公司","0");
		TreeNode tn3 = new TreeNode("6","东航期货有限责任公司","0");
		TreeNode tn4 = new TreeNode("9","东航国际控股(香港)有限公司","0");
		TreeNode tn5 = new TreeNode("8","东航国际金融(香港)有限公司","0");
		TreeNode tn6 = new TreeNode("10","东航物网风险管理有限公司","6");
		TreeNode root = new TreeNode("0","树根","0");
		data.add(tn1);
		data.add(tn2);
		data.add(tn3);
		data.add(tn4);
		data.add(tn5);
		data.add(tn6);
		
		madeTree(data,root);
		System.out.println(JSON.toJSONString(root));
	}
	public static void madeTree(List<TreeNode> data, TreeNode root) {
		Iterator<TreeNode> iter = data.iterator();
		while(iter.hasNext()){
			TreeNode node = iter.next();
			if(node.getPid().equals(root.getId())){
				root.getChildren().add(node);
				iter.remove();
			}
		}
		
		madeTree(root.getChildren(),data);
	}
	public static void madeTree(List<TreeNode> children, List<TreeNode> data) {
		if(CollectionUtils.isEmpty(data)){
			return ;
		}
		Iterator<TreeNode> iterChildren = children.iterator();
		while(iterChildren.hasNext()){//数据
			TreeNode node = iterChildren.next();//第n个孩子
			Iterator<TreeNode> iterData = data.iterator();
			
			while(iterData.hasNext()){//数据
				TreeNode child = iterData.next();
				if(child.getPid().equals(node.getId())){
					node.getChildren().add(child);
					iterData.remove();
				}
			}
			madeTree(node.getChildren(),data);
		}
	}
	
}
