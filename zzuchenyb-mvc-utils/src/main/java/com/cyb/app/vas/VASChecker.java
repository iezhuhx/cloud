package com.cyb.app.vas;

import com.alibaba.fastjson.JSONArray;
import com.cyb.utils.http.HttpClientUtils;
import com.cyb.utils.http.MyHttpClient;

/**
 * 描述：
 *
 * @author iechenyb
 * @create --
 */
public class VASChecker {
    static  String addr="http://211.95.44.232:8080/VASDataCenter/qutoes/infor.html";
    public static void main(String[] args) {
        String result = MyHttpClient.doGet(addr);
        result = HttpClientUtils.doGet(addr);
        System.out.println(result);
       long total =  JSONArray
               .parseArray(result).getJSONObject(0)
               .getLong("队列剩余数据");
        System.out.println(total);
        boolean login = false;
        boolean over =false;
        login =  JSONArray
                .parseArray(result).getJSONObject(0)
                .getBoolean("行情服务器正常登录状态");
        over =  JSONArray
                .parseArray(result).getJSONObject(0)
                .getBoolean("实时行情任务是否处理结束");

        if(!over&&login){
            if(total<=0){
                System.out.println("实时行情处理异常！");
            }else{
                System.out.println("实时行情处理正常！");
            }
        }else if(!over&&!login){
            System.out.println("实时行情服务器登录异常！");
        }
    }
}
