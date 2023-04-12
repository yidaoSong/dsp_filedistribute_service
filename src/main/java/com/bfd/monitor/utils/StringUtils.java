package com.bfd.monitor.utils;

/**
 * 
 * @ClassName:     StringUtils
 * @Description:字符串相关功能处理逻辑
 * @author:    Android_Robot
 * @date:        2018年5月2日 下午3:08:13
 *
 * 
 
 */
public class StringUtils {
	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		boolean vali = false;
		if (org.apache.commons.lang.StringUtils.isNotEmpty(str)) {
			vali = true;
		}
		return vali;
	}
	
	/**
	 * 
	 * @Title: isEmpty
	 * @Description: 判断字符串是否为空
	 * @param: @param str
	 * @param: @return   
	 * @return: boolean   
	 * @throws
	 
	 */
	public static boolean isEmpty(String str) {
	    boolean vali = false;
	    if (org.apache.commons.lang.StringUtils.isEmpty(str)) {
	        vali = true;
	    }
	    return vali;
	}

	/**
	 * 判断字符串的内容是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		boolean vali = false;
		if (org.apache.commons.lang.StringUtils.isNotBlank(str)) {
		    vali = true;
		}
		return vali;
	}
	
	/**
	 * 
	 * @Title: isBlank
	 * @Description: 判断字符的内容是否为空白
	 * @param: @param str
	 * @param: @return   
	 * @return: boolean   
	 * @throws
	 
	 */
	public static boolean isBlank(String str) {
	    boolean vali = false;
	    if (org.apache.commons.lang.StringUtils.isBlank(str)) {
	        vali = true;
	    }
	    return vali;
	}

	/**
	 * 用指定符号切割字符串
	 * 
	 * @param str
	 *            待切割字符串
	 * @param regex
	 *            切割符号
	 * @return 切割后的数组，可能返回null
	 */
	public static String[] splitArr(String str, String regex) {
		String[] strArr = null;
		if (!StringUtils.isNotEmpty(regex)) {
			regex = ",";
		}
		if (StringUtils.isNotEmpty(str)) {
			strArr = str.split(regex);
		}
		return strArr;
	}
}
