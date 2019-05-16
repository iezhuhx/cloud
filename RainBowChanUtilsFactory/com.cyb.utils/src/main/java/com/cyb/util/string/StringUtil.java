package com.cyb.util.string;

/**
 * 字符串工具
 */
public class StringUtil {
    /**
     * 判断value是否为空值
     * @param value
     * @return
     */
   public static  boolean isEmpty(Object value){
       return isEmptyObject(value);
   }
    /**
     * 判断value是否为空值
     * @param value
     * @return
     */
    public static  boolean isEmpty(String value){
        return isEmptyObject(value);
    }
    /**
     * 判断value是否为空值
     * @param value
     * @return
     */
    public static  boolean isEmptyObject(Object value){
        if(value == null) return true;
        if("".equals(value)) return true;
        return false;
    }
}
