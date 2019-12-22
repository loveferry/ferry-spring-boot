package cn.org.ferry.system.exception;

/**
 * @author liuzh
 * @since 3.5.0
 */
public class VersionException extends CommonException {
    public VersionException() {
        super();
    }

    public VersionException(String message) {
        super(message);
    }

    public VersionException(String message, Throwable cause) {
        super(message, cause);
    }

    public VersionException(Throwable cause) {
        super(cause);
    }

}
