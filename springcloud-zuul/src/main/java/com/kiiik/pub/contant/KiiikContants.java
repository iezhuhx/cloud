package com.kiiik.pub.contant;

import java.util.List;

import com.kiiik.web.rsa.bean.RsaSer;

/**
 *作者 : iechenyb<br>
 *类描述: 通用的常量属性<br>
 *创建时间: 2018年11月1日
 */
public class KiiikContants {
	public final static int ROOTID=-1;
	public static final String ZUULNAME = "zuul-service";
	public static final String DEFAULT_OPERATOR_NAME = "defaultName";
	public static final String BLANK = "";
	public static final String PROD = "prod";
	public static final String TEST = "test";
	public static final String DEV = "dev";
	public static final String UTF8 = "UTF-8";
	public static final String VERIFY_CODE = "verCode";
	public static final String SPRING_CONTEXT_KEY = "SPRING_SECURITY_CONTEXT";
	
	public static List<String> logStaticFile;
	
	public static RsaSer RSASER;//rsa证书信息
	
	public static final String SALT="kiiik";
	public static final String COMPANY = "1";
	public static final String DEPARTMENT = "2";
	public static final String EMPLOYEE = "3";
	public static final Integer ZERO = 0;
	
	public static final String DEFAULT_PASSWORD = "96E79218965EB72C92A549DD5A330112";//96E79218965EB72C92A549DD5A330112
	public static String[] reqs = new String[]{
			 "company","api","department","employee","rsa","generic",
			 "menu","org","menu","rolemenu","user","role","rest",
			 "user","cache"};//免登陆请求列表
	public static String DEFAULT_PASSWORD_MODIFY_ROLE="ROLE_MODIFY_PASSWORD";
	public final static String CACHE_SPLIT = "#";
	
	/**
     * 主数据系统标识
     */
    public static final String KEY_PREFIX = "kiiik:zuul";
    /**
     * 分割字符，默认[:]，使用:可用于rdm分组查看
     */
    public static final String KEY_SPLIT_CHAR = ":";
    public static final String ERROR_KEY_DEFALUT="99999";
	public static final int USER_ISEFFECT_OK = 1;
	public static final String USERNAMEPASSWORDEXCEPTION = "用户名或密码不正确！";
	public static final String USER_ACCOUNT_BLOCK_UP = "账户已停用！";
}
