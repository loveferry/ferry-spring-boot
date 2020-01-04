package cn.org.ferry.sys.service;


import cn.org.ferry.core.service.BaseService;
import cn.org.ferry.sys.dto.LogSoap;

/**
 * Generate by code generator
 * soap接口日志记录表 业务接口
 */

public interface LogSoapService extends BaseService<LogSoap> {
    /**
     * 插入日志
     */
    void insertLogSoap(String serviceAddress, String operationName, String clientAddress, String protocolHeaders,
                       String contentType, String inputContent, String outputContent);
}
