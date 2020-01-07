package cn.org.ferry.sys.service.impl;

import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.core.utils.NetWorkUtils;
import cn.org.ferry.core.utils.TokenUtils;
import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.sys.mapper.SysUserMapper;
import cn.org.ferry.sys.service.LogLoginService;
import cn.org.ferry.sys.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private LogLoginService logLoginService;

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
        if(sysUser == null || !StringUtils.equals(password, sysUser.getPassword())){
            responseData.setSuccess(false);
            responseData.setMessage("用户不存在或密码输入错误!");
            return responseData;
        }
        String token = TokenUtils.generateToken(sysUser.getUserCode(), sysUser.getPassword());
        logLoginService.insertLogLogin(sysUser.getUserCode(), NetWorkUtils.getIpAddress(request), NetWorkUtils.getUserAgent(request));
        TokenUtils.setTokenToRedisWithPeriodOfValidity(request.getSession().getId()+"_"+sysUser.getUserCode(), token);
        responseData.setToken(token);
        responseData.setMessage("登陆成功!");
        return responseData;
    }
}
