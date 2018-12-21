package com.cyb.utils.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * double的计算不精确，会有类似0.0000000000000002的误差，正确的方法是使用BigDecimal或者用整型
 * 整型地方法适合于货币精度已知的情况，比如12.11+1.10转成1211+110计算，最后再/100即可
 * 以下是摘抄的BigDecimal方法:
 */
public class DoubleUtil extends DataUtils implements Serializable {
    private static final long serialVersionUID = -3345205828566485102L;
    // 默认除法运算精度
    private static final Integer DEF_DIV_SCALE = 2;

    /**
     * 提供精确的加法运算。
     *
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static Double add(Double value1, Double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static double sub(Double value1, Double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static Double mul(Double value1, Double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后10位，以后的数字四舍五入。
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @return 两个参数的商
     */
    public static Double divide(Double dividend, Double divisor) {
        return divide(dividend, divisor, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @param scale    表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static Double divide(Double dividend, Double divisor, Integer scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(dividend));
        BigDecimal b2 = new BigDecimal(Double.toString(divisor));
        return b1.divide(b2, scale,RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 提供指定数值的（精确）小数位四舍五入处理。
     *
     * @param value 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double value,int scale){
        if(scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(value));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale, RoundingMode.HALF_UP).doubleValue();
    }
    public static String E2Double(String number){
    	 BigDecimal db = new BigDecimal(number);  
	     return db.toPlainString();  
    }
    public static String E2Double(double number){
   	     BigDecimal db = new BigDecimal(number);  
	     return db.toPlainString();  
   }
    public static void main(String[] args) {
    	
    	System.out.println(Double.valueOf("0.0"));
		/*double val1=0.05;
		double val2=0.01;
		System.out.println(DoubleUtil.divide(val1, val2));
		System.out.println(DoubleUtil.divide(val1, val2,8));
		System.out.println(DoubleUtil.round(val1/val2,8));
		System.out.println(val1/val2);
		System.out.println(DoubleUtil.sub(val1, val2));System.out.println(val1-val2);
		System.out.println(DoubleUtil.add(val1, val2));System.out.println("comon+="+(val1+val2));
		System.out.println(DoubleUtil.mul(val1, val2));System.out.println(val1*val2);
		 String aa = "-4.99183553218834E-7";  
		 double bb = -4.99183553218834E-7;
		 System.out.println(E2Double(aa));  
	     System.out.println(E2Double(bb));  */
    	//无限循环小数会报错new BigDecimal(10).divide(new BigDecimal(3))
    	BigDecimal big = new BigDecimal(10).divide(new BigDecimal(3),8,RoundingMode.HALF_DOWN);
    	//3.33333333
    	big = new BigDecimal(3).divide(new BigDecimal(4),1,RoundingMode.HALF_DOWN);
    	//0.75->0.7
    	big = new BigDecimal(3).divide(new BigDecimal(9),1,RoundingMode.HALF_UP);
    	//0.75->0.8
    	big = new BigDecimal(10/3.0);//.multiply(new BigDecimal(12));//默认16位小数
    	System.out.println(big.doubleValue());
    	//testBigDecimal();
    	big = new BigDecimal(0.123456789);//
    	/*big.setScale("0.123456789",BigDecimal.ROUND_HALF_UP);
    	System.out.println(big.doubleValue());*/
    	System.out.println(divide(1d,3d,8));
    	double d1 = 0.00000000;//8
    	double d2 = 0.000000001;//9
    	double d3 = 0;
    	System.out.println(new BigDecimal(d3).compareTo(new BigDecimal(d1)));
    	System.out.println(BigDecimal.ZERO.compareTo(new BigDecimal(d1)));
    	System.out.println(new BigDecimal(d1).compareTo(new BigDecimal(d2)));
    	
    	BigDecimal pow = new BigDecimal(2);
    	System.out.println(pow.pow(2));
    }
    
    public static void testBigDecimal(){
    	 double i = 3.856;
    	   // 舍掉小数取整
    	   System.out.println("舍掉小数取整:Math.floor(3.856)=" + (int) Math.floor(i));
    	   // 四舍五入取整
    	   System.out.println("四舍五入取整:(3.856)="
    	     + new BigDecimal(i).setScale(0, BigDecimal.ROUND_HALF_UP));
    	   // 四舍五入保留两位小数
    	   System.out.println("四舍五入取整:(3.856)="
    	     + new BigDecimal(i).setScale(2, BigDecimal.ROUND_HALF_UP));
    	   // 凑整，取上限
    	   System.out.println("凑整:Math.ceil(3.856)=" + (int) Math.ceil(i));
    	   // 舍掉小数取整
    	   System.out.println("舍掉小数取整:Math.floor(-3.856)=" + (int) Math.floor(-i));
    	   // 四舍五入取整
    	   System.out.println("四舍五入取整:(-3.856)="
    	     + new BigDecimal(-i).setScale(0, BigDecimal.ROUND_HALF_UP));
    	   // 四舍五入保留两位小数
    	   System.out.println("四舍五入取整:(-3.856)="
    	     + new BigDecimal(-i).setScale(2, BigDecimal.ROUND_HALF_UP));
    	   // 凑整，取上限
    	   System.out.println("凑整(-3.856)=" + (int) Math.ceil(-i));
    }
}
