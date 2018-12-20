package com.kiiik.utils;
import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.cyb.file.FileUtils;
import com.kiiik.pub.contant.KiiikContants;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月12日
 */
//@Component
public class PasswordUtils {
	 Log log = LogFactory.getLog(PasswordUtils.class);
	private  String PrivateKeyStore = System.getProperty("user.dir")+"/prikey.ser";
	private  String PublicKeyStore = System.getProperty("user.dir")+"/pubkey.ser";
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 从rsa加密串中获取加密字符串<br>
	 *创建时间: 2018年11月12日
	 *@param enPassword
	 *@return
	 * @throws Exception 
	 */
	public  String getPassword(String enPassword) throws Exception{
		return decodePassword(enPassword);
	}
	
	public  String decodePassword(String enBase64Password) throws Exception{
		/*System.out.println(enBase64Password);
		String urlDecodeString = URLDecoder.decode(enBase64Password, KiiikContants.UTF8);
		System.out.println(urlDecodeString);*/
		String res = RSAUtil.decryptDataOnJava(enBase64Password, getPrivateKey());
		try {
			log.debug("解密内容"+res);
			if(!StringUtils.isEmpty(res)){
				return res;
			}else{
				return KiiikContants.BLANK;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return KiiikContants.BLANK;
		}
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取公钥<br>
	 *创建时间: 2018年11月14日
	 *@return
	 */
	public  String  getPublicKey(){
		try {
			if(!hasSerFile()){
				genRSASer();
			}
			return  FileUtils.readContentFromFile(PublicKeyStore);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public synchronized boolean hasSerFile(){
		File priFile = new File(PrivateKeyStore);
		File pubFile = new File(PublicKeyStore);
		if(!priFile.exists()||!pubFile.exists()){
			return false;
		}
		return true;
	}
	
	
	//重新生成密钥对
	public  void genRSASer() throws Exception{
		KeyPair keyPair = RSAUtil.genKeyPair(); 
		try {
			File priFile = new File(PrivateKeyStore);
			if(priFile.exists()){
				priFile.delete();
			}
			File pubFile = new File(PublicKeyStore);
			if(pubFile.exists()){
				pubFile.delete();
			}
			priFile.createNewFile();
			pubFile.createNewFile();
			log.info("秘钥文件存储位置："+priFile);
			String pub = RSAUtil.getPublicKey((RSAPublicKey)keyPair.getPublic());
			System.out.println("pub:"+pub);
			String pri = RSAUtil.getPrivateKey((RSAPrivateKey) keyPair.getPrivate());
			System.out.println("pri:"+pri);
			FileUtils.overideString2File(pub, PublicKeyStore);
			FileUtils.overideString2File(pri, PrivateKeyStore);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
		PasswordUtils utils = new PasswordUtils();
		utils.genRSASer();
		System.out.println(utils.getPublicKey());
		System.out.println(utils.getPrivateKey());
	}
	public  String getPrivateKey() throws Exception {
		if(!hasSerFile()){
			genRSASer();
		}
		return  FileUtils.readContentFromFile(PrivateKeyStore);
	}
}
