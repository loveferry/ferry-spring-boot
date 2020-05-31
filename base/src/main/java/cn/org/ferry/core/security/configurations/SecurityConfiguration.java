package cn.org.ferry.core.security.configurations;

import cn.hutool.core.collection.CollectionUtil;
import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.core.security.handlers.LogoutHandler;
import cn.org.ferry.core.security.handlers.LogoutSuccessHandler;
import cn.org.ferry.core.security.jwt.JwtCache;
import cn.org.ferry.core.security.jwt.JwtGenerator;
import cn.org.ferry.core.security.jwt.JwtPair;
import cn.org.ferry.core.security.jwt.JwtProperties;
import cn.org.ferry.core.utils.NetWorkUtils;
import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.sys.service.LogLoginService;
import cn.org.ferry.sys.service.SysUserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>spring 安全
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/03/15 16:17
 */

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Resource
    private JwtProperties jwtProperties;

    @Resource(name = "jwtRedisCache")
    private JwtCache jwtCache;

    @Resource(name = "securityUserDetailServiceImpl")
    private UserDetailsService userDetailsService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private LogLoginService logLoginService;

    @Bean
    public JwtGenerator jwtGenerator(){
        return new JwtGenerator(jwtCache, jwtProperties);
    }

    /**
     * 登录认证成功拦截器
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(JwtGenerator jwtGenerator) {
        return (request, response, authentication) -> {
            if (response.isCommitted()) {
                logger.debug("Response has already been committed");
                return;
            }
            Map<String, Object> map = new HashMap<>();
            User principal = (User) authentication.getPrincipal();

            Collection<GrantedAuthority> authorities = principal.getAuthorities();
            Set<String> authoritySet = new HashSet<>();
            if (CollectionUtil.isNotEmpty(authorities)) {
                for (GrantedAuthority authority : authorities) {
                    authoritySet.add(authority.getAuthority());
                }
            }

            SysUser sysUser = sysUserService.queryForLoginSuccess(principal.getUsername());
            JwtPair jwtPair = jwtGenerator.jwtPair(principal.getUsername(), authoritySet, JSON.parseObject(JSONObject.toJSONString(sysUser)));
            map.put("access_token", jwtPair.getAccessToken());
            map.put("refresh_token", jwtPair.getRefreshToken());

            List<Map<String, Object>> list = new ArrayList<>(1);
            list.add(map);
            ResponseData responseData = new ResponseData(list);
            responseData.setMessage("login success");

            logLoginService.insertLogLogin(sysUser.getUserCode(), NetWorkUtils.getIpAddress(request), NetWorkUtils.getUserAgent(request));
            logger.info("user {} login success.", sysUser.getDescription());
            responseJsonWriter(response, responseData);
        };
    }

    /**
     * 登录失败拦截器
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            if (response.isCommitted()) {
                logger.debug("Response has already been committed");
                return;
            }
            ResponseData responseData = new ResponseData();
            responseData.setCode(HttpStatus.UNAUTHORIZED.value());
            responseData.setSuccess(false);
            responseData.setMessage("login failure");
            responseJsonWriter(response, responseData);
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("start config spring security.");
        http
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler(jwtGenerator()))
                .failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(new LogoutHandler())
                .logoutSuccessHandler(new LogoutSuccessHandler())
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    private static void responseJsonWriter(HttpServletResponse response, ResponseData responseData) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(new ObjectMapper().writeValueAsString(responseData));
        printWriter.flush();
        printWriter.close();
    }
}
