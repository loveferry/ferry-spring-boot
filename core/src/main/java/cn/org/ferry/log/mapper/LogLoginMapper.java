package cn.org.ferry.log.mapper;

import cn.org.ferry.log.dto.LogLogin;
import cn.org.ferry.system.mapper.Mapper;

/**
 * Generate by code generator
 * 登陆日志表 mybatis 接口层
 */

public interface LogLoginMapper extends Mapper<LogLogin> {

    /**
     * 登陆日志记录
     */
    int insertLogLogin(LogLogin logLogin);
}
