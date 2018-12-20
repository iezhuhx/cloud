package com.kiiik.web.example.service.impl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.kiiik.pub.bean.res.ResultBean;
import com.kiiik.web.example.service.TestService;



/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月10日
 */
@Service
public class TestServiceImpl implements TestService{
	Log log = LogFactory.getLog(TestServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public ResultBean<String> exeServiceOfTask(String param) {
		return new ResultBean<String>(param).success("执行成功");
	}
}
