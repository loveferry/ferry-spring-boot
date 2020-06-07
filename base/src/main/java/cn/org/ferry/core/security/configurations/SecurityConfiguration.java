package cn.org.ferry.core.security.configurations;

import cn.hutool.core.collection.CollectionUtil;
import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.core.security.dynamic.DynamicFilterInvocationSecurityMetadataSource;
import cn.org.ferry.core.security.filters.JwtAuthenticationFilter;
import cn.org.ferry.core.security.filters.LoginPostProcessor;
import cn.org.ferry.core.security.filters.PreLoginFilter;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    public static final String LOGIN_URL = "/login";

    @Autowired
    private JwtProperties jwtProperties;

    @Resource
    private JwtCache jwtRedisCache;

    @Resource
    private UserDetailsService securityUserDetailServiceImpl;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private LogLoginService logLoginService;

    @Autowired
    private Collection<LoginPostProcessor> loginPostProcessors;

    @Autowired
    private List<AccessDecisionVoter<?>> decisionVoters;

    /**
     * jwt 生成器
     */
    @Bean
    public JwtGenerator jwtGenerator(){
        logger.info("init spring bean of {}", JwtGenerator.class.getName());
        return new JwtGenerator(jwtRedisCache, jwtProperties);
    }

    /**
     * 登录认证成功拦截器
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(JwtGenerator jwtGenerator) {
        logger.info("init spring bean of {}", AuthenticationSuccessHandler.class.getName());
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
            NetWorkUtils.responseJsonWriter(response, HttpServletResponse.SC_OK, responseData);
        };
    }

    /**
     * 登录失败拦截器
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        logger.info("init spring bean of {}", AuthenticationFailureHandler.class.getName());
        return (request, response, exception) -> {
            if (response.isCommitted()) {
                logger.debug("Response has already been committed");
                return;
            }
            ResponseData responseData = new ResponseData();
            responseData.setCode(HttpStatus.UNAUTHORIZED.value());
            responseData.setSuccess(false);
            responseData.setMessage("login failure");
            NetWorkUtils.responseJsonWriter(response, HttpServletResponse.SC_OK, responseData);
        };
    }

    /**
     * 用户认证的时候出现错误时抛出的异常
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        logger.info("init spring bean of {}", AuthenticationEntryPoint.class.getName());
        return (request, response, authException) -> {
            logger.warn(authException.getMessage());
            ResponseData responseData = new ResponseData();
            responseData.setCode(HttpStatus.UNAUTHORIZED.value());
            responseData.setSuccess(false);
            responseData.setMessage(authException.getMessage());
            NetWorkUtils.responseJsonWriter(response, HttpServletResponse.SC_UNAUTHORIZED, responseData);
        };
    }

    /**
     * 访问受保护资源时被拒绝而抛出的异常
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        logger.info("init spring bean of {}", AccessDeniedHandler.class.getName());
        return (request, response, accessDeniedException) -> {
            logger.warn(accessDeniedException.getMessage());
            ResponseData responseData = new ResponseData();
            responseData.setCode(HttpStatus.FORBIDDEN.value());
            responseData.setSuccess(false);
            responseData.setMessage(accessDeniedException.getMessage());
            NetWorkUtils.responseJsonWriter(response, HttpServletResponse.SC_FORBIDDEN, responseData);
        };
    }

    /**
     * 登录前置过滤器
     */
    @Bean
    public PreLoginFilter preLoginFilter(){
        logger.info("init spring bean of {}", PreLoginFilter.class.getName());
        return new PreLoginFilter(LOGIN_URL,loginPostProcessors);
    }

    /**
     * jwt 认证过滤器
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        logger.info("init spring bean of {}", JwtAuthenticationFilter.class.getName());
        return new JwtAuthenticationFilter(jwtGenerator(), jwtRedisCache, authenticationEntryPoint());
    }

    /**
     * 登出拦截器
     */
    @Bean
    public LogoutHandler logoutHandler(){
        logger.info("init spring bean of {}", LogoutHandler.class.getName());
        return (request, response, authentication) -> {
            if(Objects.isNull(authentication)){
                logger.info("Never logged in, no need to log out.Then Please login.");
                return ;
            }
            User user = (User)authentication.getPrincipal();
            logger.info("{} logout service.", user.getUsername());
            ResponseData responseData = new ResponseData();
            responseData.setMessage("登出成功");
            responseData.setCode(HttpStatus.OK.value());
            try {
                NetWorkUtils.responseJsonWriter(response, HttpServletResponse.SC_OK, responseData);
            } catch (IOException e) {
                logger.error("logout error", e);
            }
        };
    }

    /**
     * 动态元数据加载器
     */
    @Bean
    public FilterInvocationSecurityMetadataSource dynamicFilterInvocationSecurityMetadataSource() {
        return new DynamicFilterInvocationSecurityMetadataSource();
    }

    /**
     * 角色投票器
     */
    @Bean
    public RoleVoter roleVoter() {
        return new RoleVoter();
    }

    /**
     * 基于肯定的访问决策器
     */
    @Bean
    public AccessDecisionManager affirmativeBased() {
        return new AffirmativeBased(decisionVoters);
    }

    /**
     * 自定义 FilterSecurityInterceptor  ObjectPostProcessor 以替换默认配置达到动态权限的目的
     */
    private ObjectPostProcessor<FilterSecurityInterceptor> filterSecurityInterceptorObjectPostProcessor() {
        return new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                object.setAccessDecisionManager(affirmativeBased());
                object.setSecurityMetadataSource(dynamicFilterInvocationSecurityMetadataSource());
                return object;
            }
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("start config spring security.");
        JwtAuthenticationFilter jwtAuthenticationFilter = jwtAuthenticationFilter();
        http
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/sys/attachment/query").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .withObjectPostProcessor(filterSecurityInterceptorObjectPostProcessor())
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler()).authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(preLoginFilter(), JwtAuthenticationFilter.class)
                .formLogin()
                .loginProcessingUrl(LOGIN_URL)
                .successHandler(authenticationSuccessHandler(jwtGenerator()))
                .failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(logoutHandler())
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserDetailServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
    }
}
