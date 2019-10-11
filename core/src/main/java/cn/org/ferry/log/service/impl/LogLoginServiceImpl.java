package cn.org.ferry.log.service.impl;

import cn.org.ferry.log.dto.LogLogin;
import cn.org.ferry.log.mapper.LogLoginMapper;
import cn.org.ferry.log.service.LogLoginService;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Generate by code generator
 * 登陆日志表 业务接口实现类
 */

@Service
public class LogLoginServiceImpl extends BaseServiceImpl<LogLogin> implements LogLoginService {
    /**
     * 日志处理对象
     **/
    private static final Logger logger = LoggerFactory.getLogger(LogLoginServiceImpl.class);

    @Autowired
    private LogLoginMapper logLoginMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertLogLogin(String userCode, String ip, String userAgent) {
        LogLogin logLogin = new LogLogin();
        logLogin.setUserCode(userCode);
        logLogin.setIp(ip);
        logLogin.setUserAgent(userAgent);
        logLogin.setLoginDate(new Date());
        insertSelective(logLogin);
        return true;
    }
}
