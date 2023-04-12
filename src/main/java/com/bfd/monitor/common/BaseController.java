package com.bfd.monitor.common;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfd.monitor.common.vo.Result;
import com.bfd.monitor.utils.Constants;

/**
 * 
 * @ClassName:     BaseController
 * @Description:包装返回结果的抽象类型
 * @author:    Android_Robot
 * @date:        2018年4月18日 上午11:27:15
 *
 * 
 
 */
public abstract class BaseController {
    
	/**
	 * 请求
	 */
    @Resource
    protected HttpServletRequest request;
    
    /**
     * 返回结果
     */
    @Resource
    protected HttpServletResponse response;
    
    /**
     * 
     * @Title: getRequest
     * @Description: 请求的get方法
     * @param: @return   
     * @return: HttpServletRequest   
     * @throws
     
     */
    public HttpServletRequest getRequest() {
        return request;
    }
    
    /**
     * 
     * @Title: setRequest
     * @Description: 请求的set方法
     * @param: @param request   
     * @return: void   
     * @throws
     
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    /**
     * 
     * @Title: getResponse
     * @Description: 返回结果get方法
     * @param: @return   
     * @return: HttpServletResponse   
     * @throws
     
     */
    public HttpServletResponse getResponse() {
        return response;
    }
    
    /**
     * 
     * @Title: setResponse
     * @Description: 返回结果set方法
     * @param: @param response   
     * @return: void   
     * @throws
     
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    
    /**
     * 操作結果
     * 
     * @param code
     * @param msg
     * @return
     */
    protected Result<Object> optResult(String code, String msg) {
        Result<Object> result = new Result<Object>();
        result.setCode(code);
        result.set_message(msg);
        return result;
    }
    
    /**
     * 操作結果
     * 
     * @param code
     * @param msg
     * @param data
     * @return
     */
    protected Result<Object> optResult(String code, String msg, Object data) {
        Result<Object> result = new Result<Object>();
        result.setCode(code);
        result.set_message(msg);
        result.setData(data);
        return result;
    }
    
    /**
     * 操作成功
     * 
     * @return
     */
    protected Result<Object> optSuccess() {
        Result<Object> result = new Result<Object>();
        result.setCode(Constants.SUCCESS_CODE);
        result.set_message(Constants.SUCCESS_MESSAGE);
        return result;
    }
    
    /**
     * 操作错误
     * 
     * @return
     */
    protected Result<Object> optError() {
        Result<Object> result = new Result<Object>();
        result.setCode("300");
        result.set_message("error");
        return result;
    }
    
    /**
     * 获取登录用户
     * 
     * @return
     */
    protected String getCurrentUser() {
        return (String)request.getAttribute("userName");
    }
}
