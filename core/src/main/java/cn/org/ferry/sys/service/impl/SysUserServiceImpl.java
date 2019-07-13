package cn.org.ferry.sys.service.impl;

import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.sys.mapper.SysUserMapper;
import cn.org.ferry.sys.service.SysUserService;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser queryByUserCode(String userCode) {
        return sysUserMapper.queryByUserCode(userCode);
    }
}
