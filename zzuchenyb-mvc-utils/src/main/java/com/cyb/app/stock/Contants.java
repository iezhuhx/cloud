package com.cyb.app.stock;

import java.util.List;
import java.util.Map;

public class Contants {
  public static String WEBPATH = null;
  public static List<Map<String,Object>> STOCKLIST= null;
  public static Map<String,Object> STOCKMAP = null;
  public static int NAME = 0;
  public static int OPEN = 1;
  public static int PRECLOSE = 2;
  public static int PRICE = 3;
  public static int HIGH = 4;
  public static int LOW = 5;
  public static int BUYONE = 6;
  public static int VOLUME = 7;
  public static int TURNVOLUME = 8;
  public static int COLUMNCASH = 9;
  public static int DAY = 30;
  public static int TIME = 31;
  //http://blog.sina.com.cn/s/blog_49d43d390102drix.html
  /*0：�?�大秦铁路�?�，股票名字�?
  1：�??27.55″，今日�?盘价�?
  2：�??27.25″，昨日收盘价；
  3：�??26.91″，当前价格�?
  4：�??27.55″，今日�?高价�?
  5：�??26.20″，今日�?低价�?
  6：�??26.91″，竞买价，即�?�买�?”报价；
  7：�??26.92″，竞卖价，即�?�卖�?”报价；
  8：�??22114263″，成交的股票数，由于股票交易以�?百股为基本单位，�?以在使用时，通常把该值除以一百；
  9：�??589824680″，成交金额，单位为“元”，为了�?目了然，通常以�?�万元�?�为成交金额的单位，�?以�?�常把该值除以一万；
  10：�??4695″，“买�?”申�?4695股，�?47手；
  11：�??26.91″，“买�?”报价；
  12：�??57590″，“买二�??
  13：�??26.90″，“买二�??
  14：�??14700″，“买三�??
  15：�??26.89″，“买三�??
  16：�??14300″，“买四�??
  17：�??26.88″，“买四�??
  18：�??15100″，“买五�??
  19：�??26.87″，“买五�??
  20：�??3100″，“卖�?”申�?3100股，�?31手；
  21：�??26.92″，“卖�?”报�?
  (22, 23), (24, 25), (26,27), (28, 29)分别为�?�卖二�?�至“卖四的情况�?
  30：�??2008-01-11″，日期�?
  31：�??15:05:32″，时间�?*/
  
  
  //********大盘指数*******
 /*
  * http://hq.sinajs.cn/list=s_sh000001 上证
  * http://hq.sinajs.cn/list=s_sz399004  深证
  	服务器返回的数据为：
  	var hq_str_s_sh000001="上证指数,3094.668,-128.073,-3.97,436653,5458126";
  	数据含义分别为：(0)指数名称，(1)当前点数，(2)当前价格，(3)涨跌率，(4)成交量（手），(5)成交额（万元）；*/
  public static int GRAILNAME = 0;
  public static int GRAILPOINT = 1;
  public static int GRAILPRICE = 2;
  public static int GRAILFLUCTUATE= 3;
  public static int GRAILTRADENUM = 4;
  public static int GRAILTRADEMONEY= 5;
  
  public static int ENNAME_ = 0;
  public static int ZNNAME_ = 1;
  public static int OPEN_ = 2;
  public static int PRECLOSE_ = 3;
  public static int PRICE_ = 4;
  public static int HIGH_ = 5;
  public static int LOW_ = 6;

  public static int CJE = 11;
  public static int CJL = 12;
  public static int DAY_ = 17;
  public static int TIME_ = 18;
  
  public static String 市盈率 = "普通股每股市场价格÷普通股每年每股盈利,假设某股票的市价为24元，"
  		+ "而过去12个月的每股盈利为3元，则市盈率为24/3=8,即每付出8元可分享1元的盈利。"
  		+ "该股票被视为有8倍的市盈率，即每付出8元可分享1元的盈利。";
  public static String 市净率="股票市价/每股净资产,数值较低的投资价值高，相反，则投资价值低。参考投资空间";
  public static String  商誉="指企业在同等条件下，能获得高于正常投资报酬率所形成的价值。";
  public static String 净资产收益率ROE ="净利润(盈利)/净资产(所有者权益+少数股东权益)";// 杜邦分解成ROE=净利率*资产周转率*(杠杆率+1)
  
  public static String 买入后成本价Y="（第一次买入价格*买入数量+第二次买入价格*买入数量+第三次买入价格*买入数量+总买入手续费）/买入总数=(5.35*300+5.16*200+4.99*200+总买入手续费X)/700=5.193+X/700";
  public static String  卖出后成本价="【买入股票平均成本价*卖出后剩下股票数量—（卖出股票价格-买入股票平均成本价）*卖出股数+卖出手续费】/剩下股票数量=【500Y—（5.17—Y)200+卖出手出费Z】/500";
}
