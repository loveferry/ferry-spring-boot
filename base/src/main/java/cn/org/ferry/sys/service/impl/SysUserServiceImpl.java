package cn.org.ferry.sys.service.impl;

import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.sys.mapper.SysUserMapper;
import cn.org.ferry.sys.service.SysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser queryForLoginSuccess(String userName) {
        return sysUserMapper.queryForLoginSuccess(userName);
    }

    @Override
    public SysUser queryByUserNameForSecurityAuthentication(String userName) {
        SysUser sysUser = sysUserMapper.queryByUserNameForSecurityAuthentication(userName);
        List<String> roleCodes = queryRoleCodesByUserCode(sysUser.getUserCode());
        if(CollectionUtils.isEmpty(roleCodes)){
            sysUser.setAuthorities(AuthorityUtils.NO_AUTHORITIES);
        }else{
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roleCodes.size());
            roleCodes.forEach(roleCode -> grantedAuthorities.add(() -> roleCode));
            sysUser.setAuthorities(grantedAuthorities);
        }
        return sysUser;
    }

    @Override
    public List<SysUser> query(String userName, String description) {
        SysUser sysUser = new SysUser();
        sysUser.setUserName(userName);
        sysUser.setDescription(description);
        return sysUserMapper.select(sysUser);
    }

    @Override
    public List<String> queryRoleCodesByUserCode(String userCode) {
        return sysUserMapper.queryRoleCodesByUserCode(userCode);
    }
}
