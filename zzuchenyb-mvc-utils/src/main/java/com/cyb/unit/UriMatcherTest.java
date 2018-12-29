package com.cyb.unit;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.AntPathMatcher;

/**
 * 作者 : iechenyb<br>
 * 类描述:get 请求没有 request body 所以 json请求只能用post<br>
 * 创建时间: 2018年5月15日
 */
public class UriMatcherTest {
	static AntPathMatcher matcher = new AntPathMatcher();
	static List<String> PatternList = new ArrayList<String>();
	static List<String> UriList = new ArrayList<String>();

	public static void main(String[] args) {
		
		//System.out.println("/api2/webjars".split("/")[1]);
		initPattern();
		initUri();
		for (String pattern : PatternList) {
			System.out.println("pattern:" + pattern);
			System.out.println("-------------------------------------------");
			for (String uri : UriList) {
				System.out.println(matcher.match(pattern, uri) + "\t\t\t" + uri);
			}
		}
	}

	public static void initUri() {
		/*UriList.add("/api2/webjars/springfox-swagger-ui/css/screen.css");
		UriList.add("/api2/swagger-resources/configuration/ui");
		UriList.add("/api2/swagger-resources");
		UriList.add("/api2/swagger-resources/configuration/security");
		UriList.add("/api2/swagger-ui.html");
		UriList.add("/api2/webjars/springfox-swagger-ui/css/typography.css");
		UriList.add("/api2/webjars/springfox-swagger-ui/css/screen.css");
		UriList.add("/api2/webjars/springfox-swagger-ui/css/print.css");
		UriList.add("/api1/webjars/springfox-swagger-ui/images/logo_small.png");
		*/
		/*UriList.add("/user/add");
		UriList.add("/user/delete");
		UriList.add("/user/id/1");
		UriList.add("/user/add/main.jsp");
		UriList.add("/any.jsp");
		UriList.add("/first/second/third/fourth/five/any.jsp");*/
		UriList.add("/1.jpg");
		UriList.add("/1/2/3/4/5.jpg");
	}

	public static void initPattern() {
		PatternList.add("/**/*.jpg");
//		PatternList.add("/user/*");//仅仅匹配两层
//		PatternList.add("/user/**");//匹配以user为根的无限层级
//		PatternList.add("/user/id/**");
//		PatternList.add("/user/{id}/{name}");
//		PatternList.add("/*.jsp");
//		PatternList.add("/*/**/*.jsp");
//		PatternList.add("/*/**/webjars/**");
		
		String paths = "/*/**/v2/api-docs/**,/*/**/configuration/ui/**,/*/**/swagger-resources/**,/*/**/configuration/security/**,/*/**/swagger-ui.html/**,/*/**/webjars/**,/*/**/swagger-resources/configuration/ui/**";
		//paths = "/*/v2/api-docs/**,/*/configuration/ui/**,/*/swagger-resources/**,/*/configuration/security/**,/*/swagger-ui.html/**,/*/webjars/**,/*/swagger-resources/configuration/ui/**";
		String path[] = paths.split(",");
		for(int i=0;i<path.length;i++){
//			PatternList.add(path[i]);
		}
	}
}
