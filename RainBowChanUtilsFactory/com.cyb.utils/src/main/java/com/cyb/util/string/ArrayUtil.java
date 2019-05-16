package com.cyb.util.string;

/**
 * 数组工具
 */
public class ArrayUtil {
    /**
     * 判断value是否为空值
     * @param value
     * @return
     */
   public static  boolean isEmpty(Object[] value){
       return isEmptyObject(value);
   }
    /**
     * 判断value是否为空值
     * @param value
     * @return
     */
    public static  boolean isEmpty(String[] value){
        return isEmptyObject(value);
    }
    /**
     * 判断value是否为空值
     * @param value
     * @return
     */
    public static  boolean isEmptyObject(Object[] value){
        if(value == null) return true;
        if(value.length==0) return true;
        return false;
    }
}
