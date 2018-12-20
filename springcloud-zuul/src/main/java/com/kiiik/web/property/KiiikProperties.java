package com.kiiik.web.property;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月20日
 */
@Component
public class KiiikProperties {
	/*@Value("${spring.profiles.active}")
	public String environment;//环境
   */	
	@Value("${checkSession}")
	public boolean checkSession;
}
