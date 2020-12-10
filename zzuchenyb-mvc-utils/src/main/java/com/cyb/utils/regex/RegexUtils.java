package com.cyb.utils.regex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年12月9日 下午4:17:54
 */
import org.springframework.util.AntPathMatcher;
public class RegexUtils extends MatcherUtils{
	Log log = LogFactory.getLog(RegexUtils.class);
	static AntPathMatcher pathMatcher = new AntPathMatcher();
	
	public static  boolean isMath(String content,String regex){
		return pathMatcher.match(regex, content);
	}
	
	 public static void main(String[] args) {
		 System.out.println(isMath("()", "(*)"));
		System.out.println(isMath("(中欧012)", "(*)"));
		System.out.println(isMath("中欧)", "(*)"));
		System.out.println(isMath("(中欧", "(*)"));
	}
}
