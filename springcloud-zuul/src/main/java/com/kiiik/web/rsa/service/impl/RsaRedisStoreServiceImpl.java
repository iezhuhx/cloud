package com.kiiik.web.rsa.service.impl;
import java.security.KeyPair;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.util.StringUtils;

import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.utils.RSAUtil;
import com.kiiik.web.rsa.service.RsaService;
/**
 *作者 : iechenyb<br>
 *类描述: 将公秘钥存入内存方案<br>
 *创建时间: 2018年11月16日
 */
//@Service
public class RsaRedisStoreServiceImpl  implements RsaService{
	Log log = LogFactory.getLog(RsaRedisStoreServiceImpl.class);
    
	
	@Autowired
	CacheManager cacheManager;
	
	
	@Override
	public String getPassword(String enPassword) throws Exception {
		return decodePassword(enPassword);
	}

	@Override
	public String decodePassword(String enBase64Password) throws Exception {
		String res = RSAUtil.decryptDataOnJava(enBase64Password, getPrivateKey());
		log.debug("解密内容"+res);
		if(!StringUtils.isEmpty(res)){
			return res;
		}else{
			return KiiikContants.BLANK;
		}
		
	}
    private String key = "rsakiiik";
    private String pubKey = "pub";
    private String priKey = "pri";
    
	@Override
	public String getPublicKey() {
		if(cacheManager.getCache(key).get(pubKey)==null){
			try {
				genRSASer();
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
		return cacheManager.getCache(key).get(pubKey).get().toString();
	}

	@Override
	public synchronized void genRSASer() throws Exception {
	  KeyPair kp =	RSAUtil.genKeyPair();//刷新公钥和私钥信息
	  cacheManager.getCache(key).put(priKey, RSAUtil.getPrivateKey(kp.getPrivate()));
	  cacheManager.getCache(key).put(pubKey, RSAUtil.getPublicKey(kp.getPublic()));
	}

	@Override
	public String getPrivateKey() throws Exception {
		if(cacheManager.getCache(key).get(priKey)==null){
				genRSASer();
		}
		return cacheManager.getCache(key).get(priKey).get().toString();
	}

	@Override
	public String encodePassword(String password) throws Exception {
		return RSAUtil.encryptedDataOnJava(password,
				getPublicKey());
	}
}
