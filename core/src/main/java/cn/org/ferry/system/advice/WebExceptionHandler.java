package cn.org.ferry.system.advice;

import cn.org.ferry.system.dto.ResponseData;
import cn.org.ferry.system.exception.NotNumberException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WebExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public String handlerException(Throwable e){
        return "出现未知错误!";
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseData handlerException(NotNumberException e){
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        responseData.setCode(e.getClass().getAnnotation(ResponseStatus.class).code().value());
        responseData.setMessage(e.getMessage());
        return responseData;
    }
}
