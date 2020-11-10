package com.xian.cloud.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <Description> 路由实体类
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/10/30 15:00
 */
@Data
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



}
