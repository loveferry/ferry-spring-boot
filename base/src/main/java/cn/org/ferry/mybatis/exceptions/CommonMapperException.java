package cn.org.ferry.mybatis.exceptions;

/**
 * 标志异常为 mybatis 错误
 *
 * @author ferry
 */
public class CommonMapperException extends RuntimeException {

    public CommonMapperException() {
        super();
    }

    public CommonMapperException(String message) {
        super(message);
    }

    public CommonMapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonMapperException(Throwable cause) {
        super(cause);
    }

}
