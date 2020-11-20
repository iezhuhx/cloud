package com.cyb.app.vedio;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyb.utils.file.FileUtils;
/**
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年4月30日 上午10:17:46
 */
public class RoleAna {
	Log log = LogFactory.getLog(RoleAna.class);
	/**
	 * 
	 *@Author iechenyb<br>
	 *@Desc 说点啥<br>
	 *@CreateTime 2020年4月30日 上午10:18:22
	 *@param a
	 *@param b
	 *@modify  新增方法时不用填写，修改时新增一个modify，需要注明修改人、时间和修改内容
	 */
	public void x(int a,int b){
		
	} 
	
	public static void main(String[] args) throws IOException {
		JSONArray arr  = JSON.parseArray(FileUtils.read("d://data//zuul//role.txt"));
		for(int i=0;i<arr.size();i++){
			JSONObject cur = arr.getJSONObject(i);
			System.out.println(cur.get("id")+"\t"+cur.get("roleName")+"\t"+cur.get("description"));
		}
		
	}
	
	
}
