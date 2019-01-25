package com.cyb.app.h2;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月23日
 */
public class H2DBInfor {
	public static String Server="0";
	public static String Embed="1";
	public static String Memery="2";
	Log log = LogFactory.getLog(H2DBInfor.class);
	String type;//0 tcpserver 1 embed 2 mem
	String path;
	String fileName;
	String password;
	String username;
	public boolean isServer(H2DBInfor db){
		return Server.equals(db.getType());
	}
	public boolean isEmbed(H2DBInfor db){
		return Embed.equals(db.getType());
	}
	public boolean isMemery(H2DBInfor db){
		return Memery.equals(db.getType());
	}
	public H2DBInfor(){
		this.type = "1";
		if(Memery.equals(type)){
			this.path = "";
		}else{
			this.path = "/data/";
		}
		this.fileName = "test";
		this.password = "111111";
		this.username = "sa";
	}
	public H2DBInfor(String type,String path,String fileName,String password,String username){
		this.type = type;
		if(Memery.equals(type)){
			this.path = "";
		}else{
			this.path = path;
		}
		this.fileName = fileName;
		this.password = password;
		this.username = username;
	}
	public H2DBInfor(String type,String path,String fileName){
		this.type = type;
		if(Memery.equals(type)){
			this.path = "";
		}else{
			this.path = path;
		}
		this.fileName = fileName;
		username="sa";
		password="11111";
	}
	public Log getLog() {
		return log;
	}
	public void setLog(Log log) {
		this.log = log;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
