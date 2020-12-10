package com.cyb.utils.sql.utils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.app.stock.pub.Stock;
import com.cyb.utils.sql.SQLContants;
/**
 *@Author iechenyb<br>
 *@Desc 类描述 仅仅使用生成创建表语句<br>
 *@CreateTime 2020年12月10日 上午8:34:29
 */
public class BeanToSqlNoAnnoUtils  {
	Log log = LogFactory.getLog(BeanToSqlNoAnnoUtils.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(Stock.class.getCanonicalName());
		/*System.out.println(getBeanFilesList("com.pdt.bean.Dictionary"));
		System.out.println(genCreateTableSql("com.pdt.bean.Dictionary"));
		System.out.println(genInsertSql("com.pdt.bean.Dictionary"));*/
	}
	public static String getBeanName(Class<?> clss){
		return getBeanName(clss.getCanonicalName());
	}
	/**
	 * beane = Stock.class.getCanonicalName() = com.cyb.stock.bean.Stock
	 *@Author iechenyb<br>
	 *@Desc 说点啥<br>
	 *@CreateTime 2020年12月10日 上午10:00:47
	 *@param bean Stock
	 *@return
	 */
	private static String getBeanName(String bean){
		try {
			Class<?> clz = Class.forName(bean);
			//得到类名
			return getTableName(clz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String getTableName(Class<?> clss){
		return clss.getSimpleName();
	}
	
	private static List<String> getBeanPropertyList(String bean){
		try {
			Class<?> clz = Class.forName(bean);
			Field[] strs = clz.getDeclaredFields();
			List<String> propertyList = new ArrayList<String>();
			for (int i = 0; i < strs.length; i++) {
				String protype = strs[i].getType().toString();
				if(SQLContants.filterProperty.contains(strs[i].getName())){
					continue;
				}
				propertyList.add(protype.substring(protype.lastIndexOf(".")+1)+"`"+strs[i].getName());
			}
			return propertyList;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String getBeanFilesList(String bean){
		try {
			Class<?> clz = Class.forName(bean);
			Field[] strs = clz.getDeclaredFields();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < strs.length; i++) {
				//String protype = strs[i].getType().toString();
				if(SQLContants.filterProperty.contains(strs[i].getName())){
					continue;
				}
				if (!strs[i].getName().equals("tableName")&&!strs[i].getType().equals("List")) {
				   sb.append(strs[i].getName()+",");
				}
			}
			sb.deleteCharAt(sb.toString().lastIndexOf(","));
			return sb.toString();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String genCreateTableSql(Class<?> clss){
		return genCreateTableSql(clss.getCanonicalName());
	}
	/**
	 * 生成建表語句
	 * @param bean
	 * @return
	 */
	private static String genCreateTableSql(String bean){
		List<String> beanPropertyList =  getBeanPropertyList(bean);
		StringBuffer sb = new StringBuffer("create table "+getBeanName(bean)+"(\n");
		for (String string : beanPropertyList) {
			String[] propertys = string.split("`");
			if (!propertys[1].equals("tableName")&&!propertys[1].equals("param")&&!propertys[0].equals("List")) {
				if (propertys[1].equals("id")) {
					sb.append("   id "+getH2DBColumnType(propertys[0])+" primary key auto_increment,\n");
				} else {
					/*if (propertys[0].equals("int")) {
						sb.append("   " + propertys[1] + " int default 0 comment '',\n");
					} else if (propertys[0].equals("String")) {
						sb.append("   " + propertys[1] + " varchar(2000) default '' comment '',\n");
					} else if (propertys[0].equals("double")) {
						sb.append("   " + propertys[1] + " double(10,2) default 0.0 comment '',\n");
					} else if (propertys[0].equals("Date")) {
						sb.append("   " + propertys[1] + " datetime comment '',\n");
					}*/
					sb.append("   " + propertys[1] + " "+getH2DBColumnType(propertys[0])+" comment '',\n");
					
				}
			}
		}
		sb.append(")");
		sb.deleteCharAt(sb.lastIndexOf(","));
		return sb.toString();
	}
	
	
	
	
	public  static String getH2DBColumnType(String fieldType){
		String type = fieldType.toUpperCase();
		if(SQLContants.COLUMNSTYPEMAP.containsKey(type)){
			return SQLContants.COLUMNSTYPEMAP.get(type);
		}
		System.err.print("找不到对应的类型！"+fieldType);
		return null;
	}
	
	
	/**
	 * 生成查询语句 不用
	 * @param bean
	 * @return
	 */
	public static String genSelectAllSql(String bean){
		String filesList =  getBeanFilesList(bean);
		return "select \n "+filesList+" \n from \n "+getBeanName(bean)+"";
	}
	
	/**
	 * 生成插入语句 用#{} 不用
	 * @param bean
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String genInsertSql(String bean){
		String filesList =  getBeanFilesList(bean);
		int fl = filesList.split(",").length;//DataUtil.getCountSonStr(filesList,",")+1;
		String wenhao = "";
		for (int i = 0; i < fl; i++) {
			if(i==fl-1){
				wenhao = wenhao+"${"+filesList.split(",")[i]+"}";
			}else{
				wenhao = wenhao+"${"+filesList.split(",")[i]+"},";
			}
		}
		return "insert into \n"+getBeanName(bean)+"("+filesList+") \n values("+wenhao+")";
	}
	
	/**
	 * 生成插入语句 用问号 不用
	 * @param bean
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String genInsertSqlWhy(String bean){
		String filesList =  getBeanFilesList(bean);
		int fl = filesList.split(",").length+1;//DataUtil.getCountSonStr(filesList,",")+1;
		String wenhao = "";
		for (int i = 0; i < fl; i++) {
			if(i==fl-1){
				wenhao = wenhao+"?";
			}else{
				wenhao = wenhao+"?,";
			}
		}
		return "insert into \n "+getBeanName(bean)+"("+filesList+") \n values("+wenhao+")";
	}
}
