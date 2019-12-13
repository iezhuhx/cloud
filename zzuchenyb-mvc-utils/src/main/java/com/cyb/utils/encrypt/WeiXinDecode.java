package com.cyb.utils.encrypt;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.alibaba.fastjson.JSONObject;
public class WeiXinDecode {
	public static void main(String[] args) {
		String encryptedData="PY3BSGF4aB87BSyLJBa6EscjPUEx4N+kOGqlp7QoaByx20gInwFgJ5gxAGXOp5Zh05Nbobxq/ovNgvHIJYVM+G+50t765s37RVe0SOydImia6prlLfdaAW5Rcy7325N/NoYVtOhhBliCCHADuFApG1bVgTczJJc8Uy3aFAozwOBVHJQS6gCrOw6DO5uRlicpypZRwXbBaW5ARPx4zC2UWHpYyFH1AnrD2iKk2ryLwaA0GAv5phUhSbGiesA4Amcw3qPW/nlqNSfjfWw0f1kNzJaEaqrUj4xkfIxvV/hc6JsLdJJopDTYKDXfAW9T1/ML/+n573saJxuhMomLxpvy+Wor8NcmSfDfgAp80EuwsLYv8W28r4afsKnQWe2LSh6poX4UwUEGXYGQBP/xs9qT6k2Q/EoplWygMBNVFQvo0cNZaEGAISYMyzR/8QmC2urLiGdIRqTY9SH1XhlQ+RjJJF+awcxlibuj4A1C5CbS6w0=";
		String sessionkey="8467d2221a2a39f84b38431740295be77d8b89e4";
		String iv="kZF1bc+TOCqOZqKE+LT/1w==";
	    System.out.println(getUserInfo(encryptedData,sessionkey,iv));
	
	}
	
	/**
     * 获取信息
     */
    public static JSONObject getUserInfo(String encryptedData,String sessionkey,String iv){
        // 被加密的数据
        byte[] dataByte = Base64.decodeBase64(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decodeBase64(sessionkey);
        // 偏移量
        byte[] ivByte = Base64.decodeBase64(iv);
        try {
               // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return null;
    }
}
