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

import com.cyb.utils.office.bean.Person;

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
public class WordExportController {
	public static void main(String[] args) {
		WordExportController test = new WordExportController();
		test.createWord();
	}
 
	private Configuration configuration = null;
 
	@SuppressWarnings("deprecation")
	public WordExportController() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
	}
 
	@SuppressWarnings("deprecation")
	public void createWord() {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		/*String image=getImageStr("
		 * E:/temp/7575198B15CC5EB88031CFEB4D0C4B60.jpg");
		dataMap.put("image", image);*/
    	List<Person> personList = new ArrayList<Person>();
        for(int i=0;i<10;i++){
        	Person u = new Person();
        	u.setName("ZhangSan"+i);
        	u.setRealName("张三"+i);
        	u.setPhone("130xxxxxxxx"+i); 
        	personList.add(u);
        }
        dataMap.put("users", personList);
		try {
			/*configuration.
			setClassForTemplateLoading(this.getClass(),
					"/template");*/ // FTL文件所存在的位置
			configuration.
			setDirectoryForTemplateLoading(
					new File("d:/data/template/"));   
			configuration.setObjectWrapper(new DefaultObjectWrapper());
			Template template = configuration.getTemplate("list2.ftl");
			File outFile = new File("d:/data/tmp/" + "AAAA" + ".doc");
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
