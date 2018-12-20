package com.kiiik.pub.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.kiiik.utils.RequestUtils;
/**
 * @author iechenyb
 * @time 2018年9月05日 12:58:46
 * 启动监听
 */
@Component
public class CenterAuthApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	Environment env;
	

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		RequestUtils.initPatternList(env.getProperty("path.neednot.to.record.log"));
	}
}
