package com.cyb.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.models.dto.builder.ApiInfoBuilder;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

/**
 * Swagger2 UI配置
 * 
 * <pre>
 * 通过访问http://your ip:8090/api/swagger-ui.html查看发布的REST接口;
 * </pre>
 * 
 * @author 许畅
 * @since JDK1.7
 * @version 2017年10月19日 许畅 新建
 */
// @Configuration
/*
 * @PropertySources({
 * 
 * @PropertySource( value = "classpath:swagger2.properties",
 * ignoreResourceNotFound = true, encoding = "UTF-8") })
 */
@EnableSwagger
public class Swagger2UIConfig {
	private SpringSwaggerConfig springSwaggerConfig;

	/**
	 * Required to autowire SpringSwaggerConfig
	 */
	@Autowired
	public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
		this.springSwaggerConfig = springSwaggerConfig;
	}

	/**
	 * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc
	 * framework - allowing for multiple swagger groups i.e. same code base
	 * multiple swagger resource listings.
	 */
	@Bean
	public SwaggerSpringMvcPlugin customImplementation() {
		return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo()).includePatterns(".*?");
	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = /*
							 * new ApiInfo( "springmvc搭建swagger",
							 * "spring-API swagger测试",
							 * "My Apps API terms of service",
							 * "534560449@qq.com", "web app",
							 * "My Apps API License URL");
							 */
				new ApiInfoBuilder().title("springmvc搭建swagger").description("springmvc搭建swagger")
						.termsOfServiceUrl("xxxxx")
						// .version("2.0")
						.build();
		return apiInfo;
	}
}