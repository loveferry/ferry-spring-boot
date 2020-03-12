package cn.org.ferry.core.exceptions;

import org.springframework.http.HttpStatus;

public class QueryParamsException extends CommonException {
    private String message;

    private int code = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public QueryParamsException(){
        super("查询参数不能为空");
    }

    public QueryParamsException(String message){
        super(message);
        this.message = message;
    }

    public QueryParamsException(Throwable cause){
        super(cause);
    }

    public QueryParamsException(String message, int code){
        super(message);
        this.message = message;
        this.code = code;
    }

    public QueryParamsException(String message, Throwable cause){
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
