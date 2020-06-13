package cn.org.ferry.core.security.filters;

import cn.org.ferry.core.security.jwt.JwtCache;
import cn.org.ferry.core.security.jwt.JwtGenerator;
import cn.org.ferry.core.security.jwt.JwtPair;
import cn.org.ferry.core.security.jwt.JwtPayload;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>jwt 认证过滤器
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/06/01 16:17
 */

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private static final String AUTHENTICATION_PREFIX = "Bearer ";
    private AuthenticationEntryPoint authenticationEntryPoint;
    private JwtGenerator jwtGenerator;
    private JwtCache jwtCache;


    public JwtAuthenticationFilter(JwtGenerator jwtGenerator, JwtCache jwtCache, AuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtGenerator = jwtGenerator;
        this.jwtCache = jwtCache;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 通过认证的直接放行
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(request, response);
            return;
        }
        // 无 token 放行，SecurityContext 无权限不会放行
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(header) && header.startsWith(AUTHENTICATION_PREFIX)) {
            String jwtToken = header.replace(AUTHENTICATION_PREFIX, "");
            if (!StringUtils.hasText(jwtToken)) {
                authenticationEntryPoint.commence(request, response, new AuthenticationCredentialsNotFoundException("token is not found"));
                return ;
            }
            try {
                authenticationTokenHandle(jwtToken, request);
            } catch (AuthenticationException e) {
                authenticationEntryPoint.commence(request, response, e);
                return ;
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * 认证逻辑
     */
    private void authenticationTokenHandle(String token, HttpServletRequest request) throws AuthenticationException {
        logger.info("token authorization start");
        // 解析 token
        JSONObject jsonObject = jwtGenerator.decode(token);
        String username;
        try {
            Objects.requireNonNull(jsonObject);
            username = jsonObject.get(JwtPayload.AUD).toString();
        }catch (Exception e){
            throw new BadCredentialsException("token is invalid", e);
        }
        // 从缓存获取 token
        JwtPair jwtPair = jwtCache.get(username);
        // token 失效，刷新token
        if(LocalDateTime.now().isAfter(
                LocalDateTime.parse(
                        jsonObject.get(JwtPayload.EXP).toString(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                )
        ) || Objects.isNull(jwtPair)){
            jwtCache.expire(username);
            throw new CredentialsExpiredException("token is expired");
        }
        String accessToken = jwtPair.getAccessToken();
        if (!token.equals(accessToken)) {
            throw new BadCredentialsException("token is invalid");
        }
        JSONArray jsonArray = jsonObject.getJSONArray(JwtPayload.AUTHORITIES);
        String[] authoritiesArray = new String[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            authoritiesArray[i] = jsonArray.getString(i);
        }
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(authoritiesArray);
        User user = new User(username, "[PROTECTED]", authorities);
        // 构建用户认证token
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, authorities);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // 放入安全上下文中
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
