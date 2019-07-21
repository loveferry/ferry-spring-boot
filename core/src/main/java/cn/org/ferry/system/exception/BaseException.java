package cn.org.ferry.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public abstract class BaseException extends Exception {
    private String message;

    private String code;

    protected BaseException(){
        super();
    }

    protected BaseException(String message){
        super(message);
        this.message = message;
    }

    protected BaseException(String message, String code){
        super(message);
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
