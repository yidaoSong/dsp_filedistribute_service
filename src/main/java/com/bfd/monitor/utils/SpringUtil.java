package com.bfd.monitor.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName:     SpringUtil
 * @Description:获取公共配置类
 * @author:    Android_Robot
 * @date:        2018年5月2日 下午3:10:39
 *
 * 
 
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;
    
    /**
     * 
     * <p>Title: setApplicationContext</p>
     * <p>Description: 上下文配置信息</p>
     * @param applicationContext
     * @throws BeansException
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
        // System.out.println("========ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext=" + applicationContext + "========");
        LogUtil.getLogger(LogType.Run).info("========ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext=" + applicationContext + "========");
    }
    
    /**
     * 
     * @Title: getApplicationContext
     * @Description: 获取上下文配置信息
     * @param: @return   
     * @return: ApplicationContext   
     * @throws
     
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    
    /**
     * 
     * @Title: getBean
     * @Description: 获取bean
     * @param: @param name
     * @param: @return   
     * @return: Object   
     * @throws
     
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }
    
    /**
     * 
     * @Title: getBean
     * @Description: 获取bean
     * @param: @param clazz
     * @param: @return   
     * @return: T   
     * @throws
     
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }
    
    /**
     * 
     * @Title: getBean
     * @Description: 获取bean
     * @param: @param name
     * @param: @param clazz
     * @param: @return   
     * @return: T   
     * @throws
     
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
