package cn.org.ferry.system.exception;

public class LogicDeleteException extends CommonException {

    public LogicDeleteException() {
        super();
    }

    public LogicDeleteException(String message) {
        super(message);
    }

    public LogicDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogicDeleteException(Throwable cause) {
        super(cause);
    }
}
