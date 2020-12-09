package com.cyb.utils.dbf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;

/**
 * @Author iechenyb<br>
 * @Desc 类描述<br>
 * @CreateTime 2020年12月9日 上午11:59:23
 */
public class DbfTest {
	Log log = LogFactory.getLog(DbfTest.class);

	public static void simpeChinease() throws DBFException, FileNotFoundException {
		File dbf = new File("d:/data/tf/test.dbf");
		if (dbf.exists()) {			dbf.delete();		}

		// 定义DBF文件字段 ,必须是英文列头
		DBFField[] fields = new DBFField[3];
		fields[0] = new DBFField();
		fields[0].setName("Grade"); // name必须是ascii
		fields[0].setDataType(DBFField.FIELD_TYPE_C);
		fields[0].setFieldLength(20);
		fields[1] = new DBFField();
		fields[1].setName("Name");
		fields[1].setDataType(DBFField.FIELD_TYPE_C);
		fields[1].setFieldLength(50);
		fields[2] = new DBFField();
		fields[2].setName("Score");
		fields[2].setDataType(DBFField.FIELD_TYPE_C);
		fields[2].setFieldLength(20);
		
		DBFWriter writer = null;// = new DBFWriter(f);
		writer = new DBFWriter();
		writer.setCharactersetName("GBK");
		writer.setFields(fields);

		Object[] rowData = new Object[3];
		rowData[0] = "1";
		rowData[1] = "chenyb";
		rowData[2] = "60";
		writer.addRecord(rowData);
		rowData = new Object[3];
		rowData[0] = "2";
		rowData[1] = URLEncoder.encode("张三");
		rowData[2] = "70";
		writer.addRecord(rowData);
		rowData = new Object[3];
		rowData[0] = "3";
		rowData[1] = "董存瑞";
		rowData[2] = "70";
		writer.addRecord(rowData);// 只是新增 并没有写到文件！
		//writer.write(new ByteArrayOutputStream());
		//ByteArrayOutputStream baos = new ByteArrayOutputStream();
		//writer.write(new ByteArrayOutputStream());// 一次写出文件new FileOutputStream(dbf)
		writer.write(new FileOutputStream(dbf));

	}

	public static void main(String[] args) throws FileNotFoundException {
		try {
			simpeChinease();
		} catch (DBFException e) {
			e.printStackTrace();
		}
	}
}
