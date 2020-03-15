package cn.org.ferry.core.exceptions;

import org.springframework.http.HttpStatus;

public class ParameterException extends CommonException {
    private String message;

    private int code = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public ParameterException(){
        super("参数不能为空");
    }

    public ParameterException(String message){
        super(message);
        this.message = message;
    }

    public ParameterException(Throwable cause){
        super(cause);
    }

    public ParameterException(String message, int code){
        super(message);
        this.message = message;
        this.code = code;
    }

    public ParameterException(String message, Throwable cause){
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
