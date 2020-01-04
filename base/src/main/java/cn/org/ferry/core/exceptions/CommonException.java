package cn.org.ferry.core.exceptions;

import org.springframework.http.HttpStatus;

public class CommonException extends RuntimeException {
    private String message;

    private int code = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public CommonException(){
        super();
    }

    public CommonException(String message){
        super(message);
        this.message = message;
    }

    public CommonException(Throwable cause){
        super(cause);
    }

    public CommonException(String message, int code){
        super(message);
        this.message = message;
        this.code = code;
    }

    public CommonException(String message, Throwable cause){
        super(message, cause);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
