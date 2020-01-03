package cn.org.ferry.sys.exceptions;

import cn.org.ferry.core.exceptions.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <p>附件异常
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/20 20:21
 */

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AttachmentException extends CommonException {
    public AttachmentException(String message){
        super(message);
    }
}
