package com.cyb.app.单例;

/**
 *作者 : iechenyb<br>
 *类描述: 类加载的时候单例对象也会跟着加载，
 *拖延类加载速度，有时候没用到这个类的单例对象的话，会浪费空间<br>
 *创建时间: 2019年1月31日
 */
public class StaticFieldSingleton {
	private StaticFieldSingleton() {
		System.out.println("内部类创建2!");
    }

	private static final StaticFieldSingleton singletonPattern = new StaticFieldSingleton();


    public static StaticFieldSingleton getInstance() {
        return singletonPattern;
    }
}
