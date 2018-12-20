package io.renren.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.renren.entity.ColumnEntity;
import io.renren.entity.TableEntity;

/**
 * 代码生成器   工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午11:40:24
 */
@Component
public class GenUtils {
	
	@Autowired
    Environment env;
	
    public  List<String> getTemplates(){
    	List<String> templates = new ArrayList<String>();
    	templates.add("template/Entity.java.vm");
    	templates.add("template/Dao.java.vm");
        templates.add("template/Dao.xml.vm");
        templates.add("template/Service.java.vm");
        templates.add("template/ServiceImpl.java.vm");
        templates.add("template/Controller.java.vm");
    	if(env.getProperty("spring.profiles.active").equals("dev")){
	        templates.add("template/menu.sql.vm");
	        templates.add("template/index.vue.vm");
	        templates.add("template/add-or-update.vue.vm");
    	}
        return templates;
    }

    /**
     * 生成代码
     */
    public  void generatorCode(Map<String, String> table,
                                     List<Map<String, String>> columns, ZipOutputStream zip) {
        //配置信息
        Configuration config = getConfig();
        boolean hasBigDecimal = false;
        boolean useComments = getConfig().getBoolean("useComments");//Boolean.valueOf(env.getProperty("useComments"));//是否使用注释
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName" ));
        if(useComments){
        	tableEntity.setComments(table.get("tableComment" ));
        }else{
        	tableEntity.setComments(tableEntity.getTableName());
        }
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix" ));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));
        
        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for(Map<String, String> column : columns){
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName" ));
            columnEntity.setDataType(column.get("dataType" ));
            if(useComments){
            	 columnEntity.setComments(column.get("columnComment" ));//列注释
            }else{
            	columnEntity.setComments(columnEntity.getColumnName());
            }
           
            columnEntity.setExtra(column.get("extra" ));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType" );
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && attrType.equals("BigDecimal" )) {
                hasBigDecimal = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey" )) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader" );
        Velocity.init(prop);
        String mainPath = config.getString("mainPath" );
        mainPath = StringUtils.isBlank(mainPath) ? "io.renren" : mainPath;
        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());//表名
        map.put("comments", tableEntity.getComments());//表注释
        map.put("pk", tableEntity.getPk());
        //map.put("className", tableEntity.getClassName());//TSysEmployeeController
        /**
         * 2018年11月7日16:46:41
         * 更新逻辑  将className进行处理 ，将指定的规则集合去掉  比如  t_sys_xxxxx  去掉TSys
         */
        String[] cutNames = null;
        if(!org.springframework.util.StringUtils.isEmpty(Constant.cutName)){
        	cutNames = Constant.cutName.split(",");
        }else{
        	cutNames = getConfig().getStringArray("cutNames");
        }
        if(cutNames.length>0){
        	String className_ =  tableEntity.getClassName();
	        for(int i=0;i<cutNames.length;i++){
	        	className_ = className_.replace(cutNames[i], "");
	        }
	        map.put("className", className_);
        }else{
        	map.put("className", tableEntity.getClassName());
        }
        if(map.get("className")!=null){
        	map.put("classname", toLowerCaseFirstOne(map.get("className").toString()));//首字母小写类名
        }else{
        	map.put("classname",tableEntity.getClassname());
        }
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("mainPath", mainPath);
        map.put("package", config.getString("package" ));
        map.put("moduleName",map.get("classname"));//generator config.getString("moduleName") 
        map.put("author", config.getString("author" ));
        map.put("email", config.getString("email" ));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8" );
            tpl.merge(context, sw);

            try {
                //添加到zip
                //zip.putNextEntry(new ZipEntry(getFileName(template, tableEntity.getClassName()+1, config.getString("package" ), config.getString("moduleName" ))));
                zip.putNextEntry(new ZipEntry(getFileName(template, map.get("className").toString(), config.getString("package" ), map.get("classname").toString())));
                IOUtils.write(sw.toString(), zip, "UTF-8" );
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RRException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }

  //首字母转小写
    public static String toLowerCaseFirstOne(String s){
      System.out.println("小写转换内容："+s);
      if(Character.isLowerCase(s.charAt(0)))
        return s;
      else
        return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }


    //首字母转大写
    public static String toUpperCaseFirstOne(String s){
      System.out.println("大写转换内容："+s);
      if(Character.isUpperCase(s.charAt(0)))
        return s;
      else
        return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    /**
     * 列名转换成Java属性名
     */
    public  String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "" );
    }

    /**
     * 表名转换成Java类名
     */
    public  String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "" );
        }
        return columnToJava(tableName);
    }
    static PropertiesConfiguration pro;
    /**
     * 获取配置信息
     */
    public  synchronized Configuration getConfig() {
        try {
        	if(pro == null){
        		pro =  new PropertiesConfiguration("generator.properties" );
        	}
        	return pro;
        } catch (ConfigurationException e) {
            throw new RRException("获取配置文件失败，", e);
        }
    }
    
    /**
     * 获取配置信息
     */
    public  Configuration getAppConfig() {
        try {
            return new PropertiesConfiguration("application.yml" );
        } catch (ConfigurationException e) {
            throw new RRException("获取配置文件失败，", e);
        }
    }
    
    public static  void main(String[] args) {
		/*String port = getAppConfig().getString("server:port");
		System.out.println(port);*/
		System.out.println(new GenUtils().getConfig().getStringArray("cutNames")[0]);
		
	}

    /**
     * 获取文件名
     */
    public  String getFileName(String template, String className, String packageName, String moduleName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }

        if (template.contains("Entity.java.vm" )) {
            return packagePath + "entity" + File.separator + className + "Entity.java";
        }

        if (template.contains("Dao.java.vm" )) {
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }

        if (template.contains("Service.java.vm" )) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm" )) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm" )) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains("Dao.xml.vm" )) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + moduleName + File.separator + className + "Dao.xml";
        }

        if (template.contains("menu.sql.vm" )) {
            return className.toLowerCase() + "_menu.sql";
        }

        if (template.contains("index.vue.vm" )) {
            return "main" + File.separator + "resources" + File.separator + "src" + File.separator + "views" + File.separator + "modules" +
                    File.separator + moduleName + File.separator + className.toLowerCase() + ".vue";
        }

        if (template.contains("add-or-update.vue.vm" )) {
            return "main" + File.separator + "resources" + File.separator + "src" + File.separator + "views" + File.separator + "modules" +
                    File.separator + moduleName + File.separator + className.toLowerCase() + "-add-or-update.vue";
        }

        return null;
    }
   
}
