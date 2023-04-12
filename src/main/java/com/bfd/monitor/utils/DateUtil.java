package com.bfd.monitor.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * Description:日期时间操作的工具类
 */
public class DateUtil {
	/**
	 * 获取指定时间的午夜
	 * @param date
	 * @return
	 */
	public static Date dayEndDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}
	/**
	 * 获取指定时间的零点
	 * @param date
	 * @return
	 */
	public static Date dayStartDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}
	
	
	/**
	 * 将Date转换成formatStr格式的字符串
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String dateToString(Date date, String formatStr) {
		if(date==null || StringUtils.isBlank(formatStr)){
			return "";
		}
		return new SimpleDateFormat(formatStr).format(date);
	}

	/**
	 * 将String转换成formatStr格式的Date
	 * @param dateStr
	 * @param formatStr
	 * @return
	 */
	public static Date stringToDate(String dateStr, String formatStr) {
		Date date = new Date();
		try {
			if(StringUtils.isBlank(dateStr) || StringUtils.isBlank(formatStr)){
				date =  null;
			}else{
				date = new SimpleDateFormat(formatStr).parse(dateStr);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 获取当天时间Date
	 * @return
	 */
	public static Date getDate() {
		return Calendar.getInstance().getTime();
	}	
}
