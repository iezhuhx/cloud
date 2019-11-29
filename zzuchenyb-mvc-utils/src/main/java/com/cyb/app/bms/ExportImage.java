package com.cyb.app.bms;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.cyb.utils.file.FileUtils;

public class ExportImage {
	static String dir ="d:/data/imageexport/";
	public static void main(String[] args) {
		
		
		initMap();
		System.out.println("关系信息："+cidFundAccountMap);
		File[] files = new File(dir+"161048593/").listFiles();
		for(int i=0;i<files.length;i++){
			unZip(files[i],dir+"tmp");
		}
		
	}
	
	public static void initMap(){
		List<String> data = FileUtils.readFileToList(dir+"fund-client-id.txt");
		for(String str:data){
			cidFundAccountMap.put(str.split("_")[0], str.substring(str.indexOf("_")+1, str.length()));
		}
	}

	/**
	 * 
	 * zip解压
	 * 
	 * @param srcFile
	 *            zip源文件
	 * 
	 * @param destDirPath
	 *            解压后的目标文件夹
	 * 
	 * @throws RuntimeException
	 *             解压失败会抛出运行时异常
	 * 
	 */
	static Map<String,String> cidFundAccountMap=new LinkedHashMap<String,String>();
	public static void unZip(File srcFile, String destDirPath) throws RuntimeException {
		String client_id=srcFile.getName().split("_")[0];
		long start = System.currentTimeMillis();
		// 判断源文件是否存在
		if (!srcFile.exists()) {
			throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
		}
		// 开始解压
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(srcFile);
			Enumeration<?> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				System.out.println("解压" + entry.getName());
				// 如果是文件夹，就创建个文件夹
				if (entry.isDirectory()) {
					String dirPath = destDirPath + "/" + entry.getName();
					File dir = new File(dirPath);
					dir.mkdirs();
				} else {
					//重命名
					// 如果是文件，就先创建一个文件，然后用io流把内容copy过去
					File targetFile = new File(destDirPath + "/" + cidFundAccountMap.get(client_id)+entry.getName());
					// 保证这个文件的父文件夹必须要存在
					if (!targetFile.getParentFile().exists()) {
						targetFile.getParentFile().mkdirs();
					}
					targetFile.createNewFile();
					// 将压缩文件内容写入到这个文件中
					InputStream is = zipFile.getInputStream(entry);
					FileOutputStream fos = new FileOutputStream(targetFile);
					int len;
					byte[] buf = new byte[1000];
					while ((len = is.read(buf)) != -1) {
						fos.write(buf, 0, len);
					}
					// 关流顺序，先打开的后关闭
					fos.close();
					is.close();
				}
			}
			long end = System.currentTimeMillis();
			System.out.println("解压完成，耗时：" + (end - start) + " ms");
		} catch (Exception e) {
			throw new RuntimeException("unzip error from ZipUtils", e);
		} finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
