package com.cyb.test.data;

import com.cyb.util.data.DecimalUtils;
import com.cyb.util.data.DoubleUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DataTest
{
    public static void main(String[] args) {
        System.out.println(DecimalUtils.e2Double(0.1522784999999996, 8));
        System.out.println(0.05+0.01);
        System.out.println(1.0-0.42);
        System.out.println(4.015*100);
        System.out.println(123.3/100);
        System.out.println(1/3.0);
        System.out.println(DecimalUtils.e2Double(0.05+0.01, 11));
        System.out.println(DecimalUtils.e2Double(4.015*100, 9));
        System.out.println(DecimalUtils.e2Double(123.3/100, 9));
        System.out.println(DecimalUtils.e2Double(1/3.0, 5));
        System.out.println(DecimalUtils.e2Double(1/3.0, 6));
        System.out.println(DecimalUtils.e2Double(1/3.0, 7));
        System.out.println(DecimalUtils.e2Double(1/3.0, 15));
        System.out.println(DecimalUtils.e2Double(1/3.0, 20));
        System.out.println(0.0000001>0);
        System.out.println(0.000000000000000000000001>0);
        System.out.println(DecimalUtils.e2StringBD(1.0E-2, 1));
        System.out.println(DecimalUtils.e2StringBD(1.0E-17, 18));
        System.out.println(DecimalUtils.e2StringBD(1.0E-20, 21));
        //无限循环小数会报错new BigDecimal(10).divide(new BigDecimal(3))
        BigDecimal big = new BigDecimal(10).divide(new BigDecimal(3),8, RoundingMode.HALF_DOWN);
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

        System.out.println(Double.valueOf("0.0"));
        double val1=0.05;
        double val2=0.01;
        System.out.println(DoubleUtil.divide(val1, val2));
        System.out.println(DoubleUtil.divide(val1, val2,8));
        System.out.println(DoubleUtil.round(val1/val2,8));
        System.out.println(val1/val2);
        System.out.println(DoubleUtil.sub(val1, val2));
        System.out.println(val1-val2);
        System.out.println(DoubleUtil.add(val1, val2));
        System.out.println("comon+="+(val1+val2));
        System.out.println(DoubleUtil.mul(val1, val2));
        System.out.println(val1*val2);
        String aa = "-4.99183553218834E-7";
        double bb = -4.99183553218834E-7;
    }
}
