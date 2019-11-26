package com.cyb.utils.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

public class FileUtils {
	public void pathInfo() throws IOException {
		@SuppressWarnings("unused")
		int million = 1_000_000;
		Path path = Paths.get("c:\\Temp\\temp");

		System.out.println("Number of Nodes:" + path.getNameCount());

		System.out.println("File Name:" + path.getFileName());

		System.out.println("File Root:" + path.getRoot());

		System.out.println("File Parent:" + path.getParent());

		// 这样写不会抛异常
		Files.deleteIfExists(path);
		
	}
	public static void fileUpload(InputStream is, OutputStream os) throws Exception{
		byte[] b = new byte[1024 * 1024 * 10];
		int length = 0;
		while (true) {
			length = is.read(b);
			if (length < 0)
				break;
			os.write(b, 0, length);
		}
		is.close();
		os.close();
   }
	/**
	 * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
	 */
	public static void readFileByBytes(String fileName) {
		File file = new File(fileName);
		InputStream in = null;
		try {
			System.out.println("以字节为单位读取文件内容，一次读一个字节：");
			// 一次读一个字节
			in = new FileInputStream(file);
			int tempbyte;
			while ((tempbyte = in.read()) != -1) {
				System.out.write(tempbyte);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			System.out.println("以字节为单位读取文件内容，一次读多个字节：");
			// 一次读多个字节
			byte[] tempbytes = new byte[100];
			int byteread = 0;
			in = new FileInputStream(fileName);
			showAvailableBytes(in);
			// 读入多个字节到字节数组中，byteread为一次读入的字节数
			while ((byteread = in.read(tempbytes)) != -1) {
				System.out.write(tempbytes, 0, byteread);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 以字符为单位读取文件，常用于读文本，数字等类型的文件
	 */
	public static void readFileByChars(String fileName) {
		File file = new File(fileName);
		Reader reader = null;
		try {
			System.out.println("以字符为单位读取文件内容，一次读一个字节：");
			// 一次读一个字符
			reader = new InputStreamReader(new FileInputStream(file));
			int tempchar;
			while ((tempchar = reader.read()) != -1) {
				// 对于windows下，\r\n这两个字符在一起时，表示一个换行。
				// 但如果这两个字符分开显示时，会换两次行。
				// 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
				if (((char) tempchar) != '\r') {
					System.out.print((char) tempchar);
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("以字符为单位读取文件内容，一次读多个字节：");
			// 一次读多个字符
			char[] tempchars = new char[30];
			int charread = 0;
			reader = new InputStreamReader(new FileInputStream(fileName));
			// 读入多个字符到字符数组中，charread为一次读取字符数
			while ((charread = reader.read(tempchars)) != -1) {
				// 同样屏蔽掉\r不显示
				if ((charread == tempchars.length) && (tempchars[tempchars.length - 1] != '\r')) {
					System.out.print(tempchars);
				} else {
					for (int i = 0; i < charread; i++) {
						if (tempchars[i] == '\r') {
							continue;
						} else {
							System.out.print(tempchars[i]);
						}
					}
				}
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static StringBuffer readFileByLines(String path) {
		StringBuffer content = new StringBuffer("");
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
			// new FileReader(file)
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				content.append(tempString.trim() + ",");
				// line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return content;
	}

	@SuppressWarnings("unused")
	public static List<String> readFileToList(String path) {
		System.out.println("操作文件路径：" + path);
		List<String> content = new ArrayList<String>();
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				content.add(tempString.trim());
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return content;
	}

	public static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}

	/**
	 * 随机读取文件内容
	 */
	public static void readFileByRandomAccess(String fileName) {
		RandomAccessFile randomFile = null;
		try {
			System.out.println("随机读取一段文件内容：");
			// 打开一个随机访问文件流，按只读方式
			randomFile = new RandomAccessFile(fileName, "r");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 读文件的起始位置
			int beginIndex = (fileLength > 4) ? 4 : 0;
			// 将读文件的开始位置移到beginIndex位置。
			randomFile.seek(beginIndex);
			byte[] bytes = new byte[10];
			int byteread = 0;
			// 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
			// 将一次读取的字节数赋给byteread
			while ((byteread = randomFile.read(bytes)) != -1) {
				System.out.write(bytes, 0, byteread);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 显示输入流中还剩的字节数
	 */
	private static void showAvailableBytes(InputStream in) {
		try {
			System.out.println("当前字节输入流中的字节数为:" + in.available());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void copyFile(String src, String dest) throws IOException {
		try {
			FileInputStream in = new FileInputStream(src);
			File file = new File(dest);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file);

			byte[] buffer = new byte[1024];
			int c;
			while ((c = in.read(buffer)) != -1) {
				for (int i = 0; i < c; i++) {
					out.write(buffer[i]);
				}
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void copyFile(File src, String dest) throws IOException {
		try {
			FileInputStream in = new FileInputStream(src);
			File file = new File(dest);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file);

			byte[] buffer = new byte[1024];
			int c;
			while ((c = in.read(buffer)) != -1) {
				for (int i = 0; i < c; i++) {
					out.write(buffer[i]);
				}
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void copyFileByStream(InputStream src, String dest) {
		try {
			File file = new File(dest);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int len = 0;
			/*
			 * while ((c = src.read(buffer)) != -1) { for (int i = 0; i < c;
			 * i++) out.write(buffer[i]); }
			 */
			while ((len = src.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			src.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void appendString2File(String content, String dest) {
		FileOutputStream fop = null;
		File file;
		try {
			file = new File(dest);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			// 如果第二个参数为true，则将字节写入文件末尾处，而不是写入文件开始处
			fop = new FileOutputStream(file, true);
			if (!file.exists()) {
				file.createNewFile();
			}
			byte[] contentInBytes = content.toString().getBytes();
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void overideString2File(String content, String dest) {
		FileOutputStream fop = null;
		File file;
		// StringBuffer content = new StringBuffer("This is the text content
		// 中文"+"\n");
		// content.append("This is the text content 中文是");
		try {
			file = new File(dest);
			// 如果第二个参数为true，则将字节写入文件末尾处，而不是写入文件开始处
			fop = new FileOutputStream(file, false);
			if (!file.exists()) {
				file.createNewFile();
			} else {
				file.delete();
				file.createNewFile();
			}
			byte[] contentInBytes = content.toString().getBytes();
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
			System.out.println("字符串内容写入到文件完成！" + dest);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void appendString2File1(String content, String to) {
		FileWriter fw = null;
		try {
			// 如果文件存在，则追加内容；如果文件不存在，则创建文件
			File file = new File(to);
			fw = new FileWriter(file, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		pw.println(content);// 写一行
		pw.flush();
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void appendString2File2(String content, String to) {
		BufferedWriter out = null;
		try {
			File file = new File(to);
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
			out.write(content + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void appendString2File3(String content, String to) {
		try {
			// 打开一个随机访问文件流，按读写方式
			RandomAccessFile randomFile = new RandomAccessFile(to, "rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。
			randomFile.seek(fileLength);
			// String s=new String(content.getBytes("gb2312"), "utf-8");//编码转换
			randomFile.writeChars(content + "\n");
			randomFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建文件夹或者文件目录,可以是多级目录
	 * 
	 * @param path
	 */
	public static void genFileDir(String path) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				if (file.isFile()) {
					file.createNewFile();
				} else if (file.isDirectory()) {
					file.mkdirs();
				} else {
					file.mkdirs();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取指定类的同级文件内容
	 * 
	 * @param clazz
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public static String readContentFromCurClsDir(Class<?> clazz, String name) throws IOException {
		InputStream in = clazz.getResourceAsStream(name);
		Reader re = new InputStreamReader(in, "UTF-8");
		char[] chs = new char[1024];
		int count;
		String content = "";
		while ((count = re.read(chs)) != -1) {
			content = content + new String(chs, 0, count);
		}
		return content;
	}

	public static String readContentFromFile(String file) throws IOException {
		InputStream in = new FileInputStream(new File(file));
		@SuppressWarnings("resource")
		Reader re = new InputStreamReader(in, "UTF-8");
		char[] chs = new char[1024 * 10];
		int count;
		String content = "";
		while ((count = re.read(chs)) != -1) {
			content = content + new String(chs, 0, count);
		}
		return content;
	}
	public static String readContentFromFile(File file) throws IOException {
		return readContentFromFile(file.getAbsolutePath());
	}

	public static List<String> readContentFromCurClsDirList(Class<?> clazz, String name) throws IOException {
		InputStream in = clazz.getResourceAsStream(name);
		Reader re = new InputStreamReader(in, "UTF-8");
		List<String> lst = new ArrayList<String>();
		char[] chs = new char[1024];
		int count;
		while ((count = re.read(chs)) != -1) {
			lst.add(new String(chs, 0, count));
			// System.out.println(new String(chs, 0, count));
		}
		return lst;
	}

	public static String getJsonFromNet(String urlStr, String savePath, String charset) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置超时间为3秒
		conn.setConnectTimeout(3 * 1000);
		// 防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		// 得到输入流
		InputStream inputStream = conn.getInputStream();
		// 获取自己数组
		byte[] getData = getContentByCharset(inputStream, charset);
		if (savePath != null) {
			// 文件保存位置
			File file = new File(savePath);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(getData);
			if (fos != null) {
				fos.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		// str = new String(str.getBytes("gbk"),"utf-8");
		return new String(getData);
	}

	public static InputStream getInstreamFromUrl(String urlStr, String savePath, String charset) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置超时间为3秒
		conn.setConnectTimeout(3 * 1000);
		// 防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		// 得到输入流
		InputStream inputStream = conn.getInputStream();
		return inputStream;
	}

	public static byte[] getJsonFromUrl(String urlStr, String savePath, String charset) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置超时间为3秒
		conn.setConnectTimeout(3 * 1000);
		// 防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		// 得到输入流
		InputStream inputStream = conn.getInputStream();
		// 获取自己数组
		byte[] getData = getContentByCharset(inputStream, charset);
		if (savePath != null && !"".equals(savePath)) {
			// 文件保存位置
			File file = new File(savePath);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(getData);
			if (fos != null) {
				fos.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		// str = new String(str.getBytes("gbk"),"utf-8");
		return getData;
	}

	public static byte[] getContentByCharset(InputStream inputStream, String charset) throws IOException {

		Reader re = new InputStreamReader(inputStream, charset);
		char[] chs = new char[1024];
		int count;
		String content = "";
		while ((count = re.read(chs)) != -1) {
			content = content + new String(chs, 0, count);
		}
		return content.getBytes();
	}

	public static String getAbsolutePathAtClass(Class<?> clss) {
		String packagePath = System.getProperty("user.dir");
		String packageName = clss.getPackage().getName().replaceAll("\\.", "/");
		return packagePath + "/src/" + packageName + "/";
	}

	public static String getAbsolutePathAtMavenClass(Class<?> clss) {
		String packagePath = System.getProperty("user.dir");
		String packageName = clss.getPackage().getName().replaceAll("\\.", "/");
		return packagePath + "/src/maven/java/" + packageName + "/";
	}

	public static void main(String[] args) {
		
		/* String to = "d:\\file\\industry.txt"; 
		 appendString2File1("111汉字1", to); 
		 appendString2File2("1112汉字", to); 
		 appendString2File3("1113汉字",to);
		try {
			String str = readContentFromCurClsDir(FileUtils.class, "test.txt");
			System.out.println(str);
			genFileDir("d:/xxx/yyy");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		List<String> ret = new ArrayList<>();
		String dir = "D:\\apache-tomcat-7.0.52\\conf";
		listDirFiles("js",dir,dir,ret);
		System.out.println(ret);
	}

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 获取文件的文件名后缀<br>
	 * 创建时间: 2017年7月15日hj12
	 * 
	 * @param file
	 * @return
	 */

	public static String suffix(File file) {
		String name = file.getName();
		int pos = name.lastIndexOf(".");
		return name.substring(pos);
	}

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 获取文件名后缀<br>
	 * 创建时间: 2017年7月15日hj12
	 * 
	 * @param fileName
	 * @return
	 */
	public static String suffix(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	/**
	 * 左填充
	 * 
	 * @param str
	 * @param length
	 * @param ch
	 * @return
	 */
	public static String leftPad(String str, int length, char ch) {
		if (str.length() >= length) {
			return str;
		}
		char[] chs = new char[length];
		Arrays.fill(chs, ch);
		char[] src = str.toCharArray();
		System.arraycopy(src, 0, chs, length - src.length, src.length);
		return new String(chs);

	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 *            待删除的完整文件名
	 * @return
	 */
	public static boolean delete(String fileName) {
		boolean result = false;
		File f = new File(fileName);
		if (f.exists()) {
			result = f.delete();

		} else {
			result = true;
		}
		return result;
	}

	/***
	 * 递归获取指定目录下的所有的文件
	 * 
	 * @param obj
	 * @return
	 */
	public static ArrayList<File> listFiles(String dirPath) {
		File dir = new File(dirPath);
		ArrayList<File> files = new ArrayList<File>();
		if (dir.isDirectory()) {
			File[] fileArr = dir.listFiles();
			for (int i = 0; i < fileArr.length; i++) {
				File f = fileArr[i];
				if (f.isFile()) {
					files.add(f);
				} else {
					files.addAll(listFiles(f.getPath()));
				}
			}
		}
		return files;
	}

	/**
	 * 获取指定目录下的所有文件(不包括子文件夹)
	 * 
	 * @param dirPath
	 * @return
	 */
	public static ArrayList<File> getDirFiles(String dirPath) {
		File path = new File(dirPath);
		File[] fileArr = path.listFiles();
		ArrayList<File> files = new ArrayList<File>();

		for (File f : fileArr) {
			if (f.isFile()) {
				files.add(f);
			}
		}
		return files;
	}

	/**
	 * 获取指定目录下特定文件后缀名的文件列表(不包括子文件夹)
	 * 
	 * @param dirPath
	 *            目录路径
	 * @param suffix
	 *            文件后缀
	 * @return
	 */
	public static ArrayList<File> getDirFiles(String dirPath, final String suffix) {
		File path = new File(dirPath);
		File[] fileArr = path.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				String lowerName = name.toLowerCase();
				String lowerSuffix = suffix.toLowerCase();
				if (lowerName.endsWith(lowerSuffix)) {
					return true;
				}
				return false;
			}

		});
		ArrayList<File> files = new ArrayList<File>();

		for (File f : fileArr) {
			if (f.isFile()) {
				files.add(f);
			}
		}
		return files;
	}
	
	 /** 
     * 寻找指定目录下，具有指定后缀名的所有文件。 
     * ret存储的是相对目录（减少存储空间），使用时需要加上root 
     * @param filenameSuffix : 文件后缀名 
     * @param currentDirUsed : 当前使用的文件目录 
     * @param currentFilenameList ：当前文件名称的列表 
     */  
    public static void listDirFiles(String suffix, String root ,String baseDir,  
            List<String> ret) {  
        File dir = new File(baseDir);  
        if (!dir.exists() || !dir.isDirectory()) {  
            return;  
        }  
        for (File file : dir.listFiles()) {  
            if (file.isDirectory()) {  
                /** 
                 * 如果目录则递归继续遍历 
                 */  
            	listDirFiles(suffix,root,file.getAbsolutePath(), ret);  
            } else {  
                /** 
                 * 如果不是目录。 
                 * 那么判断文件后缀名是否符合。 
                 */  
                if (file.getAbsolutePath().endsWith(suffix)) {  
                	//System.out.println(file.getAbsolutePath().substring(root.length(), file.getAbsolutePath().length()));
                	ret.add(file.getAbsolutePath().substring(root.length(), file.getAbsolutePath().length())); //省存储，只保存相对路径 .substring(baseDir.length(), file.getAbsolutePath().length()
                }  
            }  
        }  
    }

	/**
	 * 读取文件内容
	 * 
	 * @param fileName
	 *            待读取的完整文件名
	 * @return 文件内容
	 * @throws IOException
	 */
	public static String read(String fileName) throws IOException {
		File f = new File(fileName);
		FileInputStream fs = new FileInputStream(f);
		String result = null;
		byte[] b = new byte[fs.available()];
		fs.read(b);
		fs.close();
		result = new String(b);
		return result;
	}

	/**
	 * 写文件
	 * 
	 * @param fileName
	 *            目标文件名
	 * @param fileContent
	 *            写入的内容
	 * @return
	 * @throws IOException
	 */
	public static boolean write(String fileName, String fileContent) throws IOException {
		boolean result = false;
		File f = new File(fileName);
		FileOutputStream fs = new FileOutputStream(f);
		byte[] b = fileContent.getBytes();
		fs.write(b);
		fs.flush();
		fs.close();
		result = true;
		return result;
	}

	/**
	 * 追加内容到指定文件
	 * 
	 * @param fileName
	 * @param fileContent
	 * @return
	 * @throws IOException
	 */
	public static boolean append(String fileName, String fileContent) throws IOException {
		boolean result = false;
		File f = new File(fileName);
		if (f.exists()) {
			RandomAccessFile rFile = new RandomAccessFile(f, "rw");
			byte[] b = fileContent.getBytes();
			long originLen = f.length();
			rFile.setLength(originLen + b.length);
			rFile.seek(originLen);
			rFile.write(b);
			rFile.close();
		}
		result = true;
		return result;
	}

	/**
	 * 拆分文件
	 * 
	 * @param fileName
	 *            待拆分的完整文件名
	 * @param byteSize
	 *            按多少字节大小拆分
	 * @return 拆分后的文件名列表
	 * @throws IOException
	 */
	public List<String> splitBySize(String fileName, int byteSize) throws IOException {
		List<String> parts = new ArrayList<String>();
		File file = new File(fileName);
		int count = (int) Math.ceil(file.length() / (double) byteSize);
		int countLen = (count + "").length();
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(count, count * 3, 1, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(count * 2));

		for (int i = 0; i < count; i++) {
			String partFileName = "d://data//tmp/" + file.getName() + "." + leftPad((i + 1) + "", countLen, '0')
					+ ".part";
			threadPool.execute(new SplitRunnable(byteSize, i * byteSize, partFileName, file));
			parts.add(partFileName);
			System.out.println(partFileName);
		}
		return parts;
	}

	/**
	 * 合并文件
	 * 
	 * @param dirPath
	 *            拆分文件所在目录名
	 * @param partFileSuffix
	 *            拆分文件后缀名
	 * @param partFileSize
	 *            拆分文件的字节数大小
	 * @param mergeFileName
	 *            合并后的文件名
	 * @throws IOException
	 */
	public void mergePartFiles(String dirPath, String partFileSuffix, int partFileSize, String mergeFileName)
			throws IOException {
		ArrayList<File> partFiles = getDirFiles(dirPath, partFileSuffix);
		Collections.sort(partFiles, new FileComparator());

		RandomAccessFile randomAccessFile = new RandomAccessFile(mergeFileName, "rw");
		randomAccessFile
				.setLength(partFileSize * (partFiles.size() - 1) + partFiles.get(partFiles.size() - 1).length());
		randomAccessFile.close();

		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(partFiles.size(), partFiles.size() * 3, 1,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(partFiles.size() * 2));
		for (int i = 0; i < partFiles.size(); i++) {
			threadPool.execute(new MergeRunnable(i * partFileSize, mergeFileName, partFiles.get(i)));
		}
		/*
		 * System.out.println(partFiles.size()); for (; x.getAndIncrement() <
		 * partFiles.size(); ) { System.out.println(x.get());
		 * threadPool.execute(new MergeRunnable(x.get()-1 * partFileSize,
		 * mergeFileName, partFiles.get(x.get()-1))); }
		 */

	}

	/**
	 * 根据文件名，比较文件
	 * 
	 * @author yjmyzz@126.com
	 *
	 */
	private class FileComparator implements Comparator<File> {
		@Override
		public int compare(File o1, File o2) {
			return o1.getName().compareToIgnoreCase(o2.getName());
		}
	}

	/**
	 * 分割处理Runnable
	 * 
	 * @author yjmyzz@126.com
	 *
	 */
	private class SplitRunnable implements Runnable {
		int byteSize;
		String partFileName;
		File originFile;
		int startPos;

		public SplitRunnable(int byteSize, int startPos, String partFileName, File originFile) {
			this.startPos = startPos;
			this.byteSize = byteSize;
			this.partFileName = partFileName;
			this.originFile = originFile;
		}

		@Override
		public void run() {
			RandomAccessFile rFile;
			OutputStream os;
			try {
				rFile = new RandomAccessFile(originFile, "r");
				byte[] b = new byte[byteSize];
				rFile.seek(startPos);// 移动指针到每“段”开头
				int s = rFile.read(b);
				os = new FileOutputStream(partFileName);
				os.write(b, 0, s);
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 合并处理Runnable
	 * 
	 * @author yjmyzz@126.com
	 *
	 */
	private class MergeRunnable implements Runnable {
		long startPos;
		String mergeFileName;
		File partFile;

		public MergeRunnable(long startPos, String mergeFileName, File partFile) {
			this.startPos = startPos;
			this.mergeFileName = mergeFileName;
			this.partFile = partFile;
		}

		@Override
		public void run() {
			RandomAccessFile rFile;
			try {
				rFile = new RandomAccessFile(mergeFileName, "rw");
				rFile.seek(startPos);
				FileInputStream fs = new FileInputStream(partFile);
				byte[] b = new byte[fs.available()];
				fs.read(b);
				fs.close();
				rFile.write(b);
				rFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日
	 *@param fileName classpath:/a/b/c.txt
	 * @throws FileNotFoundException 
	 */
	public static File classPathFile(String fileName) throws FileNotFoundException{
		return ResourceUtils.getFile(fileName);
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日
	 *@param fileName /a/b/c.txt
	 *@return
	 *@throws IOException
	 */
	public static File classPathFile2(String fileName) throws IOException{
		return new ClassPathResource(fileName).getFile();
	}
	
	/**
	 * 
	 * @param dir 自定目录
	 * @param res 返回结果
	 * @return
	 * @throws IOException 
	 */
	public List<String> regexFileByDir(String dir,List<String> res) throws IOException{
		//获取dir下的所有文件
		List<File> files = getDirFiles(dir);
		for(File f:files){
			String content = readContentFromFile(f);
	    }
		return null;
	}
}