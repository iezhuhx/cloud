package com.cyb.app.bms.zuul;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年8月13日 下午1:49:58
 */
public class Test {
	Log log = LogFactory.getLog(Test.class);
	 public static final Map<String,Integer> ACC_LOCATION= new HashMap<String, Integer>();
	  //特法分账单资金账号所在位置定义
	  static{
	    ACC_LOCATION.put("amassets",2);//1
	    ACC_LOCATION.put("amassetsdetails",2);//2
	    ACC_LOCATION.put("cusaccount",2);//3
	    ACC_LOCATION.put("cuscode",2);//4
	    ACC_LOCATION.put("cusfund",2);//5
	    ACC_LOCATION.put("customer",4);//6
	    ACC_LOCATION.put("fundchg",2);//7
	    ACC_LOCATION.put("holddata",2);//8
	    ACC_LOCATION.put("holddetails",2);//9
	    ACC_LOCATION.put("liquiddetails",2);//10
	    ACC_LOCATION.put("mortgagedetails",2);//11
	    ACC_LOCATION.put("optexerdata",2);//12
	    ACC_LOCATION.put("optholddata",2);//13
	    ACC_LOCATION.put("optholddetails",2);//14
	    ACC_LOCATION.put("optliquiddetails",2);//15
	    ACC_LOCATION.put("opttrddata",2);//16
	    ACC_LOCATION.put("otherfund",2);//17
	    ACC_LOCATION.put("trddata",2);//18
	    //25-7=18 去掉换汇、交割无数据文件
	  }

	  public static void main(String[] args) {
	    //System.out.println(ACC_LOCATION);
		  System.out.println(JobMain.checkSuccess("20190101"));
	  }
}
