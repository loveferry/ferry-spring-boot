package cn.org.ferry.system.exception;

public class TokenException extends RuntimeException {
    private int errorCode;

    public TokenException(int errorCode, String errorMessage){
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
