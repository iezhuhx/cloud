package com.cyb.app.单例;

/**
 *作者 : iechenyb<br>
 *类描述: 当我们调用 SingletonPattern.getInstance() 时，
 *内部类 SingletonPatternHolder 才会初始化
 *，静态变量 singletonPattern 被创建出来<br>
 *创建时间: 2019年1月31日
 */
public class StaticInnerClassSingleton {
	private StaticInnerClassSingleton() {
		System.out.println("内部类创建1!");
    }

    private static class SingletonPatternHolder {
        private static final StaticInnerClassSingleton singletonPattern = new StaticInnerClassSingleton();
    }

    public static StaticInnerClassSingleton getInstance() {
        return SingletonPatternHolder.singletonPattern;
    }
}
