package com.cyb.utils.sql.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.utils.sql.bean.SQLBean;
import com.cyb.utils.sql.utils.BeanToSqlNoAnnoUtils;
import com.cyb.utils.sql.utils.ProduceSqlUtils;

/**
 * @Author iechenyb<br>
 * @Desc 类描述<br>
 * @CreateTime 2020年12月10日 上午8:37:40
 */
public class BeanToSqlOperator {
	Log log = LogFactory.getLog(BeanToSqlOperator.class);

	public static void main(String[] args) {
		testSQLGenV1();
		testSQLGenV2();//无注解生成sql
	}
	
	public static void testSQLGenV1(){
		System.out.println("建表语句:"+BeanToSqlNoAnnoUtils.genCreateTableSql(SQLBean.class));
	}
	/**
	 * 直接根据new Object 反射出表结构和相应字段信息，字段值为空时，不参与更新和查询操作。
	 *@Author iechenyb<br>
	 *@Desc 说点啥<br>
	 *@CreateTime 2020年12月10日 上午9:50:12
	 */
	public static void testSQLGenV2(){
		String name = "test";
//		Stock stock = new Stock("sh600868","梅雁吉祥");
		
		SQLBean stock = new SQLBean();
		String sql = ProduceSqlUtils.getDeleteSql(name, stock);
		System.out.println(sql);
		
		 sql = ProduceSqlUtils.getInsertSql(name, stock);
		System.out.println(sql);
		
		
		 sql = ProduceSqlUtils.getSelectSql(name, stock);
		System.out.println(sql);
		
		 try {
			sql = ProduceSqlUtils.getUpdateSql(name, stock);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		System.out.println(sql);
	}
}
