package com.cyb.utils.reflection.service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月6日
 */
public class AnimalSayImpl implements SayService{
	Log log = LogFactory.getLog(AnimalSayImpl.class);

	@Override
	public String say(String content) {
		log.info("Animal say is "+content);
		return content;
	}

	@Override
	public String say(String name, String content) {
		log.info("Animal say is "+content+" to "+name);
		return name+","+content;
	}
}
