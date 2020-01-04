package cn.org.ferry.core.interceptors;

import cn.org.ferry.core.annotations.LoginPass;
import cn.org.ferry.core.components.TokenTactics;
import cn.org.ferry.core.exceptions.TokenException;
import cn.org.ferry.core.utils.ConstantUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {
//    @Autowired
//    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private TokenTactics tokenTactics;
    @Value("#{'${ferry.filter.paths}'.split(',')}")
    private List<String> allowPaths;

    // 在业务处理器处理请求之前被调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(matchUri(request.getRequestURI())){
            return true;
        }
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if(method.isAnnotationPresent(LoginPass.class) || method.getDeclaringClass().isAnnotationPresent(LoginPass.class)){
            return true;
        }
        if(!method.getDeclaringClass().getPackage().getName().contains("cn.org.ferry")){
            return true;
        }
        String _token = request.getHeader(ConstantUtils._TOKEN);
        if(StringUtils.isEmpty(_token)){
            throw new TokenException("unauthorized access!");
        }
        /*SysUser user;
        try{
            String userCode = JWT.decode(_token).getAudience().get(0);
            user = Optional.of(sysUserService.queryByUserCode(userCode)).get();
        }catch (JWTDecodeException e){
            throw new TokenException("Invalid token");
        }
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).withAudience(user.getUserCode()).build();
        try {
            jwtVerifier.verify(_token);
        } catch (JWTVerificationException e) {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String key = NetWorkUtils.getIpAddress(request)+"_"+user.getUserCode();
            String token = valueOperations.get(key);
            if(StringUtils.equals(_token, token)){
                token = tokenTactics.generateToken(user.getUserCode(), user.getPassword());
                tokenTactics.setTokenToRedisWithPeriodOfValidity(key, token);
                response.setStatus(TokenException.class.getAnnotation(ResponseStatus.class).code().value());
                response.addHeader(ConstantUtils._TOKEN, token);
                response.getWriter().append("refresh token");
                return false;
            }else{
                throw new TokenException("Invalid token! Please log in again!");
            }
        }*/
        return true;
    }
    // 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    // 在整个请求结束之后被调用，也就是在 DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

    private boolean matchUri(String uri){
        if(allowPaths.contains(uri)){
            return true;
        }
        for (String allowPath : allowPaths) {
            if(allowPath.equals("/")){
                return true;
            }
            if(allowPath.endsWith("**") && uri.startsWith(allowPath.substring(0,allowPath.length()-2))){
                return true;
            }
            String path = allowPath.substring(0,allowPath.length()-1);
            if(allowPath.endsWith("*") && uri.startsWith(path)){
                if(StringUtils.split(uri.substring(path.length()-1),'/').length == 1){
                    return true;
                }
            }
        }
        return false;
    }
}
