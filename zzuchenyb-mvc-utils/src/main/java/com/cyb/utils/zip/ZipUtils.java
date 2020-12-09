package com.cyb.utils.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author iechenyb<br>
 * @Desc 类描述<br>
 * @CreateTime 2020年12月8日 下午12:16:32
 */
public class ZipUtils {
	Log log = LogFactory.getLog(ZipUtils.class);
	private String zipFilePath;      // 目的地Zip文件
    private String sourceFileName;   //源文件（带压缩的文件或文件夹）
    public boolean isEmptyDir=true;//是否以源目录名为压缩包一级目录名
    
    public ZipUtils(String zipFileName,String sourceFileName)
    {
        this.zipFilePath=zipFileName;
        this.sourceFileName=sourceFileName;
    }
    
    public void zip() throws Exception
    {
    	 File oldFile = new File(zipFilePath);
    	 if(oldFile.exists()){
    		 System.out.println("删除旧zip文件!删除成功？"+oldFile.delete());
    	 }
        System.out.println("压缩中...");
        //创建zip输出流
        ZipOutputStream out = new ZipOutputStream( new FileOutputStream(zipFilePath));
        
        //创建缓冲输出流
        BufferedOutputStream bos = new BufferedOutputStream(out);
        
        File sourceFile = new File(sourceFileName);
        
        //调用函数
        compress(out,bos,sourceFile,isEmptyDir?"":sourceFile.getName());
        
        bos.close();
        out.close();
        System.out.println("压缩完成");
        
    }
    
    public void compress(ZipOutputStream out,BufferedOutputStream bos,File sourceFile,String base) throws Exception
    {
        //如果路径为目录（文件夹）
        if(sourceFile.isDirectory())
        {
        
            //取出文件夹中的文件（或子文件夹）
            File[] flist = sourceFile.listFiles();
            
            if(flist.length==0)//如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
            {
                System.out.println(base+"/");
                out.putNextEntry(  new ZipEntry(base+"/") );
            }
            else//如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
            {
                for(int i=0;i<flist.length;i++)
                {
                    compress(out,bos,flist[i],base+"/"+flist[i].getName());
                }
            }
        }
        else//如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
        {
            out.putNextEntry( new ZipEntry(base) );
            FileInputStream fis = new FileInputStream(sourceFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            
            int tag;
            System.out.println(base);
            //将源文件写入到zip文件中
            while((tag=bis.read())!=-1)
            {
                out.write(tag);
            }
            bis.close();
            fis.close();
            
        }
    }

	public static void main(String[] temp) { // 主方法
		ZipUtils zipCom = new ZipUtils("D:\\data\\zip\\to\\h1.zip","D:\\data\\zip\\src");//DIR,压缩文件名
		try {
			zipCom.zip();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
