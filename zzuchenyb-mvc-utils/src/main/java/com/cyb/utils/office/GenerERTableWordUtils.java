package com.cyb.utils.office;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.cyb.utils.csv.AppUtils;
import com.cyb.utils.date.DateSafeUtil;
import com.cyb.utils.file.FileUtils;
import com.cyb.utils.office.bean.Column;
import com.cyb.utils.office.bean.TablesInfor;
import com.cyb.utils.page.Pagination;
import com.cyb.utils.random.RandomUtils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import sun.misc.BASE64Encoder;
/**
 * 定义word->另存为xml->用工具编辑xml->改名为ftl
 * https://blog.csdn.net/zmx729618/article/details/53283992/
 * 编辑是word里边不要带上{}符号，直接将属性名修改为${name}
 * @author Administrator
 *
 */
public class GenerERTableWordUtils {
	public static void main(String[] args) throws IOException {
		//tb_qh_optvariety_account,tb_qh_optvariety_account_file,tb_qh_promise,tb_qh_question_bank,tb_qh_question_type,tb_qh_question_option,tb_qh_send_msg,tb_qh_bussiness,tb_qh_question_answer_record,tb_qh_config
		String jsonDataFile="D:\\data\\er\\optvar.txt";
		String erFilePath="D:\\data\\er\\";
		GenerERTableWordUtils test = 
				new GenerERTableWordUtils();
		@SuppressWarnings("unchecked")
		List<TablesInfor> tables = (List<TablesInfor>) 
				JSONArray.parse(FileUtils
						.readContentFromFile(
								new File(jsonDataFile)));
		try {
			test.createWord(erFilePath,tables);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
 
	@SuppressWarnings("deprecation")
	public void createWord(String jsonDataFile,List<TablesInfor> tables) throws IOException {
		Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("tables",tables);
		try {
			Pagination x;
			configuration.
			setDirectoryForTemplateLoading(
					new File("d:/data/template/"));   
			configuration.setObjectWrapper(new DefaultObjectWrapper());
			Template template = configuration.getTemplate("tables1.ftl");
			File outFile = new File(jsonDataFile + "tables"+DateSafeUtil.date2long14() + ".doc");
			System.out.println(outFile);
			Writer out = 
					new BufferedWriter(
							new OutputStreamWriter(
									new FileOutputStream(outFile), 
									"UTF-8"));
			template.process(dataMap, out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("deprecation")
	public void createWord() throws IOException {
		Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("tables",AppUtils.getJsonTables().values());        //getExampleData()
		try {
			configuration.
			setDirectoryForTemplateLoading(
					new File("d:/data/template/"));   
			configuration.setObjectWrapper(new DefaultObjectWrapper());
			Template template = configuration.getTemplate("tables1.ftl");
			File outFile = new File("d:/data/template/" + "tables"+DateSafeUtil.date2long14() + ".doc");
			System.out.println(outFile);
			Writer out = 
					new BufferedWriter(
							new OutputStreamWriter(
									new FileOutputStream(outFile), 
									"UTF-8"));
			template.process(dataMap, out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static List<TablesInfor> getExampleData(){
		List<TablesInfor> tables = new ArrayList<>();
		for(int t=0;t<5;t++){
			TablesInfor table = new TablesInfor();
			table.setTableDesc("testst"+t);
			table.setTableName("tab"+t);
			table.setIdx(""+t);
	    	List<Column> columns = new ArrayList<Column>();
	        for(int i=0;i<10;i++){
	        	Column u = new Column();
	        	u.setColName(RandomUtils.getPassWord(8));
	        	u.setColDesc(RandomUtils.getChineaseName());
	        	u.setColLength(i+"");
	        	u.setColType("type"+i);
	        	u.setIsEmpty("否");
	        	columns.add(u);
	        }
	        table.setCols(columns);
	        tables.add(table);
		}
		return tables;
	}

	private Configuration configuration = null;
 
	@SuppressWarnings("deprecation")
	public GenerERTableWordUtils() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
	}
	@SuppressWarnings("unused")
	private String getImageStr(String path) {
		String imgFile = path;
		InputStream in = null;
		byte[] data = null;
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}
}
