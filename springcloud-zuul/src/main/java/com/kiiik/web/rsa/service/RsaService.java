package com.kiiik.web.rsa.service;

/**
 *作者 : iechenyb<br>
 *类描述: rsa加解密服务接口<br>
 *创建时间: 2018年11月16日
 */
public interface RsaService {
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 将密码解密<br>
	 *创建时间: 2018年11月16日
	 *@param enPassword
	 *@return
	 *@throws Exception
	 */
	public  String getPassword(String enPassword) throws Exception;
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 密码解密<br>
	 *创建时间: 2018年11月16日
	 *@param enBase64Password
	 *@return
	 *@throws Exception
	 */
	public  String decodePassword(String enBase64Password) throws Exception;
	
	public String encodePassword(String password) throws Exception;
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取公钥信息<br>
	 *创建时间: 2018年11月16日
	 *@return
	 */
	public  String  getPublicKey();
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 重新生成公秘钥<br>
	 *创建时间: 2018年11月16日
	 *@throws Exception
	 */
	public  void genRSASer() throws Exception;
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取私钥信息<br>
	 *创建时间: 2018年11月16日
	 *@return
	 *@throws Exception
	 */
	public  String getPrivateKey() throws Exception ;
}
