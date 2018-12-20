package com.kiiik.utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.web.system.service.impl.MenuServiceImpl;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月22日
 */

public class RedisUtils {
	Log log = LogFactory.getLog(RedisUtils.class);

	 /**
     * 主数据系统标识
     */
    public static final String KEY_PREFIX = KiiikContants.KEY_PREFIX;
    /**
     * 分割字符，默认[:]，使用:可用于rdm分组查看
     */
    private static final String KEY_SPLIT_CHAR = ":";

    /**
     * redis的key键规则定义
     * @param module 模块名称
     * @param func 方法名称
     * @param args 参数..
     * @return key
     */
    public static String keyBuilder(String module, String func, String... args) {
        return keyBuilder(null, module, func, args);
    }
    
    private static String keyName(String value) throws Exception{
    	if(StringUtils.isEmpty(value)){
    		throw new Exception("缓存没有定义对应的key名称");
    	}
    	if(value.split("#").length > 0){
    		return value.split("#")[0];
    	}
		return value;
    }
    public static String keyBuilder(Class<?> clazz, String name) throws Exception {
        return new StringBuffer(KEY_PREFIX)
        		.append(KEY_SPLIT_CHAR)
        		.append(clazz.getSimpleName())
        		.append(KEY_SPLIT_CHAR)
        		.append(keyName(name))
        		.toString();
    }

    /**
     * redis的key键规则定义
     * @param module 模块名称
     * @param func 方法名称
     * @param objStr 对象.toString()
     * @return key
     */
    public static String keyBuilder(String module, String func, String objStr) {
        return keyBuilder(null, module, func, new String[]{objStr});
    }

    /**
     * redis的key键规则定义
     * @param prefix 项目前缀
     * @param module 模块名称
     * @param func 方法名称
     * @param objStr 对象.toString()
     * @return key
     */
    public static String keyBuilder(String prefix, String module, String func, String objStr) {
        return keyBuilder(prefix, module, func, new String[]{objStr});
    }

    /**
     * redis的key键规则定义
     * @param prefix 项目前缀
     * @param module 模块名称
     * @param func 方法名称
     * @param args 参数..
     * @return key
     */
    public static String keyBuilder(String prefix, String module, String func, String... args) {
        // 项目前缀
        if (prefix == null) {
            prefix = KEY_PREFIX;
        }
        StringBuilder key = new StringBuilder(prefix);
        // KEY_SPLIT_CHAR 为分割字符
        key.append(KEY_SPLIT_CHAR).append(module).append(KEY_SPLIT_CHAR).append(func);
        for (String arg : args) {
            key.append(KEY_SPLIT_CHAR).append(arg);
        }
        return key.toString();
    }

    /**
     * redis的key键规则定义
     * @param redisEnum 枚举对象
     * @param objStr 对象.toString()
     * @return key
     */
    public static String keyBuilder(RedisEnum redisEnum, String objStr) {
        return keyBuilder(redisEnum.getKeyPrefix(), redisEnum.getModule(), redisEnum.getFunc(), objStr);
    }
    
    public static void main(String[] args) throws Exception {
		System.out.println(keyBuilder("com.cyb.test", "method", String.valueOf(111)));
		System.out.println(keyBuilder("com.cyb.test", "method:value", String.valueOf(111)));
	    System.out.println(keyBuilder(MenuServiceImpl.class, "aaa"));
    
    
    }

}
