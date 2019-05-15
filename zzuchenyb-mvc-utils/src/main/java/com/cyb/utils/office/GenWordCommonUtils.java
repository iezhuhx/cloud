package com.cyb.utils.office;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import freemarker.template.Configuration;
import freemarker.template.Template;
/**
 * 根据参数，动态生成doc文件
 * @author Administrator
 *
 */
public class GenWordCommonUtils {
	public static void main(String[] args) throws FileNotFoundException {
        Map<String,String> map=new HashMap<String,String>();
        map.put("ar_cp_name","月报");
        map.put("ar_dateTime","2018-5-28");
        map.put("ar_info","岁的法国大使馆的风格");
        getBuild("d:/data/doc/ar_template.doc",map,"D:/data/doc/aaa.doc");
    }

    public static void getBuild(String  tmpFile, Map<String, String> contentMap, String exportFile) throws FileNotFoundException{

        //InputStream inputStream = DocUtil.class.getClassLoader().getResourceAsStream(tmpFile);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(tmpFile);
        inputStream = new FileInputStream(new File(tmpFile));
        HWPFDocument document = null;
        try {
            document = new HWPFDocument(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 读取文本内容
        Range bodyRange = document.getRange();
        // 替换内容
        for (Map.Entry<String, String> entry : contentMap.entrySet()) {
            bodyRange.replaceText("${" + entry.getKey() + "}", entry.getValue());
        }

        //导出到文件
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.write(byteArrayOutputStream);
            OutputStream outputStream = new FileOutputStream(exportFile);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
	public static  String createWord1(Map<?, ?> dataMap,String templateName,String filePath,String fileName,HttpServletRequest request,HttpServletResponse response){
        String    fileOnlyName=null;
        try {
            //创建配置实例 
            Configuration configuration = new Configuration();
            
            //设置编码
            configuration.setDefaultEncoding("UTF-8");
            
            //ftl模板文件统一放至 template 包下面
            configuration.setClassForTemplateLoading(GenWordCommonUtils.class,"/template/");
            
            //获取模板 
            Template template = configuration.getTemplate(templateName,"UTF-8");
            //重命名
            fileOnlyName = rename(fileName);
            //定义路径 统一放到 webappo/hgjc/uploadRoot目录下
            String servicePath = request.getSession().getServletContext().getRealPath(File.separator);
            String basePath = "";//ReadConfig.getConfigValue("uploadRoot")+File.separator+ReadConfig.getConfigValue(filePath)+File.separator+fileOnlyName; 
            //输出文件
            File outFile = new File(servicePath+basePath);
            
            //如果输出目标文件夹不存在，则创建
            if (!outFile.getParentFile().exists()   ){
                outFile.getParentFile().mkdirs();
            }
            
            //将模板和数据模型合并生成文件 
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
            //生成文件
            template.process(dataMap, out);
            //关闭流
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileOnlyName;
    }

	private static String rename(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}
}
