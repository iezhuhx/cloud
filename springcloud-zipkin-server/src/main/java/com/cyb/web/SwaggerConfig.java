package com.cyb.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月14日
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	Log log = LogFactory.getLog(SwaggerConfig.class);

	//@Bean
	public Docket createRestApi() {
		log.info("create api!");
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any()) // 对所有api进行监控none
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		log.info("api Info!");
		return new ApiInfoBuilder().title("东航金控-增值服务中心服务中心")
				.description("接口中心仅对测试环境开放，正式环境请关闭!")
				.termsOfServiceUrl("zzuchenyb@sina.com")
				.version("1.0")
				.build();
	}
}
