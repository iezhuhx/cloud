package com.cyb.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
/**
 * 
 * @author iechenyb
 * 根据实际环境选择最佳的服务方
 * http://localhost:8884/service-ribbon/m1?a=3&b=2 通过路由进行负载均衡
 * http://localhost:8884/service-ribbon/m2?a=3&b=2 手动设置调用规则
 */
@RestController
public class ConsumerController {
    Log log = LogFactory.getLog(getClass());
    static int count =0;
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired  
    private LoadBalancerClient loadBalancerClient; 
    
    @GetMapping("/")
	public String hello(){
		Map<String,String> ret = new HashMap<>();
		ret.put("infor", "Hello ,I am zuul Root!");
		ret.put("tip", "please input /service/info 查看是否服务进行负载均衡(默认轮询策略)");
		return ret.toString();
	}
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 名称为SERVICE-B的服务有两个，但是服务名称都为SERVICE-B，
     *另有一个Service-B2工程启动时服务名称也为SERVICE-B,默认采用轮询的方式，
     *所以返回值会在serviceb和servcieb2之间来回切换<br>
     *创建时间: 2017年7月15日hj12
     *@param a
     *@param b
     *@return
     */
    @RequestMapping(value = "/m1", method = RequestMethod.GET)
    public String m1(@RequestParam Integer a,@RequestParam Integer b,HttpServletRequest req) {
    	//随机访问策略localhost:8881，注意restTemplate的调用函数的写法，直接是服务名，而不是http服务地址
    	String serviceId="SERVICE-B";//同一个B服务有两个，任意选择一个。
        return restTemplate.getForEntity("http://"+serviceId+"/add?a="+a+"&b="+b, String.class).getBody()+" ribbon sessionId is "+req.getSession().getId();
    	
    }
    
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public String session(HttpServletRequest req) {
    	String serviceId="SERVICE-C";//同一个C服务有两个，任意选择一个。
        return restTemplate.getForEntity("http://"+serviceId+"/sessions", String.class).getBody()+" ribbon sessionId is "+req.getSession().getId();
    	
    }
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 手动定义规则调用服务，a为偶数时，调用service-a服务，奇数是调用service-b服务<br>
     *创建时间: 2017年7月15日hj12
     *@param a
     *@param b
     *@return
     */
    @RequestMapping(value = "/m2", method = RequestMethod.GET)
    public String m2(@RequestParam Integer a,@RequestParam Integer b,HttpServletRequest req) {
    	log.info("负载均衡开始！"+count);
    	count++;
    	String serviceId="SERVICE-B";
    	if(a%2==0){
    		serviceId="SERVICE-A";
    	}
    	ServiceInstance si =  this.loadBalancerClient.choose(serviceId);
    	System.out.println("http://"+si.getHost()+":"+si.getPort()+"/"+","+si.getServiceId()+","+si.getUri());
    	//随机访问策略localhost:8881，注意restTemplate的调用函数的写法，直接是服务名，而不是http服务地址
        return restTemplate.getForEntity("http://"+si.getServiceId()+"/add?a="+a+"&b="+b, String.class).getBody()+" ribbon sessionId is "+req.getSession().getId();
    	
    }
    
}