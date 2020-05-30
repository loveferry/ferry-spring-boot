package cn.org.ferry.sys.service.impl;

import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.sys.mapper.SysUserMapper;
import cn.org.ferry.sys.service.SysUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.annotation.Resource;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser queryForLoginSuccess(String userName) {
        return sysUserMapper.queryForLoginSuccess(userName);
    }

    @Override
    public SysUser queryByUserNameForSecurityAuthentication(String userName) {
        return sysUserMapper.queryByUserNameForSecurityAuthentication(userName);
    }

    /*@Transactional(rollbackFor = Exception.class)
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
        logLoginService.insertLogLogin(sysUser.getUserCode(), NetWorkUtils.getIpAddress(request), NetWorkUtils.getUserAgent(request));
        responseData.setMessage("登陆成功!");
        return responseData;
    }*/

    @Override
    public List<SysUser> query(String userName, String description) {
        SysUser sysUser = new SysUser();
        sysUser.setUserName(userName);
        sysUser.setDescription(description);
        return sysUserMapper.select(sysUser);
    }
}
