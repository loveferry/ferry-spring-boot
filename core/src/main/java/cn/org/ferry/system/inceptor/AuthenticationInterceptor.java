package cn.org.ferry.system.inceptor;

import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.sys.service.SysUserService;
import cn.org.ferry.system.annotation.LoginPass;
import cn.org.ferry.system.components.TokenTactics;
import cn.org.ferry.system.exception.TokenException;
import cn.org.ferry.system.utils.NetWorkUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Optional;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 在业务处理器处理请求之前被调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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
        String _token = request.getHeader("_token");
        if(StringUtils.isEmpty(_token)){
            throw new TokenException("非法访问!");
        }
        SysUser user;
        try{
            String userCode = JWT.decode(_token).getAudience().get(0);
            user = Optional.of(sysUserService.queryByUserCode(userCode)).get();
        }catch (JWTDecodeException e){
            throw new TokenException("无效的token");
        }
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).withAudience(user.getUserCode()).build();
        try {
            jwtVerifier.verify(_token);
        } catch (JWTVerificationException e) {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String key = NetWorkUtils.getIpAddress(request)+"_"+user.getUserCode();
            String token = valueOperations.get(key);
            if(StringUtils.isNotEmpty(token) && StringUtils.equals(_token, token)){
                token = TokenTactics.generateToken(user.getUserCode(), user.getPassword());
                valueOperations.set(key, token);
                response.setStatus(TokenException.class.getAnnotation(ResponseStatus.class).code().value());
                response.addHeader("_token", token);
                response.getWriter().append("refresh token");
                return false;
            }else{
                throw new TokenException("无效的token!请重新登录!");
            }
        }
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
}
