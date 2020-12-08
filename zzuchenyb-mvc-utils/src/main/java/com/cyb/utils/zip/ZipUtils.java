package com.cyb.utils.zip;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年12月8日 下午12:16:32
 */
public class ZipUtils {
	Log log = LogFactory.getLog(ZipUtils.class);
	
	
	public void zip(String zipFileName, String inputFilePath) throws Exception{
		zip(zipFileName,new File(inputFilePath));
	}
	
	public void zip(String zipFileName, File inputFile) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName)); // 创建ZipOutputStream类对象
		zip(out, inputFile, ""); // 调用方法
		System.out.println("压缩中…"); // 输出信息
		out.close(); // 将流关闭
	}
	
	private void zip(ZipOutputStream out, File f, String base)
			throws Exception { // 方法重载
		if (f.isDirectory()) { // 测试此抽象路径名表示的文件是否是一个目录
			File[] fl = f.listFiles(); // 获取路径数组
			out.putNextEntry(new ZipEntry(base + "/")); // 写入此目录的entry
			base = base.length() == 0 ? "" : base + "/"; // 判断参数是否为空
			for (int i = 0; i < fl.length; i++) { // 循环遍历数组中文件
				zip(out, fl[i], base + fl[i]);
			}
		} else {
			out.putNextEntry(new ZipEntry(base)); // 创建新的进入点
			// 创建FileInputStream对象
			FileInputStream in = new FileInputStream(f);
			int b; // 定义int型变量
			System.out.println(base);
			while ((b = in.read()) != -1) { // 如果没有到达流的尾部
				out.write(b); // 将字节写入当前ZIP条目
			}
			in.close(); // 关闭流
		}
	}
	public static void unzip(String zipFilePath,String outputPath) {
		unzip(new File(zipFilePath),outputPath);
	}
	
	/**
	 * 
	 *@Author iechenyb<br>
	 *@Desc 说点啥<br>
	 *@CreateTime 2020年12月8日 下午12:19:23
	 *@param zipFile 需要加压的文件
	 *@param outputPath 解压目录
	 */
	public static void unzip(File zipFile,String outputPath) {
		ZipInputStream zin; // 创建ZipInputStream对象
		try { // try语句捕获可能发生的异常
			zin = new ZipInputStream(new FileInputStream(zipFile.getName()));
			// 实例化对象，指明要进行解压的文件
			ZipEntry entry = zin.getNextEntry(); // 获取下一个ZipEntry
			while (((entry = zin.getNextEntry()) != null)
					&& !entry.isDirectory()) {
				// 如果entry不为空，并不在同一目录下
				File file = new File(outputPath + entry.getName()); // 获取文件目录
				System.out.println(file);
				if (!file.exists()) { // 如果该文件不存在
					file.mkdirs();// 创建文件所在文件夹
					file.createNewFile(); // 创建文件
				}
				zin.closeEntry(); // 关闭当前entry
				System.out.println(entry.getName() + "解压成功");
			}
			zin.close(); // 关闭流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] temp) { // 主方法
		ZipUtils book = new ZipUtils(); // 创建本例对象
		try {
			// 调用方法，参数为压缩后文件与要压缩文件
			book.zip("hello.zip", new File("D:\\data\\tf\\dbf"));
			System.out.println("压缩完成"); // 输出信息
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
