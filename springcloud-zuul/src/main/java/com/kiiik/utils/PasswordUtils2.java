package com.kiiik.utils;
import org.springframework.stereotype.Component;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月12日
 */
@Component
public class PasswordUtils2 {/*
	 Log log = LogFactory.getLog(PasswordUtils2.class);
	private  String RSAKeyStore = System.getProperty("user.dir")+"/RSAKey.ser";
	*//**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 从rsa加密串中获取加密字符串<br>
	 *创建时间: 2018年11月12日
	 *@param enPassword
	 *@return
	 *//*
	public  String getPassword(String enPassword){
		return enPassword;
	}
	public  String getPassword1(String enPassword){
		
		String encodeStr = new String(Base64.decode(enPassword.getBytes()));
		byte[] en_result = new BigInteger(encodeStr, 16).toByteArray();
		byte[] de_result;
		try {
			de_result = RSAUtil.decrypt(RSAUtil.getKeyPair(RSAKeyStore).getPrivate(),
					en_result);
			StringBuilder orignPassword = new StringBuilder(new String(de_result)).reverse();
			String realPassword = URLDecoder.decode(orignPassword.toString(),"UTF-8");
			log.debug(realPassword);
			if(!StringUtils.isEmpty(realPassword)){
				return realPassword.toUpperCase();
			}else{
				return KiiikContants.BLANK;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return KiiikContants.BLANK;
		}
	}
	
	public  Map<String,String> genRSAME() throws Exception{
		Map<String,String> data = new HashMap<>();
		data.put("m", getPublicKey());
		data.put("e",RSAUtil.getKeyPair(RSAKeyStore)
    			.getPublic()
    			.toString()
    			.replace("\n", "#")
    			.split("#")[2].trim()
    			.split(":")[1].trim());
		return data;
	}
	public String  getPublicKey(){
		try {
			return RSAUtil.getKeyPair(RSAKeyStore)
					.getPublic()
					.toString()
					.replace("\n", "#")
					.split("#")[1].trim()
					.split(":")[1].trim();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	public  void genRSASer(){
		try {
			File file = new File(RSAKeyStore);
			if(file.exists()){
				file.delete();
			}
			file.createNewFile();
			RSAUtil.generateKeyPair(RSAKeyStore);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/}
