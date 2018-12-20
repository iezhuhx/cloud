package io.renren.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.renren.service.SysGeneratorService;
import io.renren.utils.Constant;
import io.renren.utils.GenUtils;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 代码生成器
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午9:12:58
 */
@Controller
@RequestMapping("/sys/generator")
@Api
public class SysGeneratorController {
	@Autowired
	private SysGeneratorService sysGeneratorService;
	
	
	@ResponseBody
	@GetMapping("/replaceString")
	@ApiOperation("需要过滤掉的表名规则 ，比如 t_sys  TSys,value=x1,x2,x3...")
	public String list(String strs){
		Constant.cutName=strs;
		return Constant.cutName;
	}
	
	@ResponseBody
	@GetMapping("/queryReplaceString")
	@ApiOperation("查看已经存在的过滤规则")
	public String list(){
		return Constant.cutName;
	}
	@Autowired
	GenUtils utils;
	
	@ResponseBody
	@GetMapping("/setPro")
	@ApiOperation("设置属性值")
	public String setVal(String key,String value){
		utils.getConfig().setProperty(key, value);
		return "设置成功";
	}
	
	@ResponseBody
	@GetMapping("/getPro")
	@ApiOperation("查询属性")
	public Object getVal(String key){
		return utils.getConfig().getProperty(key);
	}
	
	
	/**
	 * 列表
	 */
	@ResponseBody
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<Map<String, Object>> list = sysGeneratorService.queryList(query);
		int total = sysGeneratorService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 生成代码
	 */
	@GetMapping("/code")
	public void code(String tables, HttpServletResponse response) throws IOException{
		byte[] data = sysGeneratorService.generatorCode(tables.split(","));

		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"renren.zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");

		IOUtils.write(data, response.getOutputStream());
	}
}
