package com.kiiik.web.example.service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

import com.kiiik.web.example.bean.TestBean;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月20日
 */
@Service
public class CacheService {
	Log log = LogFactory.getLog(CacheService.class);
	//@Cacheable(value = "people#${select.cache.timeout:1800}#${select.cache.refresh:600}", key = "#person.id", sync = true)
	@CachePut(key="#person.id",value="people")
	public TestBean save(TestBean person){
		System.err.println("保存并刷新key id测试!");
		return person;
	}	
	//有效时间+刷新时间
	@Cacheable(value = "people#120#90", key = "#person.id")
	public TestBean findOne(TestBean person) {
	    System.out.println("为id、key为:" + person.getId() + "数据做了缓存");
	    return person;
	}
	//springboot redis java.lang.Integer cannot be cast to java.lang.String
	@Cacheable(key="#id",value="people")
	public TestBean findTestBeanById(@PathVariable String id){//Integer id会报错，key支持字符串类型
		System.err.println("简单key id策略测试!");
		return new TestBean(id,"8767890","iechenyb","111111");
	}
	
	//上面的两个方法的代码可以忽略
	@Cacheable(keyGenerator="cacheKeyGenerator",value="people")
	public TestBean findTestBeanByFuza(TestBean person){//这里是比较复杂的查询条件，实际中可能是HashMap之类存在各种参数的东东
		System.err.println("自定义key生成测试!");
		return person;
	}
	
	@Autowired
	CacheManager cacheManager;
	
	public void clearCache(String name){
		System.err.println(cacheManager);
		if(StringUtils.isEmpty(name)){
			for(String n:cacheManager.getCacheNames()){
				cacheManager.getCache(n).clear();
				System.err.println("清除缓存："+n);
			}
		}else{
			cacheManager.getCache(name).clear();
		}
	}
	
	@Cacheable(keyGenerator="cacheKeyGenerator",value="people")
	public TestBean findTestNullBeanByFuza(TestBean person) {
		System.err.println("测试返回空值是否缓存！");
		return null;
	}
	
	@Cacheable(keyGenerator="cacheKeyGenerator",value="people",condition="#person.id=='20'")//person.id%2==0
	public TestBean findTestConditionCache(TestBean person) {
		System.err.println("condition缓存测试！id=20时缓存！");
		return person;
	}
	
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 测试特定条件下抛出异常，是否继续缓存<br>
     *创建时间: 2018年11月21日
     *@param person
     *@return 测试结果 异常时没有进行缓存
     * @throws Exception 
     */
	@Cacheable(keyGenerator="cacheKeyGenerator",value="people")
	public TestBean findTestExceptionCache(TestBean person) throws Exception {
		System.err.println("异常缓存测试！");
		if("iechenyb".equals(person.getName())){
			throw new Exception("抛出异常，请注意是否缓存数据！");
		}
		return person;
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: id=30不缓存，其他值均缓存<br>
	 *创建时间: 2018年11月21日
	 *@param person
	 *@return
	 */
	@Cacheable(keyGenerator="cacheKeyGenerator",value="people",unless="#person.id=='30'")
	public TestBean findTestUnlessCache(TestBean person) {
		System.err.println("Unless缓存测试！id!=30 时缓存！");
		return person;
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 更新person时，刷新缓存<br>
	 *创建时间: 2018年11月21日
	 *@param person
	 *@return
	 *@CacheEvict注解修饰的方法可用于清除缓存，使用@CacheEvict注解时可指定如下属性：
　　　　　⊙ value ： 必须属性。用于指定该方法用于清除哪个缓存区的数据。
                      ⊙ allEntries ： 该属性指定是否清空整个缓存区。
                      ⊙ beforeInvocation ： 该属性指定是否在执行方法之前清除缓存。默认是在方法成功完成之后才清除缓存。
                      ⊙ condition ： 该属性指定一个SpEL表达式，只有当该表达式为true时才清除缓存。
                  　⊙ key ： 通过SpEL表达式显示指定缓存的key。多个参数组合的key 用#name + #age + ...
	 */
	@CacheEvict(value="person",/*cacheNames={"person"}*/beforeInvocation = true,allEntries=true)
	public void update() {
		System.err.println("情况指定缓存！");
	}
	
	
	@CacheEvict(value="users")
    public void evictUser(String name, int age) {
        System.out.println("--正在清空" + name + "," + age + "对应的缓存--");
    }

    @CacheEvict(value="users",allEntries=true)
    public void evictAll() {
        System.out.println("--正在情况整个缓存--");
    }
}
