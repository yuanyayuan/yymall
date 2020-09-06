package com.nexus.mall.common.exception;

import com.nexus.mall.common.api.ResultCode;
import com.nexus.mall.common.api.ServerResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

/**

* @Description:    全局异常处理

* @Author:         Nexus

* @CreateDate:     2020/9/2 23:20

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/2 23:20

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public ServerResponse handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return ServerResponse.failed(e.getErrorCode());
        }
        return ServerResponse.failed(e.getMessage());
    }
    @ResponseBody
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ServerResponse noHandlerFound(NoHandlerFoundException e){
        return ServerResponse.failed(ResultCode.NO_HANDLER_FOUND, String.valueOf(e.getCause()));
    }
    @ResponseBody
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ServerResponse noHandlerFound(IllegalArgumentException e){
        return ServerResponse.failed(ResultCode.PARAMETER_VALIDATION_ERROR, String.valueOf(e.getCause()));
    }
    @ResponseBody
    @ExceptionHandler(value = IllegalStateException.class)
    public ServerResponse noHandlerFound(IllegalStateException e){
        return ServerResponse.failed(ResultCode.STATE_EXCEPTION_ERROR, String.valueOf(e.getCause()));
    }
    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ServerResponse noHandlerFound(MissingServletRequestParameterException e){
        return ServerResponse.failed(ResultCode.PARAMETER_VALIDATION_ERROR, String.valueOf(e.getCause()));
    }
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ServerResponse handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return ServerResponse.validateFailed(message);
    }
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public ServerResponse handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return ServerResponse.validateFailed(message);
    }
}
