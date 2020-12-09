package com.cyb.app.holiday.imp;

import java.util.HashMap;
import java.util.Map;

import com.cyb.app.holiday.Holiday;
import com.cyb.utils.date.DateUnsafeUtil;
import com.cyb.utils.text.ELUtils;

public class KiiikWorkDaySQLStrategy implements GenerSqlStrategy{

	static String sql_pattern="insert into tb_work_day (DAY, IS_WORK_DAY) values (to_date('${rq}', 'yyyy-mm-dd'), ${type});"; 
	
	@Override
	public String genSql(Holiday holiday) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("rq", DateUnsafeUtil.str8to10(DateUnsafeUtil.date2long8(holiday.getRq()).toString(), "-"));
		param.put("type",holiday.isKiiikTradeDay());
		String sql = ELUtils.el(sql_pattern,param);
		System.out.println(sql);
		return sql;
	}

}
