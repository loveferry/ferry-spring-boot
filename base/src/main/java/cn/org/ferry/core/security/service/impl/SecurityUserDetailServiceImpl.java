package cn.org.ferry.core.security.service.impl;

import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.sys.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>description
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/05/18 20:29
 */

@Service("securityUserDetailServiceImpl")
public class SecurityUserDetailServiceImpl implements UserDetailsService {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(SecurityUserDetailServiceImpl.class);

    @Resource
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("load user by username with spring security.");
        SysUser sysUser = sysUserService.queryByUserNameWithEnabled(username);
        User user = new User(
                sysUser.getUserName(),
                sysUser.getPassword(),
                null
        );
        return user;
    }
}
