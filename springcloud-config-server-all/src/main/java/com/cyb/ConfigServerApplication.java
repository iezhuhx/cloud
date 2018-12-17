package com.cyb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigServer
@EnableDiscoveryClient
@RestController
public class ConfigServerApplication {
	@RequestMapping("/")
	public String index(){
		return "/index";
	}
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}