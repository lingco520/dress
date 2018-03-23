package com.daqsoft.validator;


import com.daqsoft.commons.responseentity.ResponseBuilder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 异常处理器
 *
 * @author
 * @email
 * @date
 */
@RestControllerAdvice
public class RRExceptionHandler {

    /**
     * 自定义异常
     * 使用 ResponseBuilder 统一抛出
     * 此处只能记录到“抛出异常”的位置。
     */
    @ExceptionHandler(RRException.class)
    public Object handleRRException(RRException e) {
        e.printStackTrace();
        return ResponseBuilder.custom().failed(e.getMsg(), e.getCode()).build();
    }


}
