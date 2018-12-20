package com.swagger.offline;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.alibaba.fastjson.JSON;
import com.swagger.offline.model.User;

import io.github.robwin.markup.builder.MarkupLanguage;
import io.github.robwin.swagger2markup.GroupBy;
import io.github.robwin.swagger2markup.Swagger2MarkupConverter;
import springfox.documentation.staticdocs.SwaggerResultHandler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

/**
 * @version V1.0
 * @Title:
 * @ClassName: SwaggerStaticDocTest.java
 * @Description:
 * @Copyright 2016-2018  - Powered By 研发中心
 * @author: iechenyb
 * @date: 2018-01-22 16:06
 * 参考文件：https://github.com/quiterr/restdocs/
 * 操作说明   如果是native 直接install
 * 如果是remote 先跑单元测试下载json文件， 然后install
 */
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@RunWith(SpringRunner.class)
@SpringBootTest
public class SwaggerStaticDocTest {
    //规则及生成目录
    private String snippetDir = "target/generated-snippets";
    private String outputDir  = "target/asciidoc";

    @Autowired
    public MockMvc mockMvc;
    //输入目录
    static String docUrl = "http://localhost:8090/demo/v2/api-docs";
    static String baseDir = System.getProperty("user.dir");
    String type="remote";//remote native 远端
    @After
    public void Test() throws Exception {
    	if(type.equals("native")){
        // 得到本工程的swagger.json,写入outputDir目录中
         mockMvc.perform(get("/v2/api-docs").accept(MediaType.APPLICATION_JSON))
                .andDo(SwaggerResultHandler.outputDirectory(outputDir).build())
                .andExpect(status().isOk())
                .andReturn();
    	}else{
    		 // 这个outputDir必须和插件里面<generated></generated>标签配置一致
       	 try{
       		 //获取接口文档信息
               downLoadFromUrl(docUrl,"/swagger.json",baseDir+"/"+outputDir);
           }catch (Exception e) {
           	e.printStackTrace();
           }	
    	}

        // 读取上一步生成的swagger.json转成asciiDoc,写入到outputDir
    	 //生成到当前工程目录下
        Swagger2MarkupConverter.from(outputDir + "/swagger.json")
                .withPathsGroupedBy(GroupBy.TAGS)// 按tag排序
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)// 格式
                .withExamples(snippetDir)
                .build()
                .intoFolder(outputDir);// 输出
    }

    @Test
    public void downLoad(){
    	
    }
    
    @Test
    public void TestApi() throws Exception {
    	System.err.println("文档生成目录"+baseDir+"/"+outputDir);
    	if(type.equals("native")){
	        mockMvc.perform(get("/user/getUser").param("name", "FLY")
	                .accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andDo(MockMvcRestDocumentation.document("getUser", preprocessResponse(prettyPrint())));
	
	        User user = new User();
	        user.setId(123456);
	        user.setName("FLY");
	        user.setAge(25);
	        user.setAddress("河南郑州");
	        user.setSex("男");
	
	        mockMvc.perform(post("/user/addUser").contentType(MediaType.APPLICATION_JSON)
	                .content(JSON.toJSONString(user))
	                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcRestDocumentation.document("addUser", preprocessResponse(prettyPrint())));
    	}
    }
    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
        URL url = new URL(urlStr);  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
                //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();  
        //获取自己数组
        byte[] getData = readInputStream(inputStream);    

        //文件保存位置
        File saveDir = new File(savePath);
        if(saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);    
        if(file.exists()){
        	file.delete();
        }
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);     
        fos.write(getData); 
        if(fos!=null){
            fos.close();  
        }
        if(inputStream!=null){
            inputStream.close();
        }
        System.out.println("info:"+url+" download success"); 

    }
    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        while((len = inputStream.read(buffer)) != -1) {  
            bos.write(buffer, 0, len);  
        }  
        bos.close();  
        return bos.toByteArray();  
    }  

   
    public static void main(String[] args) {
        try{
        	 String outputDir  = "/target/asciidoc";
            downLoadFromUrl(docUrl,
                    "/swagger.json",baseDir+outputDir);//System.getProperty("user.dir")
        }catch (Exception e) {
        	e.printStackTrace();
        }
    }

}
