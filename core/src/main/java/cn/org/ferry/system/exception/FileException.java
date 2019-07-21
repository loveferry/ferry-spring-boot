package cn.org.ferry.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "File Error")
public class FileException extends BaseException {

    public FileException(String message){
        super(message);
    }
}
