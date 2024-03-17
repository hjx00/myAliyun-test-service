package com.huang.common.config;

import com.huang.common.model.BusinessException;
import com.huang.common.model.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname GlobalExceptionHandle
 * @CreatedDate 2024/3/14 10:22
 * @Author Huang
 */
@RestControllerAdvice
@Order(100)
@Slf4j
public class GlobalExceptionHandle {
    @ExceptionHandler(Exception.class)
    public Wrapper<?> exceptionHandle(HttpServletRequest request, Exception exception) {
        String message = String.format("请求:%s, 发生异常", request.getRequestURI());
        log.error(message, exception);
        return Wrapper.error("服务器异常");
    }
    @ExceptionHandler(BusinessException.class)
    public Wrapper<?> businessExceptionHandle(HttpServletRequest request, BusinessException exception) {
        String message = String.format("请求:%s, 发生异常", request.getRequestURI());
        log.warn(message, exception);
        return Wrapper.warn(exception.getMsg());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Wrapper<?> validationBodyException(HttpServletRequest request,MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        //返回第一个错误
        String errMsg = result.getAllErrors().get(0).getDefaultMessage();
        log.warn(request.getRequestURI()+",参数异常：" + exception.getMessage(), exception);
        return Wrapper.warn(errMsg);
    }
}
