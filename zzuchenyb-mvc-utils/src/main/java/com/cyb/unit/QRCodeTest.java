package com.cyb.unit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.cyb.utils.qrcode.QRCodeUtil;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2019年1月4日
 */
public class QRCodeTest {
	Log log = LogFactory.getLog(QRCodeTest.class);

	@Test
	public void test() {
		try {
			String csdn = "https://blog.csdn.net/iechenyb";
			// 生成带logo 的二维码
			QRCodeUtil.encode(csdn, "d:/data/qrcode/zzuchenyb.jpg", "d:/data/qrcode", true);
			// 生成不带logo 的二维码
			QRCodeUtil.encode(csdn, "", "d:/data/qrcode", true);
			// 指定二维码图片，解析返回数据
			System.out.println(QRCodeUtil.decode("D:/WPS/75040887.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
