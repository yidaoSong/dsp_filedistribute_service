package com.bfd.monitor.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName:     LogUtil
 * @Description:日志处理类
 * @author:    Android_Robot
 * @date:        2018年6月6日 下午3:53:09
 *
 * 
 
 */
public class LogUtil {

	private static Map<String, Logger> logMap = new HashMap<String, Logger>();

	/**
	 * 
	 * @Title:        LogUtil
	 * @Description:    构造函数
	 * @param:    
	 * @throws
	 
	 */
	private LogUtil() {
	}

	static {
		logMap.put(LogType.Run.toString(), LoggerFactory.getLogger(LogType.Run.toString()));
		logMap.put(LogType.Ope.toString(), LoggerFactory.getLogger(LogType.Ope.toString()));
		logMap.put(LogType.Beh.toString(), LoggerFactory.getLogger(LogType.Beh.toString()));
		logMap.put(LogType.Ser.toString(), LoggerFactory.getLogger(LogType.Ser.toString()));
	}

	/**
	 * 
	 * @Title: getLogger
	 * @Description: 获取日志类型
	 * @param: @param logtype
	 * @param: @return   
	 * @return: Logger   
	 * @throws
	 
	 */
	public static Logger getLogger(LogType logtype) {
		return logMap.get(logtype.toString());
	}
}
