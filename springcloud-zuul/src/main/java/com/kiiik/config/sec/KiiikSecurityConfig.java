package com.kiiik.config.sec;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import com.kiiik.pub.bean.SystemConfig;
import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.utils.EnvUtils;
import com.kiiik.utils.PackageScanUtil;
import com.kiiik.web.property.KiiikProperties;

@Configuration
@EnableWebSecurity
public class KiiikSecurityConfig extends WebSecurityConfigurerAdapter {
	
	 @Resource
	 private SessionRegistry sessionRegistry;
	 
	 @Autowired
	 AuthFilterSecurityInterceptor authFilter;
	 
	 @Autowired
	 KiiikCustomizationFilter firstFilter;
	
	 @Autowired
	 KiiikProperties kiiik;
	 
	 @Autowired
	 SystemConfig infor;
	 
	 @Autowired
	 EnvUtils env;
	 
	 @Override
    protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(firstFilter, FilterSecurityInterceptor.class);
		for(String uri:infor.getSecurityExcloudUris()){
			 http.authorizeRequests().antMatchers(uri).permitAll();
		}
        /*http
        .authorizeRequests()
        .antMatchers("/user/login","/","/user/getImage","/user/toLogin").permitAll()
        .antMatchers("/rsa/**").permitAll();*/
        //开发环境免登陆
        if(KiiikContants.DEV.equals(env.getActiveProfile())){
        	/*for(int i=0;i<KiiikContants.reqs.length;i++){
        	    http.authorizeRequests().antMatchers("/"+KiiikContants.reqs[i]+"/**").permitAll();
        	}*/
        	for(String uri:PackageScanUtil.getAllControllerUri("com.kiiik")){
        		 http.authorizeRequests().antMatchers("/"+uri+"/**").permitAll();
        	}
        }
        
      /* for(int i=0;i<KiiikCustomizationFilter.paths.size();i++){
        	http.authorizeRequests().antMatchers(KiiikCustomizationFilter.paths.get(i)).permitAll();
		}*/
       
      /* http.authorizeRequests()//禁止session限制
        .antMatchers("/swagger-ui.html").authenticated();*/
        
        http.authorizeRequests()//禁止session限制
        //.antMatchers("/static/**").permitAll()
        //.antMatchers("/swagger-ui.html").permitAll()//不
        //.antMatchers().permitAll()
       /* .antMatchers("/css/**").permitAll()
        .antMatchers("/favicon.ico").permitAll()
        .antMatchers("/bootstrap/**").permitAll()
        .antMatchers("/fonts/**").permitAll()*/
        .anyRequest().authenticated()
        .and().formLogin()                    //  定义当需要用户登录时候，转到的登录页面。
		          .loginPage("/user/toLogin") // usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/") // 设置登录页面
			      .permitAll()
			     .loginProcessingUrl("user/login") // 自定义的登录接口
			      .permitAll()
                .and()
                .authorizeRequests()        // 定义哪些URL需要被保护、哪些不需要被保护
                /*.antMatchers("/demo/helloworld").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/demo/test1").access("hasRole('ROLE_TEST1')")*/
                .anyRequest()               // 任何请求,登录后可以访问
                .authenticated()
                .and()
                .csrf().disable();          // 关闭csrf防护logout可以使用GET访问;
       /* http.sessionManagement().
        maximumSessions(1).
        expiredSessionStrategy(ajaxSessionInformationExpiredStrategy);*/
      //session并发控制过滤器
       // http.addFilterAt(new ConcurrentSessionFilter(sessionRegistry,sessionInformationExpiredStrategy()),ConcurrentSessionFilter.class);
    } 
	 /*@Autowired
	 AjaxSessionInformationExpiredStrategy ajaxSessionInformationExpiredStrategy;

    @SuppressWarnings("unused")
	private AjaxSessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
			return ajaxSessionInformationExpiredStrategy;
	}*/
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

	//password加密兼容问题
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Autowired
    EnvUtils utils;
    
    @Override
    public void configure(WebSecurity web) throws Exception {
    	File dir= utils.getStaticResourcesDir();
    	if(dir!=null){
    		File files[] = dir.listFiles();
    		if(files.length>0){
            	for(int i=0;i<files.length;i++){
            		System.err.println(files[i].getName());
            		if(files[i].isFile()){
            			web.ignoring().antMatchers("/"+files[i].getName());
            		}else{
            			web.ignoring().antMatchers("/"+files[i].getName()+"/**");
            		}
            	}
            }
    	}
    	System.err.println(env.getActiveProfile());
        web.ignoring()
        .antMatchers("/druid/**","/druid/index.html")
        .antMatchers("/v2/api-docs", "/configuration/ui",
                "/swagger-resources", "/configuration/security",
                "/swagger-ui.html","/webjars/**",
                "/swagger-resources/configuration/ui")
        ;
    }
}

