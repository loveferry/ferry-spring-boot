package cn.org.ferry.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "NOT NUMBER")
public class NotNumberException extends BaseException {

    public NotNumberException(){
        super("不是一个数字", "不是一个数字");
    }
}
