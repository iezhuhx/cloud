package com.cyb.utils.superutil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MapObjectUtils {
	/**
	 * 利用反射将map集合封装成bean对象
	 * 
	 * @param params
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T mapToBean(Map<String, Object> map, Class<?> clazz) throws Exception {
		Object obj = clazz.newInstance();
		if (map != null && !map.isEmpty() && map.size() > 0) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String propertyName = entry.getKey(); 	// 属性名
				Object value = entry.getValue();		// 属性值
				String setMethodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
				Field field = getClassField(clazz, propertyName);	//获取和map的key匹配的属性名称
				if (field == null){
					continue;
				}
				Class<?> fieldTypeClass = field.getType();
				value = convertValType(value, fieldTypeClass);
				try {
					clazz.getMethod(setMethodName, field.getType()).invoke(obj, value);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		}
		return (T) obj;
	}
	/**
	 * 根据给定对象类匹配对象中的特定字段
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	private static Field getClassField(Class<?> clazz, String fieldName) {
		if (Object.class.getName().equals(clazz.getName())) {
			return null;
		}
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		Class<?> superClass = clazz.getSuperclass();	//如果该类还有父类，将父类对象中的字段也取出
		if (superClass != null) {						//递归获取
			return getClassField(superClass, fieldName);
		}
		return null;
	}

	/**
	 * 将map的value值转为实体类中字段类型匹配的方法
	 * @param value
	 * @param fieldTypeClass
	 * @return
	 */
	private static Object convertValType(Object value, Class<?> fieldTypeClass) {
		Object retVal = null;
		
		if (Long.class.getName().equals(fieldTypeClass.getName())
				|| long.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Long.parseLong(value.toString());
		} else if (Integer.class.getName().equals(fieldTypeClass.getName())
				|| int.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Integer.parseInt(value.toString());
		} else if (Float.class.getName().equals(fieldTypeClass.getName())
				|| float.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Float.parseFloat(value.toString());
		} else if (Double.class.getName().equals(fieldTypeClass.getName())
				|| double.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Double.parseDouble(value.toString());
		} else {
			retVal = value;
		}
		return retVal;
	}
	/**
	 * 对象转map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> objToMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = obj.getClass().getDeclaredFields();	// 获取f对象对应类中的所有属性域
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();
			varName = varName.toLowerCase();					// 将key置为小写，默认为对象的属性
			try {
				boolean accessFlag = fields[i].isAccessible();	// 获取原来的访问控制权限
				fields[i].setAccessible(true);					// 修改访问控制权限
				Object o = fields[i].get(obj);					// 获取在对象f中属性fields[i]对应的对象中的变量
				if (o != null){
					map.put(varName, o.toString());
				}
				fields[i].setAccessible(accessFlag);			// 恢复访问控制权限
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return map;
	}
}
