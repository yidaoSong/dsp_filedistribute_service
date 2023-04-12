package com.bfd.monitor.common.pojo;

/**
 * 
 * @ClassName:     JsonResult
 * @Description:Json返回值bean
 * @author:    Android_Robot
 * @date:        2018年6月6日 上午10:45:20
 *
 *
 */
public class JsonResult {
	//成功失败标识
	private String code;
	//成功消息及失败原因
	private String message;
	//返回参数
	private String result;
	
	/**
	 * 
	 * @Title: getCode
	 * @Description: 成功失败标识get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 
	 * @Title: setCode
	 * @Description: 成功失败标识set方法
	 * @param: @param code   
	 * @return: void   
	 * @throws
	 
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 
	 * @Title: getMessage
	 * @Description: 失败原因get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * 
	 * @Title: setMessage
	 * @Description: 失败原因set方法
	 * @param: @param message   
	 * @return: void   
	 * @throws
	 
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 
	 * @Title: getResult
	 * @Description:返回结果get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getResult() {
		return result;
	}
	/**
	 * 
	 * @Title: setResult
	 * @Description: 返回结果set方法
	 * @param: @param result   
	 * @return: void   
	 * @throws
	 
	 */
	public void setResult(String result) {
		this.result = result;
	}
}

