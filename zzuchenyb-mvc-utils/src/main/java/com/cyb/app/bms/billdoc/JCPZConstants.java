package com.cyb.app.bms.billdoc;
import java.util.HashMap;
import java.util.Map;

public class JCPZConstants {
  public static Map<String,String> SPEC_COR_REPLACE_RULE=new HashMap<>();
  
  public static final String MOBILEFILE_FILE="mobilefile_path";

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
    
    
    SPEC_COR_REPLACE_RULE.put("8801106", "0513");
    SPEC_COR_REPLACE_RULE.put("8801108", "0513");
    
  }

  public static void main(String[] args) {
    System.out.println(ACC_LOCATION);
  }


}
