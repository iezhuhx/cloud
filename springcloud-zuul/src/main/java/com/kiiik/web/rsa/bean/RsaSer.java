package com.kiiik.web.rsa.bean;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import com.kiiik.utils.RSAUtil;
/**
 *作者 : iechenyb<br>
 *类描述: rsa证书信息<br>
 *创建时间: 2018年11月16日
 */
public class RsaSer {
	private RSAPublicKey publicKey;
	private RSAPrivateKey privateKey;
	
	public RsaSer(RSAPublicKey publicKey,RSAPrivateKey privateKey){
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}
	
	public RSAPublicKey getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(RSAPublicKey publicKey) {
		this.publicKey = publicKey;
	}
	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(RSAPrivateKey privateKey) {
		this.privateKey = privateKey;
	}
	
	public String toString(){
		return new StringBuffer("私钥：")
				.append(RSAUtil.getPrivateKey(getPrivateKey())+"\n")
				.append("公钥："+RSAUtil.getPublicKey(getPublicKey())).toString();
	}
}
