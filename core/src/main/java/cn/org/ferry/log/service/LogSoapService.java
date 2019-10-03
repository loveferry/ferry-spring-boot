package cn.org.ferry.log.service;

import cn.org.ferry.system.service.BaseService;
import cn.org.ferry.log.dto.LogSoap;

/**
 * Generate by code generator
 * soap接口日志记录表 业务接口
 */

public interface LogSoapService extends BaseService<LogSoap> {
    /**
     * 插入日志
     */
    void insertLogSoap(LogSoap logSoap);
}
