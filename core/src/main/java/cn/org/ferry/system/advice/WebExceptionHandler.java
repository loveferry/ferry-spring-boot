package cn.org.ferry.system.advice;

import cn.org.ferry.system.dto.ResponseData;
import cn.org.ferry.system.exception.BaseException;
import cn.org.ferry.system.exception.CommonException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WebExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public ResponseData handlerException(Throwable e){
        e.printStackTrace();
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        responseData.setMessage("出现未知错误");
        return responseData;
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseData handlerException(BaseException e){
        e.printStackTrace();
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        responseData.setCode(e.getClass().getAnnotation(ResponseStatus.class).code().value());
        responseData.setMessage(e.getMessage());
        return responseData;
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseData handlerException(CommonException e){
        e.printStackTrace();
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        responseData.setCode(e.getCode());
        responseData.setMessage(e.getMessage());
        return responseData;
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseData handlerException(NullPointerException e){
        e.printStackTrace();
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        responseData.setCode(500);
        responseData.setMessage(e.getMessage());
        return responseData;
    }


}
