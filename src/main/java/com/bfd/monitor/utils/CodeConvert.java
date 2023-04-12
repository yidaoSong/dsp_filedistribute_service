package com.bfd.monitor.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * 
 * @ClassName:     CodeConvert
 * @Description:编码值转换
 * @author:    Android_Robot
 * @date:        2018年5月3日 下午3:04:43
 *
 * 
 
 */
public class CodeConvert extends ClassicConverter {

	/**
	 * 
	 * <p>Title: convert</p>
	 * <p>Description: 级别代码转换</p>
	 * @param event
	 * @return
	 * @see ch.qos.logback.core.pattern.Converter#convert(java.lang.Object)
	 
	 */
	@Override
	public String convert(ILoggingEvent event) {
		if (event.getLevel() == Level.ERROR) {
			return "1500";
		}
		return "1200";
	}
}
