package com.bfd.monitor.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import ch.qos.logback.core.net.server.Client;

/**
 * 
 * @ClassName:     ConfigUtils
 * @Description:获取配置文件信息
 * @author:    Android_Robot
 * @date:        2018年5月3日 下午3:00:05
 *
 * 
 
 */
public class ConfigUtils {
	
	   public static Properties props;
	    
	    static {
	        if (props == null) {
	            try {
	                props = new Properties();
	              //加载获取配置信息文件
	                props.load(new InputStreamReader(Client.class.getClassLoader()
	                    .getResourceAsStream("application.properties"), "UTF-8"));
	            }
	            catch (IOException e) {
	                props = null;
	                LogUtil.getLogger(LogType.Run).error("IO流异常！"+e.getMessage(),e);
	            }
	        }
	    }
	    
	    /**
	     * 
	     * @Title: getString
	     * @Description: 配置属性获取
	     * @param: @param key
	     * @param: @return   
	     * @return: String   
	     * @throws
	     
	     */
	    public static String getString(String key) {
	        return props.getProperty(key);
	    }
	    
	    /**
	     * 
	     * @Title: getInt
	     * @Description: 整型值配置获取
	     * @param: @param key
	     * @param: @return   
	     * @return: int   
	     * @throws
	     
	     */
	    public static int getInt(String key) {
	        return Integer.parseInt(props.getProperty(key));
	    }
	    
	    /**
	     * 
	     * @Title: getLong
	     * @Description: 长整型值获取
	     * @param: @param key
	     * @param: @return   
	     * @return: long   
	     * @throws
	     
	     */
	    public static long getLong(String key) {
	        return Long.parseLong(props.getProperty(key));
	    }
	    
	    /**
	     * 
	     * @Title: getBoolean
	     * @Description:布尔类型配置获取
	     * @param: @param key
	     * @param: @return   
	     * @return: boolean   
	     * @throws
	     
	     */
	    public static boolean getBoolean(String key) {
	        return Boolean.valueOf(props.getProperty(key));
	    }
	    
	    /**
	     * 
	     * @Title: getDouble
	     * @Description: 获取配置值
	     * @param: @param key
	     * @param: @return   
	     * @return: double   
	     * @throws
	     
	     */
	    public static double getDouble(String key) {
	        return Double.valueOf(props.getProperty(key));
	    }   
}
