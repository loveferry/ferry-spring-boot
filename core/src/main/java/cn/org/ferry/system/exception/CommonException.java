package cn.org.ferry.system.exception;

import org.springframework.http.HttpStatus;

public class CommonException extends RuntimeException {
    private String message;

    private int code;

    public CommonException(){
        super();
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public CommonException(String message){
        super(message);
        this.message = message;
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public CommonException(String message, int code){
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
