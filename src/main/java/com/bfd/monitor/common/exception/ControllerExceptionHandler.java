package com.bfd.monitor.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bfd.monitor.common.BaseController;
import com.bfd.monitor.common.vo.Result;
import com.bfd.monitor.utils.Constants;
import com.bfd.monitor.utils.LogType;
import com.bfd.monitor.utils.LogUtil;

/**
 * 处理所有Controller抛出的异常
 * 
 * @author xile.lu
 * @version [版本号, 2016年11月25日]
 */
@ControllerAdvice
public class ControllerExceptionHandler extends BaseController {
    
    /**
     * 捕获所有Controller的异常
     * 
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Object handleException(Exception e) {
        LogUtil.getLogger(LogType.Run).error(e.getMessage(), e);
        Result<Object> result = this.optResult(Constants.FAILED_CODE, "error", null);
        return result;
    }
    
    /**
     * 
     * @Title: hadnleCustomException
     * @Description: 捕获异常
     * @param: @param e
     * @param: @return   
     * @return: Object   
     * @throws
     
     */
    @ExceptionHandler({CustomException.class})
    @ResponseBody
    public Object hadnleCustomException(CustomException e) {
        LogUtil.getLogger(LogType.Run).error(e.getMessage(), e);
        Result<Object> result = this.optResult(Constants.FAILED_CODE, e.getMessage(), e);
        return result;
    }
}