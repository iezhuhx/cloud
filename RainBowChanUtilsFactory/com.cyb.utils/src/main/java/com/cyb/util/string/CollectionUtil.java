package com.cyb.util.string;

import java.util.List;
import java.util.Map;

/**
 * 数组工具
 */
public class CollectionUtil {
    /**
     * 判断value是否为空值
     * @param value
     * @return
     */
   public static  boolean isEmpty(Map<?,?> value){
       return isEmptyMap(value);
   }
    /**
     * 判断value是否为空值
     * @param value
     * @return
     */
    public static  boolean isEmpty(List<?> value){
        return isEmptyList(value);
    }
    /**
     * 判断value是否为空值
     * @param value
     * @return
     */
    public static  boolean isEmptyMap(Map<?,?> value){
        if(value == null) return true;
        if(value.size()==0) return true;
        return false;
    }
    /**
     * 判断value是否为空值
     * @param value
     * @return
     */
    public static  boolean isEmptyList(List<?> value){
        if(value == null) return true;
        if(value.size()==0) return true;
        return false;
    }
}
