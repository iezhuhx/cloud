package com.cyb.web.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cyb.pub.SpringContextUtil;
import com.cyb.web.bean.SessionUser;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年9月3日上午9:03:14
 */
@Controller
@RequestMapping("api")
public class ApiController extends BaseController{

	Log log = LogFactory.getLog(ApiController.class);

	@GetMapping("index")
	public ModelAndView index(String username) {
		System.out.println("查看子页面的首页");
		ModelAndView view = new ModelAndView();
		view.addObject("name", super.getUser());
		view.addObject("infor", super.getRemoteUser());
		Map<String,String> user = new LinkedHashMap<String,String>();
		user.put("name", "姓名");
		user.put("age", "年龄");
		user.put("sex", "性别");
		user.put("birthday", "生日");
		user.put("phonenumber", "手机");
		view.addObject("map", user);
		List<String> userinfo = new ArrayList<String>();
		userinfo.add("周美玲");
		userinfo.add("32");
		userinfo.add("女");
		userinfo.add("1985");
		userinfo.add("19850014665");
		view.addObject("list", userinfo);
		List<Map<String,Integer>> outerList = new ArrayList<Map<String,Integer>>();
		Map<String,Integer> innerMap = new HashMap<String,Integer>();
		for (int i = 0; i < 10; i++) {
			innerMap.put("1", i);
			innerMap.put("1", i++);
			i++;
			outerList.add(innerMap);
		}
		view.setViewName("/index");
		view.addObject("listmap", outerList);
		return view;
	}

	@GetMapping("query")
	@ResponseBody
	public String getP(String username) {
		return "信息列表!";
	}

	@ResponseBody
	@PostMapping("add")
	public String add(String name) {
		return "添加信息成功！"+name;
	}

	@ResponseBody
	@DeleteMapping("delete")
	public String delete(String id) {
		return "删除信息成功!"+id;
	}
	@Autowired
	SpringContextUtil util;
	@ResponseBody
	@GetMapping("active")
	public String active() {
		return util.getActiveProfile();
	}

	@ResponseBody
	@GetMapping("update")
	public String update(String name) {
		return "更新信息成功！";
	}

	@ResponseBody
	@GetMapping("free")
	public String free(String name) {
		return "free！";
	}
	
	@ResponseBody
	@PostMapping("postString")
	public String postString(String name) {
		return "free！";
	}
	
	@ResponseBody
	@PostMapping("postObject")
	public SessionUser postString(SessionUser user) {
		return user;
	}
	
	@ResponseBody
	@PostMapping("postRequestBodyObject")
	public SessionUser postRequestBodyObject(@RequestBody SessionUser user) {
		return user;
	}

	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file, Model model) {
		if (file.isEmpty()) {
			model.addAttribute("message", "The file is empty!");
			return "/uploadStatus";
		}
		try {
			byte[] bytes = file.getBytes();
			String dir = System.getProperty("user.dir");
			Path path = Paths.get(dir+"/" + file.getOriginalFilename());
			Files.write(path, bytes);
			model.addAttribute("message", file.getOriginalFilename() + "上传succes！");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/uploadStatus";
	}
}
