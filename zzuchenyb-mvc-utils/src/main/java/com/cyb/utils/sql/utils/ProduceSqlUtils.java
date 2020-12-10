package com.cyb.utils.sql.utils;
import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.cyb.utils.sql.SQLContants;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
/**
 *@Author iechenyb<br>
 *@Desc 类描述 引用了糊涂工具   hutool<br>
 *@CreateTime 2020年12月10日 上午9:09:00
 */
public class ProduceSqlUtils extends BeanToSqlNoAnnoUtils{
	Log log = LogFactory.getLog(ProduceSqlUtils.class);
	//String insertSql = getInsertSql("user", User.class, user);
	public static  <T> String getInsertSql( T t) throws IllegalArgumentException  {
		return getInsertSql(getTableName(t.getClass()),t);	
	}
	public static  <T> String getUpdateSql( T t) throws Exception  {
		return getUpdateSql(getTableName(t.getClass()),t);	
	}
	public static  <T> String getDeleteSql( T t) throws IllegalArgumentException  {
		return getDeleteSql(getTableName(t.getClass()),t);	
	}
	public static  <T> String getSelectSql( T t) throws IllegalArgumentException  {
		return getSelectSql(getTableName(t.getClass()),t);	
	}
	 
    /**
     * 生成插入语句
     * @param tablename 表明
     * @param t 有数据的实体
     * @param <T> 数据实体类型 如 User
     */
    public static  <T> String getInsertSql(String tablename, T t) throws IllegalArgumentException  {
        //insert into table_name (column_name1,column_name2, ...) values (value1,value2, ...)
        boolean flag = false;
        String sql = "";
        Field[] fields = ReflectUtil.getFieldsDirectly(t.getClass(), false);
        StringBuffer topHalf = new StringBuffer("insert into "+tablename+" (");
        StringBuffer afterAalf = new StringBuffer("values (");
        for (Field field : fields) {
        	if(SQLContants.filterProperty.contains(field.getName())){
				continue;
			}
            if ("ID".equals(field.getName()) || "id".equals(field.getName())){
                continue;   //id 自动生成无需手动插入
            }
            if(StringUtils.isEmpty(ReflectUtil.getFieldValue(t, field.getName()))) continue;//属性为空 跳过
            topHalf.append(field.getName() + ",");
            if (ReflectUtil.getFieldValue(t, field.getName()) instanceof String     		) {
                afterAalf.append("'" + ReflectUtil.getFieldValue(t, field.getName()) + "',");
                flag = true;
            } else {
                afterAalf.append(ReflectUtil.getFieldValue(t, field.getName()) + ",");
                flag = true;
            }
        }
        if (!flag) {
            throw new IllegalArgumentException(t.getClass() +  "NullException.\nThere is no attribute that is not empty.You must provide an object with at least one attribute.");
        }
        topHalf = new StringBuffer(StrUtil.removeSuffix(topHalf.toString(), ","));
        afterAalf = new StringBuffer(StrUtil.removeSuffix(afterAalf.toString(), ","));
        topHalf.append(") ");
        afterAalf.append(") ");
        sql = topHalf.toString() + afterAalf.toString();
        return sql;
    }
 
