package cn.org.ferry.sys.service.impl;

import cn.org.ferry.sys.dto.LoginLog;
import cn.org.ferry.sys.service.LoginLogService;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLog> implements LoginLogService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertLoginLog(String userCode, String ip, String userAgent) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUserCode(userCode);
        loginLog.setIp(ip);
        loginLog.setUserAgent(userAgent);
        loginLog.setLoginDate(new Date());
        insert(loginLog);
        return true;
    }
}
