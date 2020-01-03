package cn.org.ferry.sys.exceptions;

import cn.org.ferry.core.exceptions.CommonException;

/**
 * <p>excel异常
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/20 20:21
 */

public class ExcelException extends CommonException {

    public ExcelException(String errorMessage){
        super(errorMessage);
    }
}
