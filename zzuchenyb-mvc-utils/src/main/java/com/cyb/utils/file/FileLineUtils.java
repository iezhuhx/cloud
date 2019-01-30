package com.cyb.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.cyb.app.seo.WebHrefUtils;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2019年1月29日
 */
public class FileLineUtils {
	static Log log = LogFactory.getLog(FileLineUtils.class);

	public static long getFileLineNumber(String file) throws IOException {
		return fileLineNumber(new File(file));
	}

	public static List<String> readForPage(String sourceFile, long start, long end) throws IOException {
		return readForPage(new File(sourceFile), start, end);
	}

	public static List<String> readForPage(File sourceFile, long start, long end) throws IOException {
		FileReader in = new FileReader(sourceFile);
		LineNumberReader reader = new LineNumberReader(in);
		String s = "";
		List<String> data = new ArrayList<>();
		long lines = start;
		System.out.println(start + "->" + end);
		while (lines <= end) {
			s = reader.readLine();
			if (!StringUtils.isEmpty(s)) {
				data.add(s);
			}
			System.out.println(lines + ":" + s);
			lines++;
			reader.getLineNumber();
			reader.setLineNumber((int) lines);
		}
		reader.close();
		in.close();
		return data;
	}

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 读取文件行数<br>
	 * 创建时间: 2017年7月15日
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static long fileLineNumber(File file) throws IOException {
		LineNumberReader lnr = new LineNumberReader(new FileReader(file));
		lnr.skip(Long.MAX_VALUE);
		int lineNo = lnr.getLineNumber() + 1;
		lnr.close();
		return lineNo;
	}
	public static List<String> dataReader(String nameFile) throws IOException{
		return dataReader(nameFile,1,fileLineNumber(new File(nameFile)));
	}
	public static List<String> dataReader(File nameFile, long start, long finish) throws IOException{
		return dataReader(nameFile.getAbsolutePath(),start,finish);
	}
	@SuppressWarnings("resource")
	public static List<String> dataReader(String nameFile, long start, long finish) {
		List<String> data = new ArrayList<>();
		if (start > finish) {
			System.out.println("Error start or finish!");
			return data;
		}
		InputStream inputStream = null;
		LineNumberReader reader = null;
		try {
			inputStream = new FileInputStream(new File(nameFile));
			reader = new LineNumberReader(new InputStreamReader(inputStream));
			long lines = fileLineNumber(new File(nameFile));
			if (start < 0 || finish < 0 || finish > lines || start > lines) {
				log.debug("Line not found!");
				return data;
			}
			log.debug(start + "->" + finish);
			String line = reader.readLine();
			lines = 1;
			while (lines <= finish) {
				// 每一次都是全部遍历，只是读取指定分页内容
				if (lines >= start) {
					//System.out.println(line);
					data.add(line);
				}
				lines++;
				line = reader.readLine();
			}
			inputStream.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {	
			System.err.println("IO Error");
			System.exit(0);
		}
		return data;

	}

	public static void main(String[] args) throws IOException {
		/*long total = getFileLineNumber(HolidayUtils.rqs);
		Pagination page = new Pagination(1, 5, total);
		for (int i = 1; i <= page.getPageCount(); i++) {
			Pagination pageCur = page.getPage(i);
			dataReader(HolidayUtils.rqs, pageCur.getPageStart() + 1, pageCur.getPageEnd() + 1);
			System.out.println("================");
		}
		dataReader(HolidayUtils.rqs, 2, 4);
		System.out.println("================");
		dataReader(HolidayUtils.rqs, 3, 8);*/
		List<String> news = FileLineUtils.dataReader(WebHrefUtils.newsFile);
		System.out.println(news);
	}
}
