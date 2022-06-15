package com.example.common;

import com.example.common.bean.CodeConstant;
import com.example.common.bean.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 *
 * @Author: luxiangning
 * @date:  2022/6/15 上午10:31
 * @Description 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 系统异常处理
     *
     * @param e 捕获的异常
     * @return  出错提示
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<Object> error(Exception e) {
        e.printStackTrace();
        Result<Object> mResult = new Result();
        mResult.setCode(CodeConstant.FAIL);
        mResult.setMsg("系统出问题了，请联系工作人员");
        mResult.setDetail(false);
        return mResult;
    }
}
