package com.kiiik.pub.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年10月18日
 */
@ApiModel("分页信息")
public class KiiikPage {
	@ApiModelProperty("第n页")
	private Integer curPage;
	
	@ApiModelProperty("每页记录数")
	private Integer pageSize;

	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 是否需要展示所有数据<br>
	 *创建时间: 2018年11月19日
	 *@return
	 */
	public boolean needAll(){
		if(this.curPage==null||this.pageSize==null){
			return true;
		}
		return curPage.intValue()==0||pageSize.intValue()==0;
	}
	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public Integer getPageSize() {
		if(pageSize==null){
			return 20;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