    /**
     * 生成更新语句
     * 必须含有id 且根据id更新记录
     * 数据实体中 null 与 空字段不参与更新
     * @param tablename 数据库中的表明
     * @param t 有数据的实体
     * @param <T> 数据实体类型,如 User
     * @throws Exception 
     */
    public static  <T> String getUpdateSql(String tablename, T t) throws Exception {
        //UPDATE table_name SET column_name1 = value1, column_name2 = value2, ... where ID=xxx
        //or
        //UPDATE table_name SET column_name1 = value1, column_name2 = value2, ... where id=xxx
    	
        boolean flag = false;
        String sql = "";
        String id = ""; //保存id列名：ID or id
        Field[] fields = ReflectUtil.getFieldsDirectly(t.getClass(), false);
        sql = "update "+tablename+" set ";
        for (Field field : fields) {
        	if(SQLContants.filterProperty.contains(field.getName())){
				continue;
			}
            StringBuffer tmp = new StringBuffer();
            if ("ID".equals(field.getName()) || "id".equals(field.getName())){
                id = field.getName();
                if(StringUtils.isEmpty(ReflectUtil.getFieldValue(t, id))){
                	throw new Exception("id不能为空！");
                }
                continue;//更新的时候无需set id=xxx
            }
            //if (ReflectUtil.getFieldValue(t, field.getName()) != null && ReflectUtil.getFieldValue(t, field.getName()) != "") {
            if (!StringUtils.isEmpty(ReflectUtil.getFieldValue(t, field.getName()))){
            	tmp.append( field.getName() + "=");
                if (ReflectUtil.getFieldValue(t, field.getName()) instanceof String) {
                    tmp.append( "'" + ReflectUtil.getFieldValue(t, field.getName()) + "',");
                    flag = true;
                } else {
                    tmp.append(ReflectUtil.getFieldValue(t, field.getName()) + ",");
                    flag = true;
                }
                sql += tmp;
            }
        }
        if (!flag) {
            throw new IllegalArgumentException(t.getClass() +  "NullException.\nThere is no attribute that is not empty except for ID.You must provide an object with at least one attribute exclude ID.");
        }
        sql = StrUtil.removeSuffix(sql, ",") + " where " + id + "='" + ReflectUtil.getFieldValue(t, id)+"'";
        return sql;
    }
 
    /**
     * 生成删除语句
     * 根据 user 中第一个不为空的字段删除,应该尽量使用 id,提供至少一个非空属性
     * @param tablename 表明
     * @param t 有数据的实体
     * @param <T> 数据实体类型 如 User
     */
    public static  <T> String getDeleteSql(String tablename, T t) throws IllegalArgumentException {
        //delete from table_name where column_name = value
        return getSelectOrDeleteSql(tablename, t, "delete");
    }
 
    /**
     * 生成查询语句
     * 根据 user 中第一个不为空的字段查询
     * @param tablename 表名
     * @param t 有数据的实体
     * @param <T> 数据实体类型 如 User
     */
    public static  <T> String getSelectSql(String tablename, T t) throws IllegalArgumentException {
        //delete from table_name where column_name = value
        return getSelectOrDeleteSql(tablename, t, "select *");
    }
 
    /**
     * 根据 operation 生成一个如：operation from table_name where column_name = value 的sql语句
     * @param tablename
     * @param t
     * @param operation "select *"  or "delete"
     * @param <T>
     * @return
     * @throws IllegalArgumentException
     */
    private static  <T> String getSelectOrDeleteSql(String tablename, T t, String operation) throws IllegalArgumentException {
        //operation from table_name where column_name = value
        boolean flag = false;
        String sql = "";
        int paramIndex = 1;
        Field[] fields = ReflectUtil.getFieldsDirectly(t.getClass(), false);
        StringBuffer topHalf = new StringBuffer(operation + " from " + tablename + " where ");
        for (Field field : fields) {
        	if(SQLContants.filterProperty.contains(field.getName())){
				continue;
			}
            if ("ID".equals(field.getName().toUpperCase())) {
                if (!StringUtils.isEmpty(ReflectUtil.getFieldValue(t, field.getName()))) {
                    //id 不为空
                    topHalf.append(field.getName() + " = " + ReflectUtil.getFieldValue(t, field.getName()));
                    paramIndex++;
                    flag = true;
                    //break;
                }
            }else {
            	//ReflectUtil.getFieldValue(t, field.getName()) != null && ReflectUtil.getFieldValue(t, field.getName()) != ""
                if (!StringUtils.isEmpty(ReflectUtil.getFieldValue(t, field.getName()))) {
                	if(paramIndex==1){
                		topHalf.append(field.getName() + " = '" + ReflectUtil.getFieldValue(t, field.getName()) + "'");
                    }else{
                    	topHalf.append(" and "+field.getName() + " = '" + ReflectUtil.getFieldValue(t, field.getName()) + "'");
                    }
                	paramIndex++;
                    flag = true;
                    //break;
                }
            }
        }
        if (!flag) {
            throw new IllegalArgumentException(t.getClass() +  " NullException.\nThere is no attribute that is not empty.You must provide an object with at least one attribute.(至少提供一个有属性值的对象！)");
        }
        sql = topHalf.toString();
        return sql;
    }

}
