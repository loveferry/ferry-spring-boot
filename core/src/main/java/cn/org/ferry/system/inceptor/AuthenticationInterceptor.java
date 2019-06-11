package cn.org.ferry.system.inceptor;

import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.sys.service.SysUserService;
import cn.org.ferry.system.annotation.LoginPass;
import cn.org.ferry.system.exception.TokenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Resource
    SysUserService sysUserService;

    // 在业务处理器处理请求之前被调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if(method.isAnnotationPresent(LoginPass.class)){
            return true;
        }
        String _token = request.getHeader("_token");
        if(StringUtils.isEmpty(_token)){
            response.setStatus(401);
            throw new TokenException(401, "无token，请重新登录");
        }
        SysUser user;
        try{
            String userCode = JWT.decode(_token).getAudience().get(0);
            user = sysUserService.queryByUserCode(userCode);
        }catch (JWTDecodeException e){
            response.setStatus(401);
            throw new TokenException(401, "无效的token");
        }
        if(user == null){
            response.setStatus(401);
            throw new TokenException(401, "用户不存在!请重新登录!");
        }
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(_token);
        } catch (JWTVerificationException e) {
            response.setStatus(401);
            throw new TokenException(401, "无效的token!请重新登录!");
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
