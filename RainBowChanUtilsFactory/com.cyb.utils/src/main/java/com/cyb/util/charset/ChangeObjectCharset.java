package com.cyb.util.charset;

import lombok.extern.slf4j.Slf4j;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年4月11日
 */
@Slf4j
public class ChangeObjectCharset {
	EntryCoder coder;
	
	public void setCoder(EntryCoder coder) {
		this.coder = coder;
	}

	public static void main(String[] args)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		/*User user = new User();
		user.setName("涓枃");
		ChangeObjectCharset coc = new ChangeObjectCharset();
		AbstractJDKEncoder coder = new JDKEncoder();
		coc.setCoder(coder);
		coc.changeObjectCharset(user);
		System.out.println(user.getName());*/
	}

	public void changeObjectCharset(Object bean) {
		Class<?> userCla;
		try {
			userCla = bean.getClass();
			Field[] fs = userCla.getDeclaredFields();
			for (int i = 0; i < fs.length; i++) {
				Field f = fs[i];
				f.setAccessible(true); // 设置些属性是可以访问的
				Object val = f.get(bean);// 得到此属性的值
				System.out.println(f.getName() + "= " + val);
				String type = f.getType().toString();// 得到此属性的类型
				if (type.endsWith("String")) {
					try {
						if (val != null) {
							f.set(bean, coder.decode(val.toString()));
						}
					} catch (Exception e) {
						e.printStackTrace();

					}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
