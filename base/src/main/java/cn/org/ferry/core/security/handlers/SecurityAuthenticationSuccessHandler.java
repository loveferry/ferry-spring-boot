package cn.org.ferry.core.security.handlers;

import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.core.security.jwt.JwtGenerator;
import cn.org.ferry.core.security.jwt.JwtPair;
import cn.org.ferry.core.utils.NetWorkUtils;
import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.sys.service.LogLoginService;
import cn.org.ferry.sys.service.SysUserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>认证成功拦截器
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/06/16 09:15
 */

public class SecurityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(SecurityAuthenticationSuccessHandler.class);

    /**
     * 用户服务
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录日志
     */
    @Autowired
    private LogLoginService logLoginService;

    /**
     * jwt 生成器
     */
    @Autowired
    private JwtGenerator jwtGenerator;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (response.isCommitted()) {
            logger.debug("Response has already been committed");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        User principal = (User) authentication.getPrincipal();

        Collection<GrantedAuthority> authorities = principal.getAuthorities();
        SysUser sysUser = sysUserService.queryForLoginSuccess(principal.getUsername());
        JwtPair jwtPair = jwtGenerator.jwtPair(principal.getUsername(), authorities, JSON.parseObject(JSONObject.toJSONString(sysUser)));
        map.put("access_token", jwtPair.getAccessToken());
        map.put("refresh_token", jwtPair.getRefreshToken());
        map.put("authorities", principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray());

        List<Map<String, Object>> list = new ArrayList<>(1);
        list.add(map);
        ResponseData responseData = new ResponseData(list);
        responseData.setMessage("login success");

        logLoginService.insertLogLogin(sysUser.getUserCode(), NetWorkUtils.getIpAddress(request), NetWorkUtils.getUserAgent(request));
        logger.info("user {} login success.", sysUser.getDescription());
        NetWorkUtils.responseJsonWriter(response, HttpServletResponse.SC_OK, responseData);
    }
}
