package com.kiiik.utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月16日
 */
//https://www.cnblogs.com/hujunzheng/p/9790588.html
public class KiiikAnnotationUtils {
	Log log = LogFactory.getLog(KiiikAnnotationUtils.class);
	public static void main(String[] args) {
		AnnotatedElementUtils.findAllMergedAnnotations(null, null);
	}
}
