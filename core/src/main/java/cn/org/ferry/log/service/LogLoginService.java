package cn.org.ferry.log.service;

import cn.org.ferry.system.service.BaseService;
import cn.org.ferry.log.dto.LogLogin;

/**
 * Generate by code generator
 * 登陆日志表 业务接口
 */

public interface LogLoginService extends BaseService<LogLogin> {
    /**
     * 插入登陆数据
     * @param userCode 登陆人的code
     * @param ip 登陆人的请求url
     * @param userAgent 用户代理
     */
    boolean insertLogLogin(String userCode, String ip, String userAgent);
}
