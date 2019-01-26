package com.cyb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConcurrentController {
	@RequestMapping("")
	public String home(){
		return "hello";
	}
}
