package com.bfd.monitor.common.exception;

/**
 * 
 * @ClassName:     CustomException
 * @Description:运行异常类
 * @author:    Android_Robot
 * @date:        2018年4月18日 下午2:32:13
 *
 * 
 
 */
public class CustomException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 
     * @Title:        CustomException
     * @Description:    构造函数
     * @param:    
     * @throws
     
     */
    public CustomException() {
        super();
    }
    
    /**
     * 
     * @Title:        CustomException
     * @Description:    带参构造函数
     * @param:    @param message
     * @throws
     
     */
    public CustomException(String message) {
        super(message);
    }
}
