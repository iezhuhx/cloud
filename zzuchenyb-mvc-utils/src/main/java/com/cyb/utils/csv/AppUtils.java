package com.cyb.utils.csv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cyb.utils.office.bean.Column;
import com.cyb.utils.office.bean.TablesInfor;

public class AppUtils {
	static String CSV_PATH ="d:/data/csv/";
	static String tablesFile ="d:/data/csv/tables.csv";
	static String tablesFile1 ="d:/data/csv/tables1.csv";
	
	
	public static Map<String, TablesInfor> getJsonTables(){
		Map<String, TablesInfor> tables = new HashMap<String, TablesInfor>();
		TablesInfor table = new TablesInfor();
		List<Column> cols = new ArrayList<>();
		Column col = new Column();
		col.setColName("列明1");
		col.setColType("varchar2");
		col.setIsEmpty("0");
		col.setColLength("0");
		col.setColDesc("描述不能为空");
		cols.add(col);
		table.setCols(cols);
		table.setIdx("1");
		table.setTableDesc("描述");
		table.setTableName("表名name");
		tables.put(table.getTableName(), table);
		return tables;
		
	}
	
	public static Map<String, TablesInfor> getBlankTables() throws IOException {
		List<String[]> data = File_CSV.getTestData(tablesFile);
		Map<String, TablesInfor> tables = new HashMap<String, TablesInfor>();
		for (int i = 0; i < data.size(); i++) {
			if (i == 0)
				continue;
			try {
				String tableName = data.get(i)[1];
				if (!tables.containsKey(tableName)) {
					TablesInfor table = new TablesInfor();
					List<Column> cols = new ArrayList<>();
					table.setCols(cols);
					table.setTableName(tableName);
					table.setTableDesc("");
					table.setIdx((tables.size()+1)+"");
					tables.put(tableName, table);
				}
				Column col = new Column();
				col.setColName(data.get(i)[2]);
				col.setColType(data.get(i)[3]);
				col.setIsEmpty("");
				col.setColLength("0");
				try {
					col.setColDesc(data.get(i)[4]);
				} catch (Exception e) {
					col.setColDesc("");
				}
				tables.get(tableName).getCols().add(col);
			} catch (Exception e) {
				System.err.println(e.toString() + "/" + data.get(i));
			}

		}
		return tables;
	}
	
	public static Map<String, TablesInfor> getSplitTables() throws IOException {
		List<String[]> data = File_CSV.getTestData(tablesFile);
		Map<String, TablesInfor> tables = new HashMap<String, TablesInfor>();
		for (int i = 0; i < data.size(); i++) {
			if (i == 0)
				continue;
			try {
				String tableName = data.get(i)[1];
				if (!tables.containsKey(tableName)) {
					TablesInfor table = new TablesInfor();
					List<Column> cols = new ArrayList<>();
					table.setCols(cols);
					table.setTableName(tableName);
					table.setTableDesc("");
					table.setIdx((tables.size()+1)+"");
					tables.put(tableName, table);
				}
				Column col = new Column();
				col.setColName(data.get(i)[2]);
				col.setColType(data.get(i)[3]);
				col.setIsEmpty("");
				col.setColLength("0");
				try {
					col.setColDesc(data.get(i)[4]);
				} catch (Exception e) {
					col.setColDesc("");
				}
				tables.get(tableName).getCols().add(col);
			} catch (Exception e) {
				System.err.println(e.toString() + "/" + data.get(i));
			}

		}
		return tables;
	}
	
	public static void subTable() throws IOException{
		List<String[]> allseq = File_CSV.getTestData(CSV_PATH+"allseq.csv");
		List<String[]> haveseq = File_CSV.getTestData(CSV_PATH+"haveseq.csv");
		List<String[]> all = File_CSV.getTestData(CSV_PATH+"all.csv");
		List<String[]> allhave = File_CSV.getTestData(CSV_PATH+"allhave.csv");
		List<String[]> newk = File_CSV.getTestData(CSV_PATH+"newkiiik.csv");
		
		List<String> haveseqList=new ArrayList<>();
		for(String[] s:haveseq){
			haveseqList.add(s[0]);
		}
		StringBuffer sb = new StringBuffer("'");
		for(String[] s:allseq){
			if(!haveseqList.contains(s[0])){
				System.out.println(s[0]);
				sb.append(s[0]+"','");
			}
		}
		System.out.println(sb.toString());
		
		
		/*List<String> newList=new ArrayList<>();
		for(String[] s:newk){
			newList.add(s[0]);
		}
		List<String> allList=new ArrayList<>();
		for(String[] s:all){
			allList.add(s[0]);
		}
		
		List<String> allHaveList=new ArrayList<>();
		for(String[] s:allhave){
			allHaveList.add(s[0]);
		}
		System.out.println(allList.size()+","+allHaveList.size());
		StringBuffer sb = new StringBuffer("'");
		for(String s:allList){
			if(!allHaveList.contains(s)){
				System.out.println(s);
				sb.append(s).append("','");
			}
		}*/
		//System.out.println(sb.toString());
		
		//all not contain allhave
		
		
		
		/*System.out.println(newList);
		StringBuffer sb = new StringBuffer("'");
		StringBuffer alls = new StringBuffer("'");
		for(String[] s:all){
			alls.append(s[0]).append("','");
			if(!newList.contains(s[0])){
				sb.append(s[0]).append("','");
			}
		}
		System.out.println(alls.toString());
		System.out.println(sb.toString());*/
	}
	public static void main(String[] args) throws IOException {
		subTable();
	}
	
}
