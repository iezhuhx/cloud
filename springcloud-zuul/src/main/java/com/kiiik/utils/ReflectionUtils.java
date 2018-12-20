package com.kiiik.utils;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月9日
 */
class PrivateTest {
    private String name = "hello";
    
    public String getName() {
        return name;
    }
}
public class ReflectionUtils {
	Log log = LogFactory.getLog(ReflectionUtils.class);
	
	public static void modifyListFieldValue(List<?> data,String fieldName,String value) throws Exception{
		if(CollectionUtils.isEmpty(data)){
			return ;
		}
		for(Object obj:data){
			modifyObjectFiledValue(obj,fieldName,value);
		}
	}
	
	
	public static void modifyObjectFiledValue(Object obj,String fieldName,String value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Class<?> clazz = obj.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
	}
	
	 public static void main(String[] args) throws Exception {
	        PrivateTest pt = new PrivateTest();
	        /*Class<?> clazz = PrivateTest.class;
	        // 获得指定类的属性
	        Field field = clazz.getDeclaredField("name");
	        // 值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false 则指示反射的对象应该实施 Java 语言访问检查。
	        field.setAccessible(true);
	        // 更改私有属性的值
	        field.set(pt, "world");*/
	        modifyObjectFiledValue(pt,"name","sfsfdsd");
	        System.out.println(pt.getName());

	    }
}
