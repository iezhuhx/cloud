package com.kiiik.utils;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

import com.kiiik.web.company.entity.CompanyEntity;
import com.kiiik.web.system.po.User;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月23日
 */
public class SQLUtils {
	Log log = LogFactory.getLog(SQLUtils.class);
	public static String test(){
		String str = new SQLGen<CompanyEntity>().del(new CompanyEntity());
		return str;
	}
	public static void main(String[] args) {
		System.out.println(test());
	}
}
interface GeneralMapper<T> {
	@InsertProvider(method="insert",type=SQLGen.class)
	@Options(useGeneratedKeys=true,keyProperty="id")
	int save(T t);
	
	@DeleteProvider(method="del",type=SQLGen.class)
	int del(T t);
	
	@UpdateProvider(method="update",type=SQLGen.class)
	int update(T t);
	
	@SelectProvider(method="select",type=SQLGen.class)
	List<T> list(T t);
	
}
class SQLGen<T> {
	public String select(final T object) {
		return new SQL() {
			{
				SELECT("*");
				FROM(object.getClass().getSimpleName());
				try {
					Field[] fields = object.getClass().getDeclaredFields();
					for (Field field : fields) {
						field.setAccessible(true);
						Object v = field.get(object);
						if (v != null) {
							String fieldName = field.getName();
							if (v instanceof String && ((String)v).contains("%")) {
								WHERE(fieldName + " like '"+v+"'" );
							} else {
								WHERE(fieldName + "=#{" + fieldName + "}");
							}
							
						}
					}
				} catch (Exception e) {
				}
 
			}
		}.toString();
	}
	public String update(final T object) {
		return new SQL() {
			{
				UPDATE(object.getClass().getSimpleName());
				try {
					Field[] fields = object.getClass().getDeclaredFields();
					for (Field field : fields) {
						field.setAccessible(true);
						Object v = field.get(object);
						if (v != null) {
							String fieldName = field.getName();
							SET(fieldName + "=#{" + fieldName + "}");
						}
					}
				} catch (Exception e) {
				}
				WHERE("id=#{id}");
			}
		}.toString();
	}
	public String insert(final T object) {
		return new SQL() {
			{
				INSERT_INTO(object.getClass().getSimpleName());
				try {
					Field[] fields = object.getClass().getDeclaredFields();
					for (Field field : fields) {
						field.setAccessible(true);
						Object v = field.get(object);
						if (v != null) {
							String fieldName = field.getName();
							VALUES(fieldName,"#{"+fieldName+"}");
						}
					}
				} catch (Exception e) {
				}
			}
		}.toString();
	}
	public String del(final T object) {
		return new SQL() {
			{
				DELETE_FROM(object.getClass().getSimpleName());
				try {
					Field[] fields = object.getClass().getDeclaredFields();
					for (Field field : fields) {
						field.setAccessible(true);
						Object v = field.get(object);
						if (v != null) {
							String fieldName = field.getName();
							if (v instanceof String && ((String)v).contains("%")) {
								WHERE(fieldName + " like '"+v+"'" );
							} else {
								WHERE(fieldName + "=#{" + fieldName + "}");
							}
						}
					}
				} catch (Exception e) {
				}
			}
		}.toString();
	}

	//@Mapper
	interface UserMapper extends GeneralMapper<User> {
	 
		@Insert("insert into User(name,age) values(#{name},#{age})")
		int addUser(@Param("name") String name, @Param("age") int age);
	 
		@Select("select * from User where id =#{id}")
		User findById(@Param("id") int id);
		
		@Update("update User set name=#{name} where id=#{id}")
		void updataById(@Param("id") int id, @Param("name") String name);
	 
		@Delete("delete from User where id=#{id}")
		void deleteById(@Param("id") int id);
	}
}
