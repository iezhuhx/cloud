package com.cyb.utils.collection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.cyb.utils.page.Pagination;
import com.cyb.utils.random.RandomUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月16日
 */
public class ListStudy {
	static int pageSize=5;
	static int total =17;
	Log log = LogFactory.getLog(ListStudy.class);
	public static void main(String[] args) {
		simulateBatch();
		System.out.println("=====================");
		simulateBatchPage();
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 采用模计算进行分页处理<br>
	 *创建时间: 2017年7月15日
	 */
	public static void simulateBatch(){
		List<String> data = new ArrayList<String>();
		for(int i=1;i<=total;i++){
			data.add(RandomUtils.getChineaseName());
			if(data.size()%pageSize==0){
				System.out.println("批量数据："+data);
				data.clear();
			}
		}
		if(!CollectionUtils.isEmpty(data)){
			System.out.println("剩余数据："+data);
		}
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 根据分页对象计算list数据<br>
	 *创建时间: 2017年7月15日
	 */
	public static void simulateBatchPage(){
		List<String> data = new ArrayList<String>();
		Pagination page = new Pagination(1, pageSize, total);
		for(int i=1;i<=page.getPageCount();i++){//遍历每一页
			Pagination curPage = new Pagination(i, pageSize, total);
			for(int j=curPage.getPageStart();j<=curPage.getPageEnd();j++){
				data.add(RandomUtils.getChineaseName());
			}
			System.out.println(i+"页"+data);
			data.clear();
		}
		
	}
}
