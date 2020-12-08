package com.cyb.app.bms.billdoc;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFWriter;
/**
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年12月8日 上午10:56:05
 */
public class BillSpiltor {
	Log log = LogFactory.getLog(BillSpiltor.class);
	public String basePath="d://data/tf/";
	public String uploadFileName="";
	
	public static void main(String[] args) {
		new BillSpiltor().dbfClassification();
	}
	public void dbfClassification() {
			String message = "{\"message\":'success'}";
		  try{
			 // String path = basePath+"CTPDataUpload";
			 /* DiskFileItemFactory factory = new DiskFileItemFactory();
			  
			 
			  String fileName = "";
			
			  ServletFileUpload upload = new ServletFileUpload(factory);
			  upload.setHeaderEncoding("UTF-8");
			  //清空目标目录下的文件
			  deleteAnyone(path);
			//保存zip文件
			List<FileItem> parametersList = upload.parseRequest(request);
			
			if (parametersList != null && parametersList.size()>0){
				//System.out.println("ok");
				for (FileItem item : parametersList){
					if (item != null && item.getFieldName() != null && item.getName() != null){
						 String name = item.getFieldName();
						 String value = item.getName();  
						 if (value.lastIndexOf("zip") > 0){
							 if (name.equals("filename1")){
								fileName = value;  //提取文件名称
								int index = fileName.lastIndexOf(File.separatorChar);
								if (index >0){
									fileName = fileName.substring(index+1);
								}
								if (fileName != null && !fileName.equals("")){
									createDir(path);
									item.write(new File(path, fileName));
								}
								//System.out.println("filename"+fileName);
							  }
						 }else{
							message = "{\"message\":'wrongFileFormat'}";
							OutPutObject.printjson(response,message);
							return null;
						 }
					}
				}
			}*///解析request中的文件 上传到指定的目录
						
			//解压zip文件	
			//System.out.println(path+File.separatorChar+uploadFileName);
			//unzip(path+File.separatorChar+uploadFileName,path+File.separatorChar+"total");//手动解压即可。
			
			//删除上传  zip文件
			//deleteFile(path+File.separatorChar+uploadFileName);//无需删除
			
			//生成dbf文件
			String account="";
			String path = basePath;
			System.out.println(account);
			String sourcePath=path+File.separatorChar+"total";
			String targetPath = path;
			File dir = new File(sourcePath);
			
			File[] fs = dir.listFiles();
			for(int i=0; i<fs.length; i++){
				String tmpSource = "";
				String tmptarget = "";
				String a=fs[i].getCanonicalPath();
				System.out.print(a.contains("DBF"));
				if(a.contains("DBF"))
					continue;
				account=a.substring(a.length()-7, a.length());
				if (account != null && !account.equals("")){
					tmpSource = sourcePath + File.separatorChar + account;
					tmptarget = targetPath+File.separatorChar+"dbf"+File.separatorChar+account+"-DBF";
					File targetFile = new File(tmptarget);
					targetFile.mkdirs();
				}
				//System.out.println("account:"+account);
				
				File files = new File(tmpSource);
				String[] filelist = files.list();
				//System.out.println(filelist[0]);
				String time = filelist[0].trim().substring(filelist[0].length()-12, filelist[0].length()-4);
				//System.out.println("time:"+time);
				
				String tempath1 = tmptarget + File.separatorChar + "0027_SG01_" + time + "_1_Capital.dbf";
				writeDBFCapital(tmpSource,tempath1,account,time);
				
				tempath1 = tmptarget + File.separatorChar + "0027_SG01_" + time + "_1_Delivery.dbf";
				writeDBFDelivery(tmpSource,tempath1,account,time);
				
				tempath1 = tmptarget + File.separatorChar + "0027_SG01_" + time + "_1_Trade.dbf";
				writeDBFTrade(tmpSource,tempath1,account,time);

				tempath1 = tmptarget + File.separatorChar + "0027_SG01_" + time + "_1_SettlementDetail.dbf";
				writeDBFSettlementDetail(tmpSource,tempath1,account,time);
				
				tempath1 = tmptarget + File.separatorChar + "0027_SG01_" + time + "_1_ClientCapitalDetail.dbf";
				writeDBFClientCapitalDetail(tmpSource,sourcePath,tempath1,account,time);
			  }
			
			//压缩生成的dbf文件
			String inputFileName = path+File.separatorChar+"dbf";	//你要压缩的文件夹
			String zipFileName = path+File.separatorChar+"dbf.zip";	//压缩后的zip文件（需要下载的文件）
			//zip(inputFileName, zipFileName);//手动压缩
			
			
			//下载分类后的压缩文件
			//String dirPath = path;
			String downfilename = "dbf.zip";
			//File file = new File(path+File.separatorChar+"dbf.zip");
			// 以流的形式下载文件。
			//InputStream fis = new BufferedInputStream(new FileInputStream(path+File.separatorChar+"dbf.zip"));
		  }catch (Exception ex) {
				ex.printStackTrace();
				message = "{\"message\":'error'}";
			}
		}
	
