package com.bfd.monitor.common.vo;


/**
 * 返回数据
 * 
 * @author 姓名 工号
 * @version [版本号, 2016年12月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Result<T> {
    //成功失败标识
    private String code;
    
    // 消息
    private String _message;
    
    // 数据
    private T data;
    
    /**
     * 
     * @Title:        Result
     * @Description:    构造函数
     * @param:    
     * @throws
     
     */
    public Result() {
    }
    
    /**
     * 
     * @Title:        Result
     * @Description:    构造函数
     * @param:    @param code
     * @param:    @param _message
     * @param:    @param data
     * @throws
     
     */
    public Result(String code, String _message, T data) {
        this.code = code;
        this._message = _message;
        this.data = data;
    }
    
    /**
     * 
     * @Title: getCode
     * @Description: 成功失败get方法
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
     * @Description: 成功失败set方法
     * @param: @param code   
     * @return: void   
     * @throws
     
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * 
     * @Title: get_message
     * @Description: 消息get方法
     * @param: @return   
     * @return: String   
     * @throws
     
     */
    public String get_message() {
        return _message;
    }

    /**
     * 
     * @Title: set_message
     * @Description: 消息set方法
     * @param: @param _message   
     * @return: void   
     * @throws
     
     */
    public void set_message(String _message) {
        this._message = _message;
    }

    /**
     * 
     * @Title: getData
     * @Description: 数据get方法
     * @param: @return   
     * @return: T   
     * @throws
     
     */
    public T getData() {
        return data;
    }
    
    /**
     * 
     * @Title: setData
     * @Description: 数据set方法
     * @param: @param data   
     * @return: void   
     * @throws
     
     */
    public void setData(T data) {
        this.data = data;
    }
    
}
