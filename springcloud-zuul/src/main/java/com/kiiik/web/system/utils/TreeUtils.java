package com.kiiik.web.system.utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.web.system.po.Menu;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年1月29日
 */
public class TreeUtils {
	Log log = LogFactory.getLog(TreeUtils.class);
    
  
    //判断字母还是数字
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 首次传递根节点和所有的节点数据
     *第二次传递 当前节点和所有的节点数据<br>
     *创建时间: 2017年7月15日
     *@param list
     *@param tree
     */
	
	public static Menu getRoot(){
		Menu root = new Menu();
		root.setId(KiiikContants.ROOTID);
		root.setMenuName("根节点");
		root.setParentId(KiiikContants.ROOTID);
		root.setChildren(new ArrayList<Menu>());
		return root;
	}
    public static void madeTree(List<Menu> list,Menu root)
    {
    	
        Iterator<Menu> ter = list.iterator();
        Menu cur = null;
        while(ter.hasNext())
        {
        	cur = ter.next();
            if(cur.getParentId().intValue()==root.getId().intValue())
            {
            	root.getChildren().add(cur);
                ter.remove();//提高效率，移除已处理节点
            }
        }
        madeTree(list,root.getChildren());
    }
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 说点啥<br>
     *创建时间: 2017年7月15日
     *@param list 所有节点
     *@param listChild //第n层节点
     */
	public static void madeTree(List<Menu> list,List<Menu> listChild){
		Iterator<Menu> ter = listChild.iterator();//第n层节点
		Menu cur = null;
		Menu allCur = null;
		Iterator<Menu> all = null;
        while(ter.hasNext())//遍历当前层次节点
        {
        	cur = ter.next();
        	all = list.iterator();//第n层节点
        	while(all.hasNext()){
        		allCur = all.next();
        		if(allCur.getParentId().intValue()==cur.getId().intValue()){
        			 cur.getChildren().add(allCur);
        			 all.remove();
        		 }
        	}
        	madeTree(list,cur.getChildren());
        }
	}
}

