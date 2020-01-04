package cn.org.ferry.core.advice;

import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.core.exceptions.BaseException;
import cn.org.ferry.core.exceptions.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WebExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);
    @ExceptionHandler
    @ResponseBody
    public ResponseData handlerException(Throwable e){
        logger.error("未捕获的异常：{}", e);
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        responseData.setMessage("出现未知错误");
        return responseData;
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseData handlerException(BaseException e){
        logger.error("抛出的检查时异常：{}", e);
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        responseData.setCode(e.getClass().getAnnotation(ResponseStatus.class).code().value());
        responseData.setMessage(e.getMessage());
        return responseData;
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseData handlerException(CommonException e){
        logger.error("抛出的运行时异常：{}", e);
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        responseData.setCode(e.getCode());
        responseData.setMessage(e.getMessage());
        return responseData;
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseData handlerException(NullPointerException e){
        logger.error("空值异常：{}", e);
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        responseData.setCode(500);
        responseData.setMessage(e.getMessage());
        return responseData;
    }


}
