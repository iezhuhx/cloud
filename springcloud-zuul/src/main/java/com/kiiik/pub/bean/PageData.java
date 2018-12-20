package com.kiiik.pub.bean;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;
import com.kiiik.pub.contant.KiiikContants;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月19日
 */
@ApiModel("分页数据对象")
public class PageData<T> {
	
	/**
	 * 分页数据信息
	 */
	@ApiModelProperty("当前分页数据")
	public List<T> list;
	
	@ApiModelProperty("记录总数")
	public long total;//总记录数
	
	@ApiModelProperty("第n页")
	public int curPage;//当前页
	
	@ApiModelProperty("每页展示记录数")
	public int pageSize;//页面大小
	
	@ApiModelProperty("总页数")
	public int pages;
	
	public PageData(List<T> data){
		
		if(CollectionUtils.isEmpty(data)){
			list = new ArrayList<T>();
			total = KiiikContants.ZERO;
			curPage = 0;
			pageSize = 0;
		}else{
			list = data;
			total = data.size();
			curPage=1;//第一页
			pages=1;//全部数据   共一页
		}
	}
	
	public PageData(Page<T> page,KiiikPage p){
		list = page.getResult();
		total = page.getTotal();
		if(p.getCurPage().intValue()<1){
			curPage = 1;
		}else if(p.getCurPage().intValue()>page.getPages()){
			curPage = page.getPages();
		}else{
			curPage = p.getCurPage();
		}
		pageSize = p.getPageSize();//直接赋值，不用判断
		pages = page.getPages();
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
}
