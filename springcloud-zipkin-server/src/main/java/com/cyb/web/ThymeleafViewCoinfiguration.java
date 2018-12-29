package com.cyb.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年9月3日上午10:06:05
 */
@Configuration
public class ThymeleafViewCoinfiguration {
	Log log = LogFactory.getLog(ThymeleafViewCoinfiguration.class);

	/**
	 * 设置视图解析器
	 * 
	 * @param templateEngine
	 * @return
	 */
	@Bean
	public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine);
		return resolver;
	}

	/**
	 * 设置模板引擎
	 * 
	 * @param templateResolver
	 * @return
	 */
	@Bean
	public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver templateResolver) {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(templateResolver);
		/*Set<IDialect> d = new HashSet<IDialect>();
		d.add(new SpringSecurityDialect());
		engine.setAdditionalDialects(d);*/
		return engine;
	}

	/**
	 * 模板解析引擎
	 * 
	 * @return
	 */
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setPrefix("classpath:/templates");//设置地址前缀
		resolver.setSuffix(".html");// 设置后缀
		resolver.setCacheable(false);// 设置不缓存
		resolver.setTemplateMode("HTML5");
		return resolver;

	}
}
