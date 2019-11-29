package com.cyb.utils.csv;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cyb.utils.office.bean.Column;
import com.cyb.utils.office.bean.TablesInfor;

public class File_CSV {
	public static void main(String[] args) throws IOException {
		List<String[]> data= getTestData("d:/data/csv/tables.csv");
		System.out.println(data);
		Map<String,TablesInfor> tables = new HashMap<String, TablesInfor>();
		for(int i=0;i<data.size();i++){
			if(i==0) continue;
			try {
				String tableName = data.get(i)[1];
				if(!tables.containsKey(tableName)){
					TablesInfor table = new TablesInfor();
					List<Column> cols = new ArrayList<>();
					table.setCols(cols);
					tables.put(tableName, table);
				}
				Column col = new Column();
				col.setColName(data.get(i)[2]);
				col.setColType(data.get(i)[3]);
				col.setIsEmpty("");
				try {
					col.setColDesc(data.get(i)[4]);
				} catch (Exception e) {
				}
				tables.get(tableName).getCols().add(col);
			} catch (Exception e) {
				System.err.println(e.toString()+"/"+data.get(i));
			}
		}
		System.out.println(tables);
	}
	
	
	
	
	  // 读取CSV文件的静态方法，使用CSV文件的绝对文件路径作为函数参数
    public static List<String[]> getTestData(String fileName) throws IOException {
        List<String[]> records = new ArrayList<String[]>();
        String record;
        // 设定UTF-8字符集，使用带缓冲区的字符输入流BufferedReader读取文件内容
        BufferedReader file = new BufferedReader(
        		new InputStreamReader(new FileInputStream(fileName),
        				"gbk"));//UTF-8
        // file.readLine(); //跳过表头所在的行

        // 遍历数据行并存储在名为records的ArrayList中，每一行records中存储的对象为一个String数组
        while ((record = file.readLine()) != null) {
            try {
				String fields[] = record.split("	");
				//System.out.println(fields[1]);
				records.add(fields);
			} catch (Exception e) {
				//e.printStackTrace();
			}
        }
        // 关闭文件
        file.close();
        return records;
    }
    public static Object[][] ret(List<String[]> records){
    	 // 将存储测试数据的List转换为一个Object的二维数组
        Object[][] results = new Object[records.size()][];
        // 设置二位数组每行的值，每行是一个Object对象
        for (int i = 0; i < records.size(); i++) {
            results[i] = (Object[]) records.get(i);
        }
        return results;
    }
}
