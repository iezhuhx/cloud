package com.cyb.test;

public class 符号运算 {
	/**
	 *  byte 1字节
		short 2字节
		int 4字节
		long 8字节
		float 4字节
		double 8字节
		char 2字节
		boolean 1字节
	 * @param args
	 * @throws Exception
	 */
@SuppressWarnings("unused")
public static void main(String[] args) throws Exception {
    byte by=7;//32位循环
	char ch=7;//32位循环
	short sh=7;//32位循环
	int in=7;//32位循环
	long lon=7;//64位循环
	double dd=7;
	for(int i=0;i<=64;i++){
		System.out.println(i+"->"+Long.toBinaryString(lon>>>i));//32*n=数据本身
	}
	//自己与自己亦或 永远都是0
	System.out.println(in^(in>>>32));//亦或 相同为0，不同为1---自己与自己做亦或运算
	
}
public void test() throws Exception{
	//(i&(len-1))==(i%len)
		int len=16;//只有当len等于2的n次方时，才能完全相等。只有当n为2的pow时，n-1的二进制才全部是1。
		for(int i=1;i<100;i++){
			System.out.println((i&(len-1))==(i%len));
			boolean flag=(i&(len-1))==(i%len);
			 if(!flag){
			    	throw new Exception("aaa");
			 }
		}
		
		// ~a = -a-1
		for(int i=1;i<100;i++){
			System.out.println(~i==(-i-1));
			boolean flag= ~i==(-i-1);
			 if(!flag){
			    	throw new Exception("aaa");
			 }
		}
		System.out.println((int)Math.pow(2, 5));
		for(int i=0;i<100;i++){
			for(int j=1;j<5;j++){
				int a = i>>>j;
			    int b=i/(int)Math.pow(2, j);
			    if(a!=b){
			    	throw new Exception("aaa");
			    }
				System.out.println(i+">>>"+j+"="+(i>>>j)+"--->"+i/(int)Math.pow(2, j));//获取商
			}
		}
		//带符号
		for(int i=0;i<100;i++){
			for(int j=1;j<5;j++){
				int a = i>>j;
			    int b=i/(int)Math.pow(2, j);
			    if(a!=b){
			    	throw new Exception("aaa");
			    }
				System.out.println(i+">>"+j+"="+(i>>j)+"--->"+i/(int)Math.pow(2, j));//获取商
			}
		}
}
}
