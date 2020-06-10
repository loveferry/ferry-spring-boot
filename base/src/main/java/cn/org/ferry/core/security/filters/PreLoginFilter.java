package cn.org.ferry.core.security.filters;

import cn.org.ferry.core.security.dto.LoginType;
import cn.org.ferry.core.security.processors.DefaultLoginPostProcessor;
import cn.org.ferry.core.security.processors.LoginPostProcessor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * <p>登录前过滤器
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/05/31 20:58
 */

public class PreLoginFilter extends GenericFilterBean {
    private static final String LOGIN_TYPE = "type";


    private RequestMatcher requiresAuthenticationRequestMatcher;
    private Map<LoginType, LoginPostProcessor> processors = new HashMap<>();


    public PreLoginFilter(String loginProcessingUrl, Collection<LoginPostProcessor> loginPostProcessors) {
        Assert.notNull(loginProcessingUrl, "loginProcessingUrl must not be null");
        requiresAuthenticationRequestMatcher = new AntPathRequestMatcher(loginProcessingUrl, "POST");
        if (CollectionUtils.isEmpty(loginPostProcessors)) {
            DefaultLoginPostProcessor loginPostProcessor = new DefaultLoginPostProcessor();
            processors.put(loginPostProcessor.loginType(), loginPostProcessor);
        }else{
            loginPostProcessors.forEach(e -> processors.put(e.loginType(), e));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper((HttpServletRequest) request);
        if (requiresAuthenticationRequestMatcher.matches((HttpServletRequest) request)) {
            LoginPostProcessor loginPostProcessor = processors.get(LoginType.valueOf(Optional.ofNullable(request.getParameter(LOGIN_TYPE)).orElse(LoginType.FORM.name())));
            requestWrapper.setAttribute(LoginPostProcessor.USERNAME, loginPostProcessor.username(request));
            requestWrapper.setAttribute(LoginPostProcessor.PASSWORD, loginPostProcessor.password(request));
        }
        chain.doFilter(requestWrapper, response);
    }

    /**
     * 请求包装类
     *      将参数放入attribute中，重写获取参数方法，直接从attribute中获取
     */
    class ParameterRequestWrapper extends HttpServletRequestWrapper {
        ParameterRequestWrapper(HttpServletRequest request ) {
            super(request);

        }

        @Override
        public String getParameter(String name) {
            return (String) super.getAttribute(name);
        }
    }
}
