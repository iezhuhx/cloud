package com.kiiik.web.router;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author iechenyb<br>
 * @Desc 类描述<br>
 * @CreateTime 2020年11月10日 上午11:54:07
 */
@RestController
@RequestMapping("/route")
public class RouteController {
	Log log = LogFactory.getLog(RouteController.class);
	
	@Autowired
	private RefreshRouteService refreshRouteService;

	@GetMapping("/refresh")
	public String refresh() {
		refreshRouteService.refreshRoute();
		return "refresh";
	}
}
