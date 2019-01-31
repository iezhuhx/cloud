package com.cyb.app.单例;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 解决这个问题的方法是：禁止指令重排序优化，
 *		即使用volatile变量<br>
 *创建时间: 2019年1月31日
 */
public class DCLSingleton {
	Log log = LogFactory.getLog(DCLSingleton.class);
	  private static volatile DCLSingleton instance = null;
	    public  static DCLSingleton getInstance() {
	    	// 线程二检测到instance不为空
	        if(null == instance) {    
	            synchronized (DCLSingleton.class) {
	                if(null == instance) {  
	                	// 线程一被指令重排，先执行了赋值，但还没执行完构造函数（即未完成初始化）    
	                    instance = new DCLSingleton();    
	                }
	            }
	        }
	        // 后面线程二执行时将引发：对象尚未初始化错误
	        return instance;    
	    }
}
/**
 * 如上代码段中的注释：假设线程一执行到instance = new Singleton()这句，这里看起来是一句话，但实际上其被编译后在JVM执行的对应会变代码就发现，这句话被编译成8条汇编指令，大致做了三件事情：

　　1）给instance实例分配内存；

　　2）初始化instance的构造器；

　　3）将instance对象指向分配的内存空间（注意到这步时instance就非null了）

　　如果指令按照顺序执行倒也无妨，但JVM为了优化指令，提高程序运行效率，允许指令重排序。如此，在程序真正运行时以上指令执行顺序可能是这样的：

　　a）给instance实例分配内存；

　　b）将instance对象指向分配的内存空间；

　　c）初始化instance的构造器；

　　这时候，当线程一执行b）完毕，在执行c）之前，被切换到线程二上，这时候instance判断为非空，此时线程二直接来到return instance语句，拿走instance然后使用，接着就顺理成章地报错（对象尚未初始化）。
 */
