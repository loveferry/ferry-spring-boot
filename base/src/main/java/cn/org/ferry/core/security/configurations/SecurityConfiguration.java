package cn.org.ferry.core.security.configurations;

import cn.org.ferry.core.dto.CorsProp;
import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.core.exceptions.CommonException;
import cn.org.ferry.core.security.dynamic.DynamicFilterInvocationSecurityMetadataSource;
import cn.org.ferry.core.security.handlers.SecurityAuthenticationFailureHandler;
import cn.org.ferry.core.security.handlers.SecurityAuthenticationSuccessHandler;
import cn.org.ferry.core.security.jwt.JwtCache;
import cn.org.ferry.core.security.jwt.JwtGenerator;
import cn.org.ferry.core.security.jwt.JwtProperties;
import cn.org.ferry.core.utils.NetWorkUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
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

    /**
     * jwt 属性文件
     */
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * jwt 缓存 redis 实现类
     */
    @Resource
    private JwtCache jwtRedisCache;

    /**
     * 用户详细信息组件
     */
    @Resource
    private UserDetailsService securityUserDetailServiceImpl;

    /**
     * 投票器
     */
    @Autowired
    private List<AccessDecisionVoter<?>> decisionVoters;

    /**
     * 跨域访问配置属性类
     */
    @Autowired
    private CorsProp corsProp;

    @Value("${server.api-login-uri}")
    private String apiLoginUri;

    @Value("${server.api-logout-uri}")
    private String apiLogoutUri;

    @Value("${server.form-login-uri}")
    private String formLoginUri;

    @Value("${server.need-form-login-uri}")
    private List<String> needFormLoginUri;



    /**
     * 跨域请求配置
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置允许的网站域名，如果全允许则设为 *
        if(CollectionUtils.isEmpty(corsProp.getAllowedOrigins())){
            config.addAllowedOrigin("*");
        }else{
            for (String allowedOrigin : corsProp.getAllowedOrigins()) {
                config.addAllowedOrigin(allowedOrigin);
            }
        }
        // 设置请求头允许范围
        if(CollectionUtils.isEmpty(corsProp.getAllowedHeaders())){
            throw new CommonException("未设置允许的请求头!");
        }else{
            for (String allowedHeader : corsProp.getAllowedHeaders()) {
                config.addAllowedHeader(allowedHeader);
            }
        }
        // 设置请求方式的允许范围
        if(CollectionUtils.isEmpty(corsProp.getAllowedMethods())){
            throw new CommonException("未设置允许的请求方式!");
        }else{
            for (String allowedMethod : corsProp.getAllowedMethods()) {
                config.addAllowedMethod(allowedMethod);
            }
        }
        // 设置预检请求的最大缓存范围
        config.setMaxAge(corsProp.getMaxAge());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(corsProp.getUrl(), config);
        return source;
    }

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
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        logger.info("init spring bean of {}", AuthenticationSuccessHandler.class.getName());
        return new SecurityAuthenticationSuccessHandler();
    }

    /**
     * 登录失败拦截器
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        logger.info("init spring bean of {}", AuthenticationFailureHandler.class.getName());
        return new SecurityAuthenticationFailureHandler();
    }

    /**
     * 用户认证的时候出现错误时抛出的异常
     */
    /*@Bean
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
    }*/

    @Bean
    public DelegatingAuthenticationEntryPoint delegatingAuthenticationEntryPoint(){
        LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> map = new LinkedHashMap<>(2);
        // 第三方插件访问跳转 spring security 默认登录页面完成登录认证
        LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint = new LoginUrlAuthenticationEntryPoint(formLoginUri);
        if(CollectionUtils.isNotEmpty(needFormLoginUri)){
            for (String uri : needFormLoginUri) {
                map.put(new AntPathRequestMatcher(uri), loginUrlAuthenticationEntryPoint);
            }
        }
        // 若不是ajax请求并且请求内容是静态资源则跳转至spring security 默认登录页面完成登录认证
        NegatedRequestMatcher negatedRequestMatcher = new NegatedRequestMatcher(
                new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest")
        );
        MediaTypeRequestMatcher mediaMatcher = new MediaTypeRequestMatcher(new HeaderContentNegotiationStrategy(), MediaType.APPLICATION_XHTML_XML,
                new MediaType("image", "*"), MediaType.TEXT_HTML, MediaType.TEXT_PLAIN);
        mediaMatcher.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));
        map.put(new AndRequestMatcher(negatedRequestMatcher, mediaMatcher), loginUrlAuthenticationEntryPoint);

        DelegatingAuthenticationEntryPoint delegatingAuthenticationEntryPoint = new DelegatingAuthenticationEntryPoint(map);
        delegatingAuthenticationEntryPoint.setDefaultEntryPoint((request, response, authException) -> {
            logger.warn(authException.getMessage());
            ResponseData responseData = new ResponseData();
            responseData.setCode(HttpStatus.UNAUTHORIZED.value());
            responseData.setSuccess(false);
            responseData.setMessage(authException.getMessage());
            NetWorkUtils.responseJsonWriter(response, HttpServletResponse.SC_UNAUTHORIZED, responseData);
        });
        return delegatingAuthenticationEntryPoint;
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
        RoleVoter roleVoter = new RoleVoter();
        roleVoter.setRolePrefix("");
        return roleVoter;
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
//                object.setSecurityMetadataSource(dynamicFilterInvocationSecurityMetadataSource());
                return object;
            }
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("start config spring security.");
        http
                .csrf()
                .disable()
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                .authorizeRequests()
//                .anyRequest()
//                .authenticated()
                .antMatchers("/swagger**").permitAll().anyRequest().authenticated()
                .withObjectPostProcessor(filterSecurityInterceptorObjectPostProcessor())
                .and()
                .formLogin()
//                .successHandler(authenticationSuccessHandler())
//                .failureHandler(authenticationFailureHandler())
                .permitAll()
                .and()
//                .exceptionHandling()
//                .defaultAuthenticationEntryPointFor(delegatingAuthenticationEntryPoint(), AnyRequestMatcher.INSTANCE)
//                .accessDeniedHandler(accessDeniedHandler())
//                .and()
                .logout()
                .addLogoutHandler(logoutHandler())
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserDetailServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
    }
}
