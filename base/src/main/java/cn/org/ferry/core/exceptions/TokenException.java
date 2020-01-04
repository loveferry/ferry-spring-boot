package cn.org.ferry.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "TOKEN ERROR")
public class TokenException extends BaseException {
    public TokenException(String errorMessage){
        super(errorMessage);
    }
}
