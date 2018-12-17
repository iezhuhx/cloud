package com.kiiik.pub.servlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年6月1日下午2:46:46
 */
//@ServletComponentScan
@WebServlet(urlPatterns = "/druid/*", 
    initParams={
            @WebInitParam(name="allow",value="192.168.27.7,127.0.0.1"),// IP白名单 (没有配置或者为空，则允许所有访问)
            @WebInitParam(name="deny",value="192.168.16.111"),// IP黑名单 (存在共同时，deny优先于allow)
            @WebInitParam(name="loginUsername",value="cyb"),// 用户名
            @WebInitParam(name="loginPassword",value="cyb"),// 密码
            @WebInitParam(name="resetEnable",value="false")// 禁用HTML页面上的“Reset All”功能
    })
public class DruidStatViewServlet extends StatViewServlet{
	private static final long serialVersionUID = 1L;
}
