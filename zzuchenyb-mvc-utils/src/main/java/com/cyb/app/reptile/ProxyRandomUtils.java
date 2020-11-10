package com.cyb.app.reptile;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.cyb.utils.computer.CmdUtils;
import com.cyb.utils.file.FileUtils;
import com.cyb.utils.random.RandomUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 随机获取有用的代理地址<br>
 *创建时间: 2018年12月29日
 */
public class ProxyRandomUtils {
	Log log = LogFactory.getLog(ProxyRandomUtils.class);
	static List<String> proxy =new ArrayList<>();
	static String useableFilePath="d:/data/proxy/useable.txt";
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			System.out.println(findUseableProxyRandom());
		}
	}
	public static ProxyInfor findUseableProxyRandom(){
		if(CollectionUtils.isEmpty(proxy)){
			proxy = FileUtils.readFileToList(useableFilePath);
		}
		boolean find = false;
		int idx=0;
		while(!find){
			System.out.println("find proxy....");
			idx = RandomUtils.getNum(0,proxy.size()-1);
			if(CmdUtils.telnet(new ProxyInfor(proxy.get(idx)))){
				find = true;
			}
		}
		System.out.println("可用代理个数:"+proxy.size()+",随机索引序号:"+idx+",是否可用("+proxy.get(idx)+")："+CmdUtils.telnet(new ProxyInfor(proxy.get(idx))));
		return new ProxyInfor(proxy.get(idx));
	}
}
