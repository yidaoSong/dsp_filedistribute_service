package com.bfd.monitor.utils.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 
 * @ClassName:     IPLogConfig
 * @Description:ip信息获取类
 * @author:    Android_Robot
 * @date:        2018年6月11日 上午11:21:03
 *
 * 
 
 */
public class IPLogConfig extends ClassicConverter{

	/**
	 * 
	 * <p>Title: convert</p>
	 * <p>Description: 信息获取</p>
	 * @param event
	 * @return
	 * @see ch.qos.logback.core.pattern.Converter#convert(java.lang.Object)
	 
	 */
    public String convert(ILoggingEvent event) {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}