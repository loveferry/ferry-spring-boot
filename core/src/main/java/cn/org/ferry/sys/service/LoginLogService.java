package cn.org.ferry.sys.service;

import cn.org.ferry.sys.dto.LoginLog;
import cn.org.ferry.system.service.BaseService;

public interface LoginLogService extends BaseService<LoginLog> {
    /**
     * 插入登陆数据
     * @param userCode 登陆人的code
     * @param ip 登陆人的请求url
     */
    boolean insertLoginLog(String userCode, String ip);
}
