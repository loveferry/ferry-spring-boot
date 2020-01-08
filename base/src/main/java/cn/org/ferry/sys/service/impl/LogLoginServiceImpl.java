package cn.org.ferry.sys.service.impl;

import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.sys.dto.LogLogin;
import cn.org.ferry.sys.mapper.LogLoginMapper;
import cn.org.ferry.sys.service.LogLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import javax.annotation.Resource;

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

    @Resource
    private LogLoginMapper logLoginMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertLogLogin(String userCode, String ip, String userAgent) {
        LogLogin logLogin = new LogLogin();
        logLogin.setUserCode(userCode);
        logLogin.setIp(ip);
        logLogin.setUserAgent(userAgent);
        logLogin.setLoginDate(new Date());
        int count = logLoginMapper.insertSelective(logLogin);
        logger.info("登陆日志记录：成功插表 {} 条，{}", count, logLogin);
        return true;
    }
}
