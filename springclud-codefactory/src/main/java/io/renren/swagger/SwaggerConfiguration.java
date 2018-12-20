package io.renren.swagger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
 *创建时间: 2018年11月9日
 */
/*@Configuration  
@EnableSwagger2 */ 
public class SwaggerConfiguration extends WebMvcConfigurerAdapter {  
  
    @Value("${swagger2.basePackage:com.xxx}")  
    private String swagger2BasePackage;  
    @Value("${swagger2.title:系统API文档}")  
    private String swagger2Title;  
    @Value("${swagger2.api.version:1.0}")  
    private String apiVersion;  
  
    @Override  
    public void addResourceHandlers(ResourceHandlerRegistry registry) {  
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");  
        registry.addResourceHandler("/webjars*").addResourceLocations("classpath:/META-INF/resources/webjars/");  
    }  
  
    @Bean  
    public Docket createRestApi() {  
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())  
                .select().apis(RequestHandlerSelectors.basePackage(swagger2BasePackage))  
                .paths(PathSelectors.any()).build();  
    }  
  
    private ApiInfo apiInfo() {  
        return new ApiInfoBuilder().title(swagger2Title).version(apiVersion).build();  
    }  
}