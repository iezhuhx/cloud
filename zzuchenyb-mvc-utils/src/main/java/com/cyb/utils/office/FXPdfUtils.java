package com.cyb.utils.office;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
/**
 * 动态生成pdf文件
 * https://www.cnblogs.com/wangpeng00700/p/8418594.html
 * @author Administrator
 *
 */
public class FXPdfUtils {
	
	 // 模板路径  
   static String templatePath = "d:/data/fx/证明模板.pdf";
    // 生成的新文件路径  
   static String newPDFPath = "d:/data/fx/out"+"-11"+".pdf";
	// 利用模板生成pdf  
    @SuppressWarnings("unchecked")
	public static void pdfout(Map<String,Object> o) {
       

        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            BaseFont bf = BaseFont.createFont("c://windows//fonts//simsun.ttc,1" , BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            out = new FileOutputStream(newPDFPath);// 输出流
            reader = new PdfReader(templatePath);// 读取pdf模板  
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();
            //表单文字类的内容处理
            Map<String,String> datemap = (Map<String,String>)o.get("datemap");
            form.addSubstitutionFont(bf);
            for(String key : datemap.keySet()){
                String value = datemap.get(key);
                form.setField(key,value);
            }
            //表单图片类的内容处理
            Map<String,String> imgmap = (Map<String,String>)o.get("imgmap");
            for(String key : imgmap.keySet()) {
                String value = imgmap.get(key);
                String imgpath = value;
                int pageNo = form.getFieldPositions(key).get(0).page;
                Rectangle signRect = form.getFieldPositions(key).get(0).position;
                float x = signRect.getLeft();
                float y = signRect.getBottom();
                //根据路径读取图片
                Image image = Image.getInstance(imgpath);
                //获取图片页面
                PdfContentByte under = stamper.getOverContent(pageNo);
                //图片大小自适应
                image.scaleToFit(signRect.getWidth()*2, signRect.getHeight()*1);
                //添加图片
                image.setAbsolutePosition(x, y);
                under.addImage(image);
            }
            stamper.setFormFlattening(true);// 如果为false，生成的PDF文件可以编辑，如果为true，生成的PDF文件不可以编辑
            stamper.close();
            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();
        } catch (IOException e) {
        	e.printStackTrace();
            System.out.println(e);
        } catch (DocumentException e) {
        	e.printStackTrace();
            System.out.println(e);
        }

    }
//数字英文  汉字显示正常
   public static void main(String[] args) {
	   //复选框使用
	   //循环列表显示
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name","张三");
        map.put("boy","yes");
        map.put("girl","no");
        map.put("time","2018年1月1日");
        map.put("idcard","411522198502062563");
        map.put("addr", "上海市吴中路686号D座文娱中心");
        map.put("a.b","上海市吴中路686号D座文娱中心a.b");
        Map<String,String> mapIn = new HashMap<String, String>();
        mapIn.put("att", "我是嵌套属性");

        Map<String,String> map2 = new HashMap<String, String>();
        map2.put("img","d:/data/fx/earth.jpg");//可以直接是text框，做印章不太合适，需要根据不同的数据进行加密
       
        Map<String,Object> o=new HashMap<String, Object>();
        //map.put("inner", mapIn);
        
        List<Map<String,Object>> listData = new ArrayList<>();
        listData.add(map);listData.add(map);listData.add(map);listData.add(map);listData.add(map);
        o.put("datemap",map);
        o.put("imgmap",map2);
        o.put("list", listData); //循环数据展示
        //电子章使用 ok
        pdfout(o);
    }
        
    public static void extractPdf(){

        String templatePath ="d:/data/fx/m1.pdf";;

        PdfReader reader;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            reader = new PdfReader(templatePath);// 读取pdf模板
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();
            List<String> valueList = new ArrayList<>();
            List<String> nameList = new ArrayList<>();
            java.util.Iterator<String> it = form.getFields().keySet().iterator();
            while (it.hasNext()) {
                String name = it.next();
                String value = form.getField(name);
                valueList.add(value);
                nameList.add(name);
            }
            stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.close();

        } catch (IOException e) {
            e.getStackTrace();
        } catch (DocumentException e) {
            e.getStackTrace();
        }

    }
}
