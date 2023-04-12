package com.bfd.monitor.utils;

import org.springframework.context.ApplicationContext;
/**
 * 上下文获取工具类
 * @author mengfeiyang
 *
 */
public class SpringContextUtil {
      private static ApplicationContext applicationContext;

      /**
       * 
       * @Title: setApplicationContext
       * @Description: 上下文信息赋值
       * @param: @param context   
       * @return: void   
       * @throws
       
       */
      public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
      }
      
      /**
       * 
       * @Title: getBean
       * @Description: bean的get方法
       * @param: @param beanId
       * @param: @return   
       * @return: Object   
       * @throws
       
       */
       public static Object getBean(String beanId) {
        return applicationContext.getBean(beanId);
      }
}
