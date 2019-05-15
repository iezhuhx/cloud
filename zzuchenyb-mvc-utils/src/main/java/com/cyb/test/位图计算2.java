package com.cyb.test;

import java.util.BitSet;

public class 位图计算2 {
	public static void main(String[] args){
		int count=100;//一个亿
		BitSet bit=new BitSet(count);//只能用于整数
		int a[]={0,1,2,3,4,5,6,7};
		int total=a.length;
		//设置一千个数字
		for(int aa=0;aa<total;aa++){
			bit.set(a[aa]);//设置到对应的第n个位置上设置1
		}

		for(int index=0;index<count;index++){
			if(bit.get(index)){//获取第index个bit
				System.out.println(bit.get(index)+"->"+index);
			}else{
				System.out.println(bit.get(index)+"->"+index);
			}
		}

		System.out.println("end");
		
	}
}
