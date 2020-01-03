package cn.org.ferry.sys.exceptions;

import cn.org.ferry.core.exceptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <p>文件出错
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/20 20:21
 */

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "File Error")
public class FileException extends BaseException {

    public FileException(String message){
        super(message);
    }
}
