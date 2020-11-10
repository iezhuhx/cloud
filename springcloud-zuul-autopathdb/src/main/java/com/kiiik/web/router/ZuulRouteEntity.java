package com.kiiik.web.router;
import java.io.Serializable;
import java.util.Date;
/**
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年11月10日 上午11:55:00
 */
	public class ZuulRouteEntity implements Serializable {
	    private static final long serialVersionUID = 1L;

	    /**
	     * router Id
	     */
	    private Integer id;
	    /**
	     * 路由路径
	     */
	    private String path;
	    /**
	     * 服务名称
	     */
	    private String serviceId;
	    /**
	     * url代理
	     */
	    private String url;
	    /**
	     * 转发去掉前缀
	     */
	    private String stripPrefix;
	    /**
	     * 是否重试
	     */
	    private String retryable;
	    /**
	     * 是否启用
	     */
	    private String enabled;
	    /**
	     * 敏感请求头
	     */
	    private String sensitiveheadersList;
	    /**
	     * 创建时间
	     */
	    private Date createTime;
	    /**
	     * 更新时间
	     */
	    private Date updateTime;
	    /**
	     * 删除标识（0-正常,1-删除）
	     */
	    private String delFlag;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getServiceId() {
			return serviceId;
		}
		public void setServiceId(String serviceId) {
			this.serviceId = serviceId;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getStripPrefix() {
			return stripPrefix;
		}
		public void setStripPrefix(String stripPrefix) {
			this.stripPrefix = stripPrefix;
		}
		public String getRetryable() {
			return retryable;
		}
		public void setRetryable(String retryable) {
			this.retryable = retryable;
		}
		public String getEnabled() {
			return enabled;
		}
		public void setEnabled(String enabled) {
			this.enabled = enabled;
		}
		public String getSensitiveheadersList() {
			return sensitiveheadersList;
		}
		public void setSensitiveheadersList(String sensitiveheadersList) {
			this.sensitiveheadersList = sensitiveheadersList;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public Date getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
		public String getDelFlag() {
			return delFlag;
		}
		public void setDelFlag(String delFlag) {
			this.delFlag = delFlag;
		}
	
}
