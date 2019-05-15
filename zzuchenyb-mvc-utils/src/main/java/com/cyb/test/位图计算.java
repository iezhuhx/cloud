package com.cyb.test;

import java.util.BitSet;
//https://www.cnblogs.com/xupengzhang/p/7966755.html
/**
 *  Java BitSet可以按位存储，计算机中一个字节(byte)占8位(bit)；
	而BitSet是位操作的对象，值只有0或1（即true 和 false），内部维护一个long数组，初始化只有一个long segement，
	所以BitSet最小的size是64；随着存储的元素越来越多，BitSet内部会自动扩充，一次扩充64位，最终内部是由N个long segement 来存储；
	默认情况下，BitSet所有位都是0即false
 * @author Administrator
 *
 */
public class 位图计算 {
	public static void main(String[] args){
		byte[] bs = {0,1,1,3,2,3};//1byte=8bit
		int count=100_000_000;//一个亿
		long aaa =100_000_000_000_000_000_0L;
		System.out.println(Long.toBinaryString(aaa));
		BitSet bit=new BitSet(count);//只能用于整数 创建一个位 set，它的初始大小足以显式表示索引范围在 0 到 nbits-1 的位
		System.out.println("allocate size="+bit.size()+" "+bit.length());//100000000
		int i=1000;
		//设置一千个数字
		while(i>0){
			bit.set((int)(Math.random()*count));//将指定索引处的位设置为 true
			i--;
		}

		for(int index=0;index<count;index++){
			if(bit.get(index)){
				System.out.println(bit.get(index)+"->"+index);
			}
		}

		System.out.println("end");
		
		//这个时候bitSet.size() = 128;
        BitSet bitSet = new BitSet(100);
        System.out.println("allocate size="+bitSet.size()+" "+bitSet.length());//128
 
        //这个时候bitSet.size() = 256；
        BitSet bitSet2 = new BitSet(200);
        System.out.println("allocate size="+bitSet2.size()+" "+bitSet.length());//256
	}
}
