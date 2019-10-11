package cn.org.ferry.sys.service.impl;

import cn.org.ferry.log.service.LogLoginService;
import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.sys.mapper.SysUserMapper;
import cn.org.ferry.sys.service.SysUserService;
import cn.org.ferry.system.components.TokenTactics;
import cn.org.ferry.system.dto.ResponseData;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import cn.org.ferry.system.utils.NetWorkUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private LogLoginService logLoginService;
    @Autowired
    private TokenTactics tokenTactics;

    @Override
    public SysUser queryByUserCode(String userCode) {
        return sysUserMapper.queryByUserCode(userCode);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData login(HttpServletRequest request, SysUser sysUser) {
        String password = sysUser.getPassword();
        ResponseData responseData = new ResponseData();
        sysUser = queryByUserCode(sysUser.getUserCode());
        if(sysUser == null){
            responseData.setSuccess(false);
            responseData.setMessage("用户不存在或密码输入错误!");
            return responseData;
        }
        if(!StringUtils.equals(password, sysUser.getPassword())){
            responseData.setSuccess(false);
            responseData.setMessage("用户不存在或密码输入错误!");
            return responseData;
        }
        String token = tokenTactics.generateToken(sysUser.getUserCode(), sysUser.getPassword());
        String ip = NetWorkUtils.getIpAddress(request);
        logLoginService.insertLogLogin(sysUser.getUserCode(), ip, NetWorkUtils.getUserAgent(request));
        tokenTactics.setTokenToRedisWithPeriodOfValidity(ip+"_"+sysUser.getUserCode(), token);
        responseData.setToken(token);
        responseData.setMessage("登陆成功!");
        return responseData;
    }
}
