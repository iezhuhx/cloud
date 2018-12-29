package com.cyb.pub;
import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月12日
 */
@Component
public class SpringContextUtil 
implements ApplicationContextAware {
 
    private  ApplicationContext context = null;
 
    /* (non Javadoc)
     * @Title: setApplicationContext
     * @Description: spring获取bean工具类
     * @param applicationContext
     * @throws BeansException
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.context = applicationContext;
    }
 
    // 传入线程中
    @SuppressWarnings("unchecked")
	public  <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }
 
    // 国际化使用
    public  String getMessage(String key) {
        return context.getMessage(key, null, Locale.getDefault());
    }
 
    /// 获取当前环境
    public  String getActiveProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    }
}