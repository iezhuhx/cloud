package com.cyb.utils.text;

import java.text.MessageFormat;
import java.util.Locale;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年9月11日
 */
public class ExpressionUtils {
	Log log = LogFactory.getLog(ExpressionUtils.class);

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		MessageFormat mf = new MessageFormat("aaa{0}{0}", Locale.US);
		Object arr[] = { "he" };
		System.out.println(mf.format(arr));

		String booleanString = "{0}>1 && {0}<=5";
		System.out.println(Boolean.valueOf(mf.format(booleanString, 6)));
		System.out.println(Boolean.valueOf(mf.format(booleanString, 2)));

		String expression = "value>1 && value<=5";
		Boolean rs = checkSection("2", expression);
		for (int i = 1; i < 8; i++) {
			rs = checkSection(i + "", expression);
			System.out.println(i + ",rs=" + rs);
		}
		calStringExpressionValue("10 * 2 + 6 / (3 - 1)");
	}

	/**
	 * 
	 * @author: Longjun
	 * @Description: 将${money>=2000&&money<=4000}字符串截取成
	 *               "money>=2000&&money<=4000"， 然后判断一个数值字符串是否在此区间内
	 * @date:2016年3月21日 上午11:25:32
	 */
	public static Boolean checkSection(String value, String expression) {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		engine.put("value", value);
		boolean eval = false;
		try {
			eval = (Boolean) engine.eval(expression);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return eval;
	}

	public static void calStringExpressionValue(String expression) {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");
		if (StringUtils.isEmpty(expression)) {
			expression = "10 * 2 + 6 / (3 - 1)";
		}
		try {
			String result = String.valueOf(scriptEngine.eval(expression));
			System.out.println(result);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
}
