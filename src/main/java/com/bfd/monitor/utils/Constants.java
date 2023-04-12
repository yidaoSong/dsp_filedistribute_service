package com.bfd.monitor.utils;

import org.apache.commons.lang.StringUtils;

/**
 * 常量类
 *
 * @author 姓名 工号
 * @version [版本号, 2017年1月4日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Constants {
	public static final String VERIFY_CODE = "verifyCode";

	// controller返回成功码
	public static final String SUCCESS_CODE = "200";

	// controller返回成功消息
	public static final String SUCCESS_MESSAGE = "Success";

	// controller返回失败码
	public static final String FAILED_CODE = "300";

	// controller返回失败消息
	public static final String FAILED_MESSAGE = "Failed";

	public static final String COOKIE_TOKEN = "token";

	public static final String SESSION_USER = "session_user";

	public static final String COIKIE_USER = "user";

	public static final int HBASE_SELECT_NUM = 1000;

	public static final String SERVICE_RETURN_CODE = "code";

	public static final String SERVICE_RETURN_SUCCESS_CODE = "1";

	public static final String SERVICE_RETURN_ITEMS = "items";
	//加载配置优先级 1：优先加载配置文件 2：优先加载MySQL
	public static final int CONFIG_PRIORITY = ConfigUtils.getInt("config.priority");
	public static final String CONFIG_JSON_LIST = ConfigUtils.getString("config.json.list");
	public static final String CONFIG_JSON_PATH = ConfigUtils.getString("config.json.path");

	//分发错误次数
	public static int DISTRIBUTE_META_ERROR_NUM = ConfigUtils.getInt("distribute.meta.error.num");

	public static String DISTRIBUTE_PATH;//根目录
	//分发分目录
	public static String DISTRIBUTE_PATH_SEND;
	//默认失败目录
	public static String DISTRIBUTE_FOLDER_FAILED;
	//特殊字符替换字符
	//替换cdata中原有数据与FileUtils.java中的504行注释掉的内容及application.properties中的distribute.file.char一同起作用
	public static String DISTRIBUTE_FILE_CHAR;
	//是否初始化线程
	public static boolean DISTRIBUTE_INIT = ConfigUtils.getBoolean("distribute.init");
	static {
		//分发分目录获取值
		if (StringUtils.isEmpty(DISTRIBUTE_PATH)) {
			DISTRIBUTE_PATH = ConfigUtils.getString("distribute.path");
		}
		//默认失败目录获取值
		if (StringUtils.isEmpty(DISTRIBUTE_FOLDER_FAILED)) {
			DISTRIBUTE_FOLDER_FAILED = ConfigUtils.getString("distribute.folder.failed");
		}
		//分目录获取值
		if (StringUtils.isEmpty(DISTRIBUTE_PATH_SEND)) {
			DISTRIBUTE_PATH_SEND = ConfigUtils.getString("distribute.path.send");
		}
		//替换cdata中原有数据与FileUtils.java中的504行注释掉的内容及application.properties中的distribute.file.char一同起作用
		if (StringUtils.isEmpty(DISTRIBUTE_FILE_CHAR)) {
			DISTRIBUTE_FILE_CHAR = ConfigUtils.getString("distribute.file.char");
		}

	}
}