	//获取sourcepath的第n+1个@前的字段值
	public String getValue(String sourcepath,int n) throws Exception{
		File file = new File(sourcepath);
		String value="";
		double tmp=0;
		DecimalFormat df = new DecimalFormat( "0.00"); 
		if(file.isFile() && file.exists()){
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				String [] strS  = line.split("@");
				value=strS[n];	
			}
			br.close();
		}
		if("".equals(value) || ""==value){
			return "0.00";
		}else{
			tmp = Double.parseDouble(value);
			value = df.format(tmp);
		}
		return  value;
	}
	
	//
	public String getstr(String sourcepath,int n) throws Exception{
		File file = new File(sourcepath);
		String value=""; 
		if(file.isFile() && file.exists()){
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				String [] strS  = line.split("@");
				value=strS[n];	
			}
			br.close();
		}
		if("".equals(value) || ""==value){
			return "0.00";
		}
		return  value;
	}
	
	//获取sourcepath的第n+1个@前的字段值的和
	public String getTotal(String sourcefile, int n) throws Exception{
		File file = new File(sourcefile);
		String str="";
		double total=0.00;
		double temp=0.00;
		if(file.isFile() && file.exists()){
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = br.readLine()) != null){
				String [] strS = line.split("@");
				str=strS[n];
				temp = Double.parseDouble(str);
				total += temp;
			}
			str = String.format("%.2f", total);
			return str;
		}
		return "0.00";
	}
	
	//读取txt文件
	public void readTxtFile(File sourcefile, File targetfile, String account) throws Exception {
		  InputStreamReader ir = new InputStreamReader(new FileInputStream(sourcefile),"UTF-8");
		  BufferedReader br = new BufferedReader(ir);
		  String [] strAccount = account.split(",");
		  int accountNum = strAccount.length;
		  String filename = sourcefile.getName();
		  
		  String tmpFileName = "";
		  String [] fileNames = new String[accountNum];
		  for(int i=0; i<accountNum; i++){
              tmpFileName = filename.replace("0027", strAccount[i]);//循环创建文件
			  fileNames[i] = targetfile + "//" + strAccount[i]+ File.separatorChar + tmpFileName;
			  File f = new File(fileNames[i]);
			  if (!f.exists()) {
				  f.createNewFile();
			  }
		  }
		  
		String line = null;
		while ((line = br.readLine()) != null) {
            if (StringUtils.isEmpty(line)) {
                continue;
            }
			String [] strS  = line.split("@");
			String fileName =filename.substring(4);
            fileName = fileName.substring(0, fileName.length() - 12);
            if (JCPZConstants.ACC_LOCATION.containsKey(fileName)) {//按照指定规则
				Integer idx=JCPZConstants.ACC_LOCATION.get(fileName);
				String curFd = strS[idx-1];
                for (int m = 0; m < accountNum; m++) {//每个account进行一次筛查，从当前源文件和目标文件中。
                    if (strAccount[m].equals(curFd)) {
                        String saveFileName = targetfile + "//" + strAccount[m] + File.separatorChar + filename.replace("0027", strAccount[m]);
                        FileWriter tmpFile = new FileWriter(saveFileName, true);
                        tmpFile.write(line);
                        tmpFile.write("\r\n");
                        tmpFile.close();
                        break;
                    }
				}
			}else{
                for (int i = 0; i < strS.length; i++) {//遍历数据项@分割
                    for (int j = 0; j < accountNum; j++) {//资金账号比对与每一列
						if(strAccount[j].equals(strS[i])){
                            FileWriter tmpFile = new FileWriter(fileNames[j], true);//按照数组有序对应
							tmpFile.write(line);
							tmpFile.write("\r\n");
							tmpFile.close();
							break;
						}
					  }
				  }
			}
		}
		br.close();		
	}
	

	/*private void writeDBFCapital1(String sourcepath,String targetFile,String account,String time)
	{
	    try {  
	        File f=new File(targetFile);
	        if(!f.exists())
	        {
	        	// 定义DBF文件字段  
	            DBFField[] fields = new DBFField[3];  
	            fields[0] = new DBFField();  
	            fields[0].setName("accountID");  
	            fields[0].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[0].setFieldLength(20);  
	            fields[1] = new DBFField();  
	            fields[1].setName("itemDesc");  
	            fields[1].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[1].setFieldLength(50);  
	            fields[2] = new DBFField();  
	            fields[2].setName("itemValue");  
	            fields[2].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[2].setFieldLength(20);
	            
	        	DBFWriter writer = new DBFWriter(f);
	            writer.setFields(fields); 
	            
	            // 一条条的写入记录 
	            String temp="";
	            String file="";
	            int n=0;
	            
	            file=sourcepath+File.separatorChar+account+"cusfund"+time+".txt";
	            n=6;
	            temp=getValue(file,n);
	            Object[] rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "上一交易日实有货币资金余额";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData);  
	            
	            file=sourcepath+File.separatorChar+account+"fundchg"+time+".txt";
	            n= 2;
	            temp=getValue(file,n);
	            System.out.println(temp);
	            //System.out.println(temp.substring(0,1));
	            if(!"+".equals(temp.substring(0, 1)) && "+"!=temp.substring(0, 1)){
		            temp="0.00";
	            }
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "加：当日收入资金";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData);  
	            
	            file=sourcepath+File.separatorChar+account+"cusfund"+time+".txt";
	            n= 10;
	            temp=getValue(file,n);
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "当日盈亏";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData); 
	            
	            file=sourcepath+File.separatorChar+account+"cusfund"+time+".txt";
	            n= 2;
	            temp=getValue(file,n);
	            if(!"-".equals(temp.substring(0, 1)) && "-"!=temp.substring(0, 1)){
	            	temp="0.00";
	            }
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "减：当日付出资金";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData); 
	            
	            file=sourcepath+File.separatorChar+account+"otherfund"+time+".txt";
	            n= 3;
	            temp=getValue(file,n);
	            if(temp=="A001" && "A001".equals(temp))
	            {
	            	temp = getValue(file,4);
	            }else{
	            	temp = "0.00";
	            }
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "手续费";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData); 
	            
	            temp = "-";
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "其中：交易手续费";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData); 
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "结算手续费";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData); 
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "交割手续费";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData); 
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "移仓手续费";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData); 
	            
	            file=sourcepath+File.separatorChar+account+"cusfund"+time+".txt";
	            n= 8;
	            temp=getValue(file,n);
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "当日实有货币资金余额";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData); 
	            
	            file=sourcepath+File.separatorChar+account+"holddata"+time+".txt";
	            n= 6;
	            temp=getTotal(file,n);
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "其中：交易保证金";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData);
	            
	            file=sourcepath+File.separatorChar+account+"cusfund"+time+".txt";
	            n= 3;
	            temp=getValue(file,n);
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "结算准备金";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData); 
	            
	            file=sourcepath+File.separatorChar+account+"holddata"+time+".txt";
	            n= 6;
	            temp=getTotal(file,n);
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "减：交易保证金";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData);
	            
	            file=sourcepath+File.separatorChar+account+"cusfund"+time+".txt";
	            n= 3;
	            temp=getValue(file,n);
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "当日结算准备金余额";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData);
	            
	            temp = "-";
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "加：申报划入金额";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData);
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "减：申报划出金额";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData);
	            
	            file=sourcepath+File.separatorChar+account+"cusfund"+time+".txt";
	            n= 3;
	            temp=getValue(file,n);
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "下一交易日开仓准备金";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData);
	            
	            temp="-";
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "其它";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData);
	            
	            file=sourcepath+File.separatorChar+account+"otherfund"+time+".txt";
	            n= 3;
	            temp=getValue(file,n);
	            if(temp=="A001" && "A001".equals(temp))
	            {
	            	temp = getValue(file,4);
	            }else{
	            	temp = "0.00";
	            }
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "应收手续费";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData);
	            
	            temp = "-";
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "实有货币资金变动";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData);
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "其中：交易保证金变动";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData);
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "结算准备金变动";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData);
	            
	            // 写入数据  
	            writer.write();
	            
	        }else{
	        	System.out.println("文件已存在");
	        }   
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	} */
	
	private void writeDBFCapital(String sourcepath,String targetFile,String account,String time)
	{
	    try {  
	        File f=new File(targetFile);
	        if(!f.exists())
	        {
	        	// 定义DBF文件字段  
	            DBFField[] fields = new DBFField[3];  
	            fields[0] = new DBFField();  
	            fields[0].setName("accountID");  
	            fields[0].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[0].setFieldLength(20);  
	            fields[1] = new DBFField();  
	            fields[1].setName("itemDesc");  
	            fields[1].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[1].setFieldLength(50);  
	            fields[2] = new DBFField();  
	            fields[2].setName("itemValue");  
	            fields[2].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[2].setFieldLength(20);
	            
	        	DBFWriter writer = new DBFWriter(f);
	        	writer.setCharactersetName("GBK");
	            writer.setFields(fields); 
	            
	            
	            String temp="-";
	            String file="";
	            int n=0;
	            String cusfund4="";
	            String cusfund7="";
	            String cusfund9="";
	            String cusfund11="";
	            String fundchgPlus3="";
	            String fundchgNeg3="";
	            double otherfund45=0.00;
	            String holddataTotal7="";
	            
	            
	            Long befTime = Long.parseLong(time);
	            befTime = befTime -1;
	            String strTime=befTime.toString();
	            
	            
	            file=sourcepath+File.separatorChar+account+"cusfund"+time+".txt";
	            n= 3;
	            cusfund4=getValue(file,n);
	            n=6;
	            cusfund7=getValue(file,n);
	            n= 8;
	            cusfund9=getValue(file,n);
	            n= 10;
	            cusfund11=getValue(file,n);
	            
	            file=sourcepath+File.separatorChar+account+"fundchg"+time+".txt";
	            n= 2;
	            String fundchg3=getValue(file,n);
	            //fundchgPlus3=;
	            //fundchgNeg3=fundchgPlus3;
	            //System.out.println(fundchg3);
	            if(!"-".equals(fundchg3.substring(0, 1)) || "-"!=fundchg3.substring(0, 1)){
	            	fundchgNeg3="0.00";
	            	fundchgPlus3=fundchg3;
	            	//System.out.println("fundchgNeg3=0.00");
	            }else{
	            	fundchgNeg3=fundchg3;
	            	fundchgPlus3="0.00";
	            }
	            
	            file=sourcepath+File.separatorChar+account+"otherfund"+time+".txt";    
	            otherfund45=0.00;
	    		BufferedReader br = new BufferedReader(new FileReader(file));
	    		String line = null;
	    		while ((line = br.readLine()) != null) {
	    			System.out.println(line);
	    			String [] strS  = line.split("@");
	    			if(strS[3]=="A001" || "A001".equals(strS[3]))
	    			{
	    				otherfund45 += Double.parseDouble(strS[4]);
	    			}
	    			if(strS[3]=="A003" || "A003".equals(strS[3]))
	    			{
	    				otherfund45 += Double.parseDouble(strS[4]);
	    			}
	    			if(strS[3]=="A017" || "A017".equals(strS[3]))
	    			{
	    				otherfund45 += Double.parseDouble(strS[4]);
	    			}
	    		}
	    		if( otherfund45<0.00 ){
	    			otherfund45 = -otherfund45;
	    		}
	    		br.close();
	    		
	            
	            file=sourcepath+File.separatorChar+account+"holddata"+time+".txt";
	            n= 6;
	            holddataTotal7=getTotal(file,n);
	            
	            // 一条条的写入记录 
	            Object[] rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "上一交易日实有货币资金余额";  
	            rowData[2] = cusfund7;  
	            writer.addRecord(rowData);  
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "加：当日收入资金";  
	            rowData[2] = fundchgPlus3;  
	            writer.addRecord(rowData);  
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "当日盈亏";  
	            rowData[2] = cusfund11;  
	            writer.addRecord(rowData); 
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "减：当日付出资金";  
	            rowData[2] = fundchgNeg3;  
	            writer.addRecord(rowData); 
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "手续费";  
	            rowData[2] = String.format("%.2f", otherfund45); 
	            writer.addRecord(rowData); 
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "其中：交易手续费";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData); 
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "结算手续费";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData); 
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "交割手续费";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData); 
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "移仓手续费";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData); 
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "当日实有货币资金余额";  
	            rowData[2] = cusfund9;  
	            writer.addRecord(rowData); 
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "其中：交易保证金";  
	            rowData[2] = holddataTotal7;  
	            writer.addRecord(rowData);
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "结算准备金";  
	            rowData[2] = cusfund4;  
	            writer.addRecord(rowData); 
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "减：交易保证金";  
	            rowData[2] = holddataTotal7;  
	            writer.addRecord(rowData);
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "当日结算准备金余额";  
	            rowData[2] = cusfund4;  
	            writer.addRecord(rowData);
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "加：申报划入金额";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData);
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "减：申报划出金额";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData);
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "下一交易日开仓准备金";  
	            rowData[2] = cusfund4;  
	            writer.addRecord(rowData);
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "其它";  
	            rowData[2] = temp;  
	            writer.addRecord(rowData);
	            
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "应收手续费";  
	            rowData[2] = String.format("%.2f", otherfund45); 
	            writer.addRecord(rowData);
	            
	            double douCusfund11 = Double.parseDouble(cusfund11);
	            double change=douCusfund11-otherfund45;
	            rowData = new Object[3];  
	            rowData[0] = account;
	            rowData[1] = "实有货币资金变动";  
	            rowData[2] = String.format("%.2f", change);  
	            writer.addRecord(rowData);
	            
	           // String befHolddataTotal7="";
	            //file=sourcepath+File.separatorChar+account+"holddata"+befTime+".txt";
	            //befHolddataTotal7=getTotal(file,6);
	            //change=Double.parseDouble(holddataTotal7)-Double.parseDouble(befHolddataTotal7);
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "其中：交易保证金变动";  
	            rowData[2] = "-";  
	            writer.addRecord(rowData);
	            
	            //String befcusfund4="";
	           // file=sourcepath+File.separatorChar+account+"cusfund"+befTime+".txt";
	           // befcusfund4=getValue(file,3);
	           // change = Double.parseDouble(cusfund4)-Double.parseDouble(befcusfund4);
	            rowData = new Object[3];  
	            rowData[0] = account;  
	            rowData[1] = "结算准备金变动";  
	            rowData[2] = "-";  
	            writer.addRecord(rowData);
	            
	            // 写入数据  
	            writer.write();
	            
	        }else{
	        	System.out.println("文件已存在");
	        }   
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	    
	} 
	
	private static void writeDBFDelivery(String sourcepath,String targetpath,String account,String time)
	{
	    try {  
	        File f = new File(targetpath);
	        if(!f.exists())
	        {
	        	// 定义DBF文件字段  
	            DBFField[] fields = new DBFField[9];  
	            fields[0] = new DBFField();  
	            fields[0].setName("instrID");  
	            fields[0].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[0].setFieldLength(30);  
	            fields[1] = new DBFField();  
	            fields[1].setName("partID");  
	            fields[1].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[1].setFieldLength(10);  
	            fields[2] = new DBFField();  
	            fields[2].setName("partAbbr");  
	            fields[2].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[2].setFieldLength(10); 
	            fields[3] = new DBFField();  
	            fields[3].setName("accountID");  
	            fields[3].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[3].setFieldLength(12);
	            fields[4] = new DBFField();  
	            fields[4].setName("accAttr");  
	            fields[4].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[4].setFieldLength(12);
	            
	            fields[5] = new DBFField();  
	            fields[5].setName("clearPrice");  
	            fields[5].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[5].setFieldLength(20);
	            fields[6] = new DBFField();  
	            fields[6].setName("buyTot");  
	            fields[6].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[6].setFieldLength(20);
	            fields[7] = new DBFField();  
	            fields[7].setName("soldTot");  
	            fields[7].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[7].setFieldLength(20);
	            fields[8] = new DBFField();  
	            fields[8].setName("transfee");  
	            fields[8].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[8].setFieldLength(20);
	           
	            DBFWriter writer = new DBFWriter(f);
	            writer.setFields(fields); 
	            
	            // 一条条的写入记录  
	            String tmp="-";
	            String accountID="";
	            String instrID="";
	            String clearPrice="";
	            //String delivdetails45="";
	            String buyTot="";
	            String soldTot="";
	            String transfee="";
	            sourcepath=sourcepath+File.separatorChar+account+"delivdetails"+time+".txt";
	            File file = new File(sourcepath);
	    		//DecimalFormat df = new DecimalFormat( "0.00");
	            double tmpValue=0.00;
	    		if(file.isFile() && file.exists()){
	    			BufferedReader br = new BufferedReader(new FileReader(file));
	    			String line = null;
	    			while ((line = br.readLine()) != null) {
	    				String [] strS  = line.split("@");
	    				accountID=strS[1];
	    				instrID=strS[2];
	    				clearPrice=strS[6];
	    				if("S"==strS[3] || "S".equals(strS[3]))
	    				{
	    					soldTot=strS[8];
	    					buyTot="0.00";
	    				}else if("B"==strS[3] || "B".equals(strS[3]))
	    				{
	    					soldTot="0.00";
	    					buyTot=strS[8];
	    				}
	    				transfee=strS[9];
	    				
	    				
				        if(JCPZConstants.SPEC_COR_REPLACE_RULE.containsKey(account)){
				        	   account = JCPZConstants.SPEC_COR_REPLACE_RULE.get(account);
				        }
	    				
	    				Object[] rowData = new Object[9];  
			            rowData[0] = instrID;  
			            rowData[1] = account;  
			            rowData[2] = tmp;
			            rowData[3] = accountID;  
			            rowData[4] = tmp;
			            tmpValue = Double.parseDouble(clearPrice);
			            rowData[5] = String.format("%.2f", tmpValue);
			            tmpValue = Double.parseDouble(buyTot);
			            rowData[6] = String.format("%.2f", tmpValue);
			            tmpValue = Double.parseDouble(soldTot);
			            rowData[7] = String.format("%.2f", tmpValue);
			            tmpValue = Double.parseDouble(transfee);
			            rowData[8] = String.format("%.2f", tmpValue);
			            writer.addRecord(rowData);
	    			}
	    			br.close();
	    		}
	            
	            // 写入数据  
	            writer.write();
	        }else{
	        	System.out.println("文件已存在");
	        }   
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	}
	
	private static void writeDBFSettlementDetail(String sourcepath,String targetpath,String account,String time)
	{
	    try {  
			String holddataPath=sourcepath+File.separator+account+"holddata"+time+".txt";
			String trddataPath=sourcepath+File.separator+account+"trddata"+time+".txt";
			String liquiddetailsPath=sourcepath+File.separator+account+"liquiddetails"+time+".txt";
			Vector<DbfSettlementDetail> vector=new Vector<DbfSettlementDetail>();
            Map<String, Integer> map =  new  HashMap<String, Integer>();
            
            int index=0;
            int vectorSize=0;
            DbfSettlementDetail tmp;
            BufferedReader holddatabr = new BufferedReader(new FileReader(holddataPath));
			String line = null;
			while ((line = holddatabr.readLine()) != null) {
				String [] strS  = line.split("@");
				
				if(map.containsKey(strS[2]))
				{
					index=map.get(strS[2]);
					tmp = vector.get(index);
					if("B".equals(strS[3]) || "B"==strS[3]){
						tmp.bTotalAmt+=Double.parseDouble(strS[5]);
					}else if("S".equals(strS[3]) || "S"==strS[3]){
						tmp.sTotalAmt+=Double.parseDouble(strS[5]);
					}
					tmp.margin+=Double.parseDouble(strS[6]);
					tmp.actual+=Double.parseDouble(strS[7]);
					vector.set(index, tmp);
				}else{
					vectorSize=vector.size();
					map.put(strS[2], vectorSize);
					tmp=new DbfSettlementDetail();
					tmp.partID=account;
					tmp.clientID=strS[12];
					tmp.instrID=strS[2];
					tmp.clearPrice=strS[11];
					tmp.bopenAmt=0;
					tmp.boffAmt=0;
					tmp.buyAmt=0;
					tmp.sopenAmt=0;
					tmp.soffAmt=0;
					tmp.sellAmt=0;
					tmp.buySum=0.00;
					tmp.sellSum=0.00;
					tmp.transfee=0.00;
					tmp.holddata3=strS[3];
					if("B".equals(strS[3]) || "B"==strS[3]){
						tmp.bTotalAmt=Integer.parseInt(strS[5]);
						tmp.sTotalAmt=0;
					}else  if("S".equals(strS[3]) || "S"==strS[3]){
						tmp.sTotalAmt=Integer.parseInt(strS[5]);
						tmp.bTotalAmt=0;
					}
					tmp.margin=Double.parseDouble(strS[6]);
					tmp.actual=Double.parseDouble(strS[7]);
					
					vector.add(tmp);
				}
			}
			holddatabr.close();
			
			BufferedReader trddatabr = new BufferedReader(new FileReader(trddataPath));
			line = null;
			index=0;
            vectorSize=0;
			while ((line = trddatabr.readLine()) != null) {
				String [] strS  = line.split("@");
				
				if(map.containsKey(strS[3]))
				{
					index=map.get(strS[3]);
					tmp = vector.get(index);
					if(("B"==strS[4] || "B".equals(strS[4])) && ("O"==strS[9] || "O".equals(strS[9])))
					{
						tmp.bopenAmt += Integer.parseInt(strS[5]);
					}else if(("B"==strS[4] || "B".equals(strS[4])) && ("L"==strS[9] || "L".equals(strS[9])))
					{
						tmp.boffAmt += Integer.parseInt(strS[5]);
					}else if(("S"==strS[4] || "S".equals(strS[4])) && ("O"==strS[9] || "O".equals(strS[9])))
					{
						tmp.sopenAmt += Integer.parseInt(strS[5]);
					}else if(("S"==strS[4] || "S".equals(strS[4])) && ("L"==strS[9] || "L".equals(strS[9])))
					{
						tmp.soffAmt += Integer.parseInt(strS[5]);
					}
					if("S"==strS[4] || "S".equals(strS[4]))
					{
						tmp.sellAmt += Integer.parseInt(strS[5]);
						tmp.sellSum += Double.parseDouble(strS[7]);
					}else if("B"==strS[4] || "B".equals(strS[4]))
					{
						tmp.buyAmt += Integer.parseInt(strS[5]);
						tmp.buySum += Double.parseDouble(strS[7]);
					}
					tmp.actual += Double.parseDouble(strS[11]);
					tmp.transfee += Double.parseDouble(strS[13]);
					vector.set(index, tmp);
				}else{
					vectorSize=vector.size();
					map.put(strS[3], vectorSize);
					tmp=new DbfSettlementDetail();
					tmp.partID=account;
					tmp.clientID=strS[14];
					tmp.instrID=strS[3];
					tmp.clearPrice="0.00";
					if(("B"==strS[4] || "B".equals(strS[4])) && ("O"==strS[9] || "O".equals(strS[9])))
					{
						tmp.bopenAmt=Integer.parseInt(strS[5]);
					}else if(("B"==strS[4] || "B".equals(strS[4])) && ("L"==strS[9] || "L".equals(strS[9])))
					{
						tmp.boffAmt=Integer.parseInt(strS[5]);
					}else if(("S"==strS[4] || "S".equals(strS[4])) && ("O"==strS[9] || "O".equals(strS[9])))
					{
						tmp.sopenAmt=Integer.parseInt(strS[5]);
					}else if(("S"==strS[4] || "S".equals(strS[4])) && ("L"==strS[9] || "L".equals(strS[9])))
					{
						tmp.soffAmt=Integer.parseInt(strS[5]);
					}
					if("S"==strS[4] || "S".equals(strS[4]))
					{
						tmp.sellAmt=Integer.parseInt(strS[5]);
						tmp.sellSum=Double.parseDouble(strS[7]);
					}else if("B"==strS[4] || "B".equals(strS[4]))
					{
						tmp.buyAmt=Integer.parseInt(strS[5]);
						tmp.buySum=Double.parseDouble(strS[7]);
					}
					tmp.transfee=Double.parseDouble(strS[13]);
					tmp.actual = Double.parseDouble(strS[11]);
					vector.add(tmp);
				}	
			}
			trddatabr.close();
			
			BufferedReader liquiddetailsbr = new BufferedReader(new FileReader(liquiddetailsPath));
			line = null;
			index=0;
            vectorSize=0;
			while ((line = liquiddetailsbr.readLine()) != null) {
				String [] strS  = line.split("@");
				
				if(map.containsKey(strS[2]))
				{
					index=map.get(strS[2]);
					tmp = vector.get(index);
					if("0.00".equals(tmp.clearPrice) ||"0.00"==tmp.clearPrice)
					{
						tmp.clearPrice=strS[9];
					}
					vector.set(index, tmp);
				}	
			}
			liquiddetailsbr.close();
			
	        File f = new File(targetpath);
	        if(!f.exists())
	        {
	        	// 定义DBF文件字段  
	            DBFField[] fields = new DBFField[17];  
	            fields[0] = new DBFField();  
	            fields[0].setName("partID");  
	            fields[0].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[0].setFieldLength(12);  
	            fields[1] = new DBFField();  
	            fields[1].setName("clientID");  
	            fields[1].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[1].setFieldLength(12);  
	            fields[2] = new DBFField();  
	            fields[2].setName("instrID");  
	            fields[2].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[2].setFieldLength(30); 
	            fields[3] = new DBFField();  
	            fields[3].setName("clearPrice");  
	            fields[3].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[3].setFieldLength(20);
	            fields[4] = new DBFField();  
	            fields[4].setName("bopenAmt");  
	            fields[4].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[4].setFieldLength(20);
	            
	            fields[5] = new DBFField();  
	            fields[5].setName("boffAmt");  
	            fields[5].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[5].setFieldLength(20);
	            fields[6] = new DBFField();  
	            fields[6].setName("buyAmt");  
	            fields[6].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[6].setFieldLength(20);
	            fields[7] = new DBFField();  
	            fields[7].setName("sopenAmt");  
	            fields[7].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[7].setFieldLength(20);
	            fields[8] = new DBFField();  
	            fields[8].setName("soffAmt");  
	            fields[8].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[8].setFieldLength(20);
	            fields[9] = new DBFField();  
	            fields[9].setName("sellAmt");  
	            fields[9].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[9].setFieldLength(20);
	            
	            fields[10] = new DBFField();  
	            fields[10].setName("buySum");  
	            fields[10].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[10].setFieldLength(20);
	            fields[11] = new DBFField();  
	            fields[11].setName("sellSum");  
	            fields[11].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[11].setFieldLength(20);
	            fields[12] = new DBFField();  
	            fields[12].setName("bTotalAmt");  
	            fields[12].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[12].setFieldLength(20);
	            fields[13] = new DBFField();  
	            fields[13].setName("sTotalAmt");  
	            fields[13].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[13].setFieldLength(20);
	            fields[14] = new DBFField();  
	            fields[14].setName("margin");  
	            fields[14].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[14].setFieldLength(20);
	            
	            fields[15] = new DBFField();  
	            fields[15].setName("actual");  
	            fields[15].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[15].setFieldLength(20);
	            fields[16] = new DBFField();  
	            fields[16].setName("transfee");  
	            fields[16].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[16].setFieldLength(20);
	            
	        	DBFWriter writer = new DBFWriter(f);
	            writer.setFields(fields);  
	            
					
					
	            // 一条条的写入记录  
	            
				Object[] rowData;
				double tmpValue=0.00;
				String tmpStr="0";
				for(int i=0;i<vector.size();++i)
				{
					rowData = new Object[17];  
					tmp=vector.get(i);
					rowData[0] = tmp.partID;
					
					//数据写入之前，如是8801106账号，则partID改为会员号0153
		            if(JCPZConstants.SPEC_COR_REPLACE_RULE.containsKey(account)){
		            	rowData[0] = JCPZConstants.SPEC_COR_REPLACE_RULE.get(account);
			        }
		            
					rowData[1] = tmp.clientID;
					rowData[2] = tmp.instrID;
					tmpStr=tmp.clearPrice;
					tmpValue=Double.parseDouble(tmpStr);
    				tmpStr=String.format("%.3f", tmpValue);
    				rowData[3] = tmpStr; 
		            rowData[4] = Integer.toString(tmp.bopenAmt);  
		            rowData[5] = Integer.toString(tmp.boffAmt);
		            rowData[6] = Integer.toString(tmp.buyAmt);  
		            rowData[7] = Integer.toString(tmp.sopenAmt);  
		            rowData[8] = Integer.toString(tmp.soffAmt);
		            rowData[9] = Integer.toString(tmp.sellAmt); 
		            tmpValue = tmp.buySum;
		            tmpStr=String.format("%.2f", tmpValue);
		            rowData[10] = tmpStr; 
		            tmpValue = tmp.sellSum;
		            tmpStr=String.format("%.2f", tmpValue);
		            rowData[11] = tmpStr;  
		            if(tmp.bTotalAmt==0){
		            	rowData[12]="0";
		            }else{
		            	rowData[12] = Integer.toString(tmp.bTotalAmt); 
		            }
		            if(tmp.sTotalAmt==0)
		            {
		            	rowData[13]="0";
		            }else{
		            	rowData[13] = Integer.toString(tmp.sTotalAmt);
		            }
		            tmpValue = tmp.margin;
		            tmpStr=String.format("%.2f", tmpValue);
		            rowData[14] = tmpStr;
		            tmpValue = tmp.actual;
		            tmpStr=String.format("%.2f", tmpValue);
		            rowData[15] = tmpStr; 
		            tmpValue = tmp.transfee;
		            tmpStr = String.format("%.2f", tmpValue);
		            rowData[16] = tmpStr;  
		            writer.addRecord(rowData);
				}
				
	            // 写入数据  
	            writer.write();
	        }else{
	        	System.out.println("文件已存在");
	        }   
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	}
	
	private static void writeDBFTrade(String sourcepath,String targetpath,String account,String time)
	{
	    try {  
	        File f = new File(targetpath);
	        if(!f.exists())
	        {
	        	// 定义DBF文件字段  
	            DBFField[] fields = new DBFField[12];  
	            fields[0] = new DBFField();  
	            fields[0].setName("partID");  
	            fields[0].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[0].setFieldLength(12);  
	            fields[1] = new DBFField();  
	            fields[1].setName("clientID");  
	            fields[1].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[1].setFieldLength(12);  
	            fields[2] = new DBFField();  
	            fields[2].setName("instrID");  
	            fields[2].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[2].setFieldLength(30); 
	            fields[3] = new DBFField();  
	            fields[3].setName("Tradeid");  
	            fields[3].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[3].setFieldLength(12);
	            fields[4] = new DBFField();  
	            fields[4].setName("Tvolume");  
	            fields[4].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[4].setFieldLength(20);
	            
	            fields[5] = new DBFField();  
	            fields[5].setName("Tprice");  
	            fields[5].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[5].setFieldLength(20);
	            fields[6] = new DBFField();  
	            fields[6].setName("Tamt");  
	            fields[6].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[6].setFieldLength(20);
	            fields[7] = new DBFField();  
	            fields[7].setName("Ttime");  
	            fields[7].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[7].setFieldLength(8);
	            fields[8] = new DBFField();  
	            fields[8].setName("Direction");  
	            fields[8].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[8].setFieldLength(10);
	            fields[9] = new DBFField();  
	            fields[9].setName("Offsetflag");  
	            fields[9].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[9].setFieldLength(10);
	            
	            fields[10] = new DBFField();  
	            fields[10].setName("Orderid");  
	            fields[10].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[10].setFieldLength(12);
	            fields[11] = new DBFField();  
	            fields[11].setName("Userid");  
	            fields[11].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[11].setFieldLength(15);
	            
	            
	        	DBFWriter writer = new DBFWriter(f);
	            writer.setFields(fields); 
	            
	            
	    		//DecimalFormat df = new DecimalFormat( "0.00"); 
	            String trddata2="";
	            String trddata15="";
	            String trddata4="";
	            String trddata3="";
	            String trddata6="";
	            String trddata7="";
	            String trddata8="";
	            String trddata9="";
	            String trddata5="";
	            String trddata10="";
	            String trddata18="";
	            String trddata19="";
	            sourcepath = sourcepath+File.separatorChar+account+"trddata"+time+".txt";
	            File file = new File(sourcepath);
	    		if(file.isFile() && file.exists()){
	    			BufferedReader br = new BufferedReader(new FileReader(file));
	    			String line = null;
	    			while ((line = br.readLine()) != null) {
	    				String [] strS  = line.split("@");
	    				trddata2=strS[1];
	    				trddata3=strS[2];
	    				trddata4=strS[3];
	    				trddata5=strS[4];
	    				trddata6=strS[5];
	    				trddata7=strS[6];
	    				trddata8=strS[7];
	    				trddata9=strS[8];
	    				trddata10=strS[9];
	    				trddata15=strS[14];
	    				trddata18=strS[17];
	    				trddata19=strS[18];
	    				
	    				
			            if(JCPZConstants.SPEC_COR_REPLACE_RULE.containsKey(account)){
			            	trddata2 = JCPZConstants.SPEC_COR_REPLACE_RULE.get(account);
				        }
			            
	    				if("B"==trddata5 || "B".equals(trddata5))
	    				{
	    					trddata5="买";
	    				}else if("S"==trddata5 || "S".equals(trddata5)){
	    					trddata5="卖";
	    				}
	    				double tmp=0;
	    				tmp=Double.parseDouble(trddata7);
	    				trddata7=String.format("%.2f", tmp);
	    				
	    				tmp=Double.parseDouble(trddata8);
	    				trddata8=String.format("%.2f", tmp);
	    				
	    				
	    				if("O"==trddata10 || "O".equals(trddata10))
	    				{
	    					trddata10="开仓";
	    				}else if("L"==trddata10 || "L".equals(trddata10)){
	    					trddata10="平仓";
	    				}
	    				
	    				Object[] rowData = new Object[12];  
	    	            rowData[0] = trddata2;  
	    	            rowData[1] = trddata15;  
	    	            rowData[2] = trddata4;
	    	            rowData[3] = trddata3;  
	    	            rowData[4] = trddata6;  
	    	            rowData[5] = trddata7;
	    	            rowData[6] = trddata8;  
	    	            rowData[7] = trddata9;  
	    	            rowData[8] = trddata5;
	    	            rowData[9] = trddata10;  
	    	            rowData[10] = trddata18;
	    	            rowData[11] = trddata19;
	    	            writer.addRecord(rowData);
	    			}
	    			br.close();
	    		}
	            
	            // 写入数据  
	            writer.write();
	        }else{
	        	System.out.println("文件已存在");
	        }   
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	}
	
	private static void writeDBFClientCapitalDetail(String tmpSource,String sourcepath,String targetpath,String account,String time)
	{
	    try {
            tmpSource=tmpSource+File.separatorChar+account+"trddata"+time+".txt";
            sourcepath=sourcepath+File.separatorChar+"0153_SG01_"+time+"_1_ClientCapitalDetail.DBF";
            File file = new File(tmpSource);
			String arr[] = {"0","0","0"};
    		if(file.isFile() && file.exists()){
    			BufferedReader br = new BufferedReader(new FileReader(file));
    			String line = null;
    			int i=0;
    			while ((line = br.readLine()) != null) {
    				String [] strS  = line.split("@");
                    //if("015303"==strS[18] || "015303".equals(strS[18]))//过滤某个席位太精确
                    String cffexFlag = strS[18] == null ? "" : strS[18];
                    if (cffexFlag.startsWith("0153"))
    				{
    					if(i==0){
                            arr[i] = strS[14].trim();//
	    					i++;
    					}else if (i == 1){
    						if (!arr[i-1].equals(strS[14])){
    							arr[i]=strS[14].trim();
		    					i++;
    						}
    					}else if( i == 2){
    						if(!arr[i-2].equals(strS[14]) && !arr[i-1].equals(strS[14])){
    							arr[i]=strS[14].trim();
    							i++;
    						}
    					}
    				}
    			}
    			br.close();
    		}
       
	        File f = new File(targetpath);
	        if(!f.exists())
	        {
	        	// 定义DBF文件字段  
	            DBFField[] fields = new DBFField[6];  
	            fields[0] = new DBFField();  
	            fields[0].setName("accountID");  
	            fields[0].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[0].setFieldLength(30);  
	            fields[1] = new DBFField();  
	            fields[1].setName("partID");  
	            fields[1].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[1].setFieldLength(10);  
	            fields[2] = new DBFField();  
	            fields[2].setName("clientID");  
	            fields[2].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[2].setFieldLength(10); 
	            fields[3] = new DBFField();  
	            fields[3].setName("amount");  
	            fields[3].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[3].setFieldLength(12);
	            fields[4] = new DBFField();  
	            fields[4].setName("moneyType");  
	            fields[4].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[4].setFieldLength(12);
	            fields[5] = new DBFField();  
	            fields[5].setName("typeMemo");  
	            fields[5].setDataType(DBFField.FIELD_TYPE_C);  
	            fields[5].setFieldLength(20);
	            
	            DBFWriter writer = new DBFWriter(f);
	            writer.setFields(fields); 
	
	            File file1 = new File(sourcepath);
	            if(file1.isFile() && file1.exists()){
	            	InputStream br = new FileInputStream(file1);
	            	DBFReader dbfreader = new DBFReader(br);
	            	Object[] aobj= dbfreader.nextRecord();
	            	while(!"".equals(aobj) && null != aobj){
	            		/*if(aobj[2].toString().trim().equals("00012943")){
	            			System.out.print("kkdk\n");
	            		}*/
	            		if(aobj[2].toString().trim().equals(arr[0]) || aobj[2].toString().trim().equals(arr[1]) || aobj[2].toString().trim().equals(arr[2])){
	            			Object[] rowData = new Object[6];  
	        	            rowData[0] = account;  
	        	            rowData[1] = account;  
	        	            
	        	            //数据写入之前，如是8801106账号，则partID改为会员号0153
	        	            if(JCPZConstants.SPEC_COR_REPLACE_RULE.containsKey(account)){
	    		            	rowData[1] = JCPZConstants.SPEC_COR_REPLACE_RULE.get(account);
	    			        }
	    		            
	    		            
	        	            rowData[2] = aobj[2].toString();
	        	            rowData[3] = aobj[3].toString();  
	        	            rowData[4] = aobj[4].toString();  
	        	            rowData[5] = aobj[5].toString();
	        	            writer.addRecord(rowData);
	            		}
	            		aobj= dbfreader.nextRecord();
	            	}

	    			br.close();
	    		}
	            // 写入数据  
	            writer.write();
	        }
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	}
	
	// 判断删除指定的文件或文件夹是否成功，成功则返回true否则返回false
	public static boolean deleteAnyone(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			//System.out.println("文件删除失败：" + fileName + "文件不存在！");
			return false;
		} else {
			if (file.isFile()) {
				return deleteFiles(fileName);
			} else {
				return deleteDir(fileName);
			}
		}
	}
	
	// 判断删除指定的文件是否成功，成功则返回true否则返回false
	public static boolean deleteFiles(String fileName) {
		File file = new File(fileName);
		// 如果文件路径对应的文件存在，并且是一个文件，则直接删除。
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				//System.out.println("文件：" + fileName + "删除成功！");
				return true;
			} else {
				//System.out.println("文件" + fileName + "删除失败！");
				return false;
			}
		} else {
			//System.out.println("文件删除失败：" + fileName + "文件不存在！");
			return false;
		}
	}
	
	// 判断删除指定的目录（文件夹）以及目录下的文件是否成功，成功则返回true否则返回false
	public static boolean deleteDir(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符。
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir表示的文件不存在，或者不是一个文件夹，则退出
		if (!dirFile.exists() || (!dirFile.isDirectory())) {
			//System.out.println("目录删除失败：" + dir + "目录不存在！");
			return false;
		}
		boolean flag = true;
		// 删除指定目录下所有文件（包括子目录）
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除文件
			if (files[i].isFile()) {
				flag = deleteFiles(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = deleteDir(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}
		if (!flag) {
			//System.out.println("删除目录失败！");
			return false;
		}
		//return true;
		// 删除当前目录
		if (dirFile.delete()) {
			//System.out.println("目录：" + dir + "删除成功！");
			return true;
		} else {
			return false;
		}
		
	}
	
	//创建文件夹
	public void createDir(String filePath){
		File dir = new File(filePath);
		if(!dir.exists()){
			dir.mkdirs();
		}
	}
	
	/**
	 * @param sourcefiles 源文件
	 * @param decompreDirectory 解压缩后文件存放的目录
	 * @throws IOException IO异常
	 */
	public void unzip(String sourcefiles, String decompreDirectory) throws IOException {
		ZipFile readfile = null;
		try {
			readfile =new ZipFile(sourcefiles);
			Enumeration<?> takeentrie = readfile.entries();
			ZipEntry zipEntry = null;
			File credirectory = new File(decompreDirectory);
			credirectory.mkdirs();
			while (takeentrie.hasMoreElements()) {
				zipEntry = (ZipEntry) takeentrie.nextElement();
				String entryName = zipEntry.getName();
				InputStream in = null;
				FileOutputStream out = null;
				try {
					if (zipEntry.isDirectory()) {
						String name = zipEntry.getName();
						name = name.substring(0, name.length() - 1);
						File  createDirectory = new File(decompreDirectory+ File.separator + name);
						createDirectory.mkdirs();
					} else {
						int index = entryName.lastIndexOf("\\");
						if (index != -1) {
							File createDirectory = new File(decompreDirectory+ File.separator+ entryName.substring(0, index));
							createDirectory.mkdirs();
						}
						index = entryName.lastIndexOf("/");
						if (index != -1) {
							File createDirectory = new File(decompreDirectory + File.separator + entryName.substring(0, index));
							createDirectory.mkdirs();
						}
						File unpackfile = new File(decompreDirectory + File.separator + zipEntry.getName());
						in = readfile.getInputStream(zipEntry);
						out = new FileOutputStream(unpackfile);
						int c;
						byte[] by = new byte[1024];
						while ((c = in.read(by)) != -1) {
							out.write(by, 0, c);
						}
						
						out.flush();
						out.close();
					}
					
				
					
				} catch (IOException ex) {
					ex.printStackTrace();
					throw new IOException("解压失败：" + ex.toString());
				} finally {
					if (null != in) {
						in.close();
						}
					if (null != out) {
						out.close();
						}
					}

			}
			readfile.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new IOException("解压失败：" + ex.toString());
		} finally {
			if (readfile != null) {
				try {
					readfile.close();
				} catch (IOException ex) {
					ex.printStackTrace();
					throw new IOException("解压失败：" + ex.toString());
				}
			}
		}
	}
	
	/** 
	 * 删除单个文件 
	 * @param   sPath    被删除文件的文件名 
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	public boolean deleteFile(String sPath) {  
	    boolean flag = false;  
	    File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  
	
	/*public void zip(String inputFileName, String zipFileName) throws Exception {
		//System.out.println(zipFileName);
		zip(zipFileName, new File(inputFileName));
	}*/

	/*private void zip(String zipFileName, File inputFile) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				zipFileName));
		zip(out, inputFile, "");
		System.out.println("zip done");
		out.close();
	}*/

	/*private void zip(ZipOutputStream out, File f, String base) throws Exception {
		if (f.isDirectory()) {	//判断是否为目录
			File[] fl = f.listFiles();
			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else {				//压缩目录中的所有文件
			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			int b;
			//System.out.println(base);
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			BufferedInputStream bis = new BufferedInputStream(in);
			int len=0;
			byte[] buf=new byte[1024];
			while((len=bis.read(buf,0,1024))!=-1){
				out.write(buf,0,len);
			}
			in.close();
			bis.close();
		}
	}*/
	
	private final char[] readModel(String filePath,String fileName, String charSet)
	throws IOException {
		FileInputStream fileinputstream = new FileInputStream(filePath + File.separator + fileName);
		InputStreamReader in = new InputStreamReader(fileinputstream, charSet);
		int lenght = fileinputstream.available();
		char bytes[] = new char[lenght];
		in.read(bytes);
		in.close();
		fileinputstream.close();
		return bytes;
	}


	private final void writeFile(String filePath,String fileName, String charSet, String content)
		throws IOException {
		FileOutputStream fileoutputstream = new FileOutputStream(newFile(filePath, fileName));
		OutputStreamWriter out = new OutputStreamWriter(fileoutputstream,charSet);
		out.write(content);
		out.close();
		fileoutputstream.close();
	}

	public static String newFile(String directory,String fileName){
		//File newFile;
		// myFilePat	
		try{
			java.io.File mypathName=new java.io.File(directory);
			if(!mypathName.exists()){
				mypathName.mkdir();
				}
			}catch(Exception e){
				System.out.println("新建目录出错");
				e.printStackTrace();
			}
			
		//String   filename   =   Util.getDate("yyyy_MM_dd");//fymd.format(date);   
		
		File myFile=new File(fileName);
		try{
			
			if(!myFile.exists()){
				myFile.createNewFile();
				}
			}catch(Exception e){
				System.out.println("新建文件出错");
				e.printStackTrace();
				}
		return directory+File.separator+fileName;
	}
}
