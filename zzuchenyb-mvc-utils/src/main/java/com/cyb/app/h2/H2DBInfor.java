package com.cyb.app.h2;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.utils.text.ELUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月23日
 */
public class H2DBInfor {
	/*jdbc:h2:tcp://localhost:${port}/~/${dbName};
	  jdbc:h2:tcp://localhost:${port}/${h2dbPath}/${dbName};
	*/
	public static String Server="0";
	/*jdbc:h2:~/.h2/DBName
	 *jdbc:h2:~/.h2/${h2dbPath}/DBName
	 */
	public static String Embed="1";
	//jdbc:h2:mem:DBName;
	public static String Memery="2";//基本不用，应用关掉时数据丢失
	Log log = LogFactory.getLog(H2DBInfor.class);
	String type;//0 tcpserver 1 embed 2 mem
	String url;
	String password;
	String username;
	String defaultH2dbPath="d:/data/h2db";
	String defaultServer="localhost";
	String defaultPort = "9092";//h2的默认服务端口
	String defaultDBName="test";
	String defaultUsername="sa";
	String defaultPassword="111111";
	public boolean isServer(H2DBInfor db){
		return Server.equals(db.getType());
	}
	public boolean isEmbed(H2DBInfor db){
		return Embed.equals(db.getType());
	}
	public boolean isMemery(H2DBInfor db){
		return Memery.equals(db.getType());
	}
	
	public String getDefaultTcpUrl(){
		String url = "jdbc:h2:tcp://${server}:${port}/${h2dbPath}/${dbName};";
		Map<String, Object> param = new HashMap<>();
		param.put("port",defaultPort );
		param.put("h2dbPath",defaultH2dbPath );
		param.put("dbName",defaultDBName );
		param.put("server",defaultServer );
		return ELUtils.el(url, param);
	}
    public String getDefaultEmbedUrl(){
    	String url ="jdbc:h2:~/.h2/first/${dbName}";//ok
    	//jdbc:h2:~/${h2dbPath}/${dbName} 不可以用~即代表主目录盘符
    	url ="jdbc:h2:${h2dbPath}/${dbName}";//指定路径
    	Map<String, Object> param = new HashMap<>();
		param.put("dbName",defaultDBName );
		param.put("h2dbPath",defaultH2dbPath );
		return ELUtils.el(url, param);
	}
	
	/**
	 * jdbc:h2:tcp://localhost:${port}/~/${dbName};
	 * jdbc:h2:tcp://localhost:${port}/${h2dburl}/${dbName};
	 * 默认存储
	 */
	public H2DBInfor(){
		this.type = "1";//默认是内存存储
		if(Memery.equals(type)){//2
			this.url = "";//默认路径
		}else if(Embed.equals(type)){//1
			this.url = getDefaultEmbedUrl();//自定义数据库的位置
		}else{
			this.url = getDefaultTcpUrl();//3
		}
		this.password = defaultPassword;
		this.username = defaultUsername;
	}
	public H2DBInfor(String type){
		if(Memery.equals(type)){//2
			this.url = "";//默认路径
		}else if(Embed.equals(type)){//1
			this.url = getDefaultEmbedUrl();//自定义数据库的位置
		}else{
			this.url = getDefaultTcpUrl();//3
		}
		this.password = defaultPassword;
		this.username = defaultUsername;
	}
	
	//指定类型指定url
	public H2DBInfor(
			String type,String url,
			String password,String username){
		this.type = type;
		if(Memery.equals(type)){
			this.url = "";//默认路径
		}else if(Embed.equals(type)){
			this.url = getDefaultEmbedUrl();//自定义数据库的位置
		}else{
			this.url = getDefaultTcpUrl();
		}
		this.password = password;
		this.username = username;
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
	
	
	public static String getServer() {
		return Server;
	}
	public static void setServer(String server) {
		Server = server;
	}
	public static String getEmbed() {
		return Embed;
	}
	public static void setEmbed(String embed) {
		Embed = embed;
	}
	public static String getMemery() {
		return Memery;
	}
	public static void setMemery(String memery) {
		Memery = memery;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDefaultH2dbPath() {
		return defaultH2dbPath;
	}
	public void setDefaultH2dbPath(String defaultH2dbPath) {
		this.defaultH2dbPath = defaultH2dbPath;
	}
	public String getDefaultServer() {
		return defaultServer;
	}
	public void setDefaultServer(String defaultServer) {
		this.defaultServer = defaultServer;
	}
	public String getDefaultPort() {
		return defaultPort;
	}
	public void setDefaultPort(String defaultPort) {
		this.defaultPort = defaultPort;
	}
	public String getDefaultDBName() {
		return defaultDBName;
	}
	public void setDefaultDBName(String defaultDBName) {
		this.defaultDBName = defaultDBName;
	}
	public String getDefaultUsername() {
		return defaultUsername;
	}
	public void setDefaultUsername(String defaultUsername) {
		this.defaultUsername = defaultUsername;
	}
	public String getDefaultPassword() {
		return defaultPassword;
	}
	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}
	public static void main(String[] args) {
	}
	
}
