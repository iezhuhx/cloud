package com.kiiik.web.rsa.service.impl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.utils.MD5Util;
import com.kiiik.utils.RSAUtil;
import com.kiiik.web.rsa.service.RsaService;
/**
 *作者 : iechenyb<br>
 *类描述: 将公秘钥存入内存方案<br>
 *创建时间: 2018年11月16日
 */
@Service
public class RsaMemStoreServiceImpl  implements RsaService{
	Log log = LogFactory.getLog(RsaMemStoreServiceImpl.class);

	@Override
	public String getPassword(String enPassword) throws Exception {
		return MD5Util.md5Encode(decodePassword(enPassword)).toUpperCase();//解密后的字符串需要md5加密
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

	@Override
	public String getPublicKey() {
		if(KiiikContants.RSASER==null){
			try {
				genRSASer();
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
		return RSAUtil.getPublicKey(KiiikContants.RSASER.getPublicKey());
	}

	@Override
	public synchronized void genRSASer() throws Exception {
		RSAUtil.genKeyPairMap();//刷新公钥和私钥信息
	}

	@Override
	public String getPrivateKey() throws Exception {
		if(KiiikContants.RSASER==null){
				genRSASer();
		}
		return RSAUtil.getPrivateKey(KiiikContants.RSASER.getPrivateKey());
	}

	@Override
	public String encodePassword(String password) throws Exception {
		return RSAUtil.encryptedDataOnJava(password,
				getPublicKey());
	}
}